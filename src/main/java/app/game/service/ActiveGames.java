package app.game.service;

import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.battlefield.BattlefieldFactory;

import java.util.HashMap;
import java.util.Map;

public final class ActiveGames {

    private static ActiveGames instance = new ActiveGames();

    private Map<String, Game> idGameMap;
    private Map<String, Battlefield> idBattlefieldMap;
    private BattlefieldFactory battlefieldFactory;
    private IDGenerator idGenerator;

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

    public String onNewGameResponseReceived(NewGame request, NewGame response) {
        String gameId = idGenerator.generate();

        Game game = new Game();
        game.setOpponentId(request.getUserId());
        game.setOpponentName(request.getFullName());
        game.setOpponentProtocol(request.getProtocol());
        game.setOpponentBoard(new String[16][16]);
        game.setRules(request.getRules());
        game.setGameOwner(request.getStarting());
        game.setGameId(gameId);
        game.setUserId(response.getUserId());
        game.setUserName(response.getFullName());
        game.setMode(GameStatus.Mode.player_turn);
        game.setUserBoard(new String[16][16]);

        idGameMap.put(gameId, game);

        idBattlefieldMap.put(gameId, battlefieldFactory.createEmpty());

        return gameId;
    }

    public ActiveGames setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

    public ActiveGames setIDGeneratorService(IDGenerator instance) {
        this.idGenerator = instance;
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

}
