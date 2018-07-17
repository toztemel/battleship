package app.game.service;

import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.battlefield.BattlefieldFactory;
import app.game.fire.Coordinates;
import app.game.fire.CoordinatesFormatter;
import app.game.fire.Shot;
import app.game.util.DoubleArrays;

import java.util.HashMap;
import java.util.Map;

public final class ActiveGames {

    private static ActiveGames instance = new ActiveGames();

    private Map<String, Game> idGameMap;
    private Map<String, Battlefield> idBattlefieldMap;
    private BattlefieldFactory battlefieldFactory;
    private ProtocolService protocolService;

    private ActiveGames() {
        idGameMap = new HashMap<>();
        idBattlefieldMap = new HashMap<>();
    }

    public static ActiveGames getInstance() {
        return instance;
    }

    public Battlefield getBattlefield(String gameId) {
        return idBattlefieldMap.get(gameId);
    }

    public void onOutgoingNewGameRequest(NewGame request, NewGame response) {

        Game game = new Game();

        game.setOpponentId(response.getUserId());
        game.setOpponentName(response.getFullName());
        game.setOpponentProtocol(request.getProtocol()); // provided by own user
        game.setOpponentBoard(emptyOpponentBoard());
        game.setStatus(GameStatus.Status.player_turn);

        game.setUserId(request.getUserId());
        game.setUserName(request.getFullName());
        game.setUserProtocol(protocolService.getOwnProtocol());

        game.setRules(request.getRules());
        game.setGameOwner(response.getStarting());
        game.setGameId(response.getGameId());

        idGameMap.put(response.getGameId(), game);

        idBattlefieldMap.put(response.getGameId(), battlefieldFactory.createRandom());

    }

    private String[][] emptyOpponentBoard() {
        String[][] opponentBoard = new String[16][16];
        DoubleArrays.fill(opponentBoard, ".");
        return opponentBoard;
    }

    public void onIncomingNewGameRequest(NewGame request, NewGame response) {

        Game game = new Game();

        game.setOpponentId(request.getUserId());
        game.setOpponentName(request.getFullName());
        game.setOpponentProtocol(request.getProtocol());
        game.setOpponentBoard(emptyOpponentBoard());

        game.setUserId(response.getUserId());
        game.setUserName(response.getFullName());
        game.setUserProtocol(protocolService.getOwnProtocol());

        game.setRules(request.getRules());
        game.setGameOwner(response.getStarting());
        game.setGameId(response.getGameId());
        game.setStatus(GameStatus.Status.player_turn);

        idGameMap.put(response.getGameId(), game);

        idBattlefieldMap.put(response.getGameId(), battlefieldFactory.createRandom());
    }

    public ActiveGames setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

    public boolean containsGame(String gameId) {
        return idGameMap.containsKey(gameId)
                && idBattlefieldMap.containsKey(gameId);
    }

    public boolean isOpponentsTurn(String gameId) {
        Game game = idGameMap.get(gameId);
        return game.getGameOwner().equals(game.getOpponentId());
    }

    public Game getGame(String gameId) {
        return idGameMap.get(gameId);
    }

    public void onError(String gameId) {
        idGameMap.remove(gameId);
        idBattlefieldMap.remove(gameId);
    }

    public ActiveGames setProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
        return this;
    }

    public void firedAt(String gameId, FiringResponse firingResponse) {
        Game game = idGameMap.get(gameId);
        firingResponse.getShots().forEach((coordinateStr, damage) -> {
            Coordinates coordinates = CoordinatesFormatter.fromProtocolString(coordinateStr);
            updateOpponentBoard(game, coordinates, damage);
        });
        game.setGameOwner(firingResponse.getGame().getOwner());
    }

    private void updateOpponentBoard(Game game, Coordinates coordinates, Shot.Damage damage) {
        String[][] opponentBoard = game.getOpponentBoard();
        String result = Shot.Damage.MISS == damage ? "-" : "X";
        opponentBoard[coordinates.row()][coordinates.column()] = result;
    }
}
