package app.game.service.cache;

import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.battlefield.BattlefieldFactory;
import app.game.fire.Coordinates;
import app.game.service.Game;
import app.game.service.ProtocolService;
import app.game.util.DoubleArrays;

public final class GameCacheService {

    private static GameCacheService instance = new GameCacheService();

    private GameCache gameCache;
    private BattlefieldFactory battlefieldFactory;
    private ProtocolService protocolService;

    private GameCacheService() {
        gameCache = new GameCache()
                .setBattlefieldFactory(battlefieldFactory);
    }

    public static GameCacheService getInstance() {
        return instance;
    }

    public Battlefield getBattlefield(String gameId) {
        return gameCache.getBattlefield(gameId);
    }

    public void onOutgoingNewGameRequest(NewGame request, NewGame response) {

        Game game = new Game();

        game.setOpponentId(response.getUserId());
        game.setOpponentName(response.getFullName());
        game.setOpponentProtocol(request.getProtocol()); // provided by own request
        game.setOpponentBoard(emptyBoard());

        game.setUserId(request.getUserId());
        game.setUserName(request.getFullName());
        game.setUserProtocol(protocolService.getOwnProtocol());

        game.setGameId(response.getGameId());
        game.setGameOwner(response.getStarting());
        game.setGameRule(request.getRule());
        game.setGameStatus(GameStatus.Status.player_turn);

        gameCache.cacheGame(game);
    }

    public void onIncomingNewGameRequest(NewGame opponent, NewGame user) {

        Game game = new Game();

        game.setOpponentId(opponent.getUserId());
        game.setOpponentName(opponent.getFullName());
        game.setOpponentProtocol(opponent.getProtocol()); // provided by the requester opponent
        game.setOpponentBoard(emptyBoard());

        game.setUserId(user.getUserId());
        game.setUserName(user.getFullName());
        game.setUserProtocol(protocolService.getOwnProtocol());

        game.setGameId(user.getGameId());
        game.setGameOwner(user.getStarting());
        game.setGameRule(opponent.getRule());
        game.setGameStatus(GameStatus.Status.player_turn);

        gameCache.cacheGame(game);
    }

    public void onOutgoingFireRequest(String gameId, FiringResponse firingResponse) {
        Game game = gameCache.getGame(gameId);
        updateOpponentBoard(firingResponse, game);
        game.setGameOwner(firingResponse.getGame().getOwner());
    }

    private void updateOpponentBoard(FiringResponse firingResponse, Game game) {
        firingResponse.getShots()
                .forEach((coordinateStr, damage) -> {
                    Coordinates coordinates = Coordinates.fromProtocolString(coordinateStr);
                    gameCache.updateOpponentDamage(game, coordinates, damage);
                });
    }

    private String[][] emptyBoard() {
        String[][] opponentBoard = new String[16][16];
        DoubleArrays.fill(opponentBoard, ".");
        return opponentBoard;
    }

    public boolean containsGame(String gameId) {
        return gameCache.containsGame(gameId);
    }

    public boolean isOpponentsTurn(String gameId) {
        return gameCache.isOpponentsTurn(gameId);
    }

    public Game getGame(String gameId) {
        return gameCache.getGame(gameId);
    }

    public void onError(String gameId) {
        gameCache.removeGame(gameId);
    }

    public GameCacheService setProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
        return this;
    }

    public GameCacheService setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

}
