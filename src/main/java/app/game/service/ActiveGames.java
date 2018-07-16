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

    public void putBattlefield(String gameId) {
        idBattlefieldMap.put(gameId, battlefieldFactory.createEmpty());
    }

    public ActiveGames setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

    public void onNewGameRequestReceived(NewGame request, NewGame response) {
        Game game = new Game();
        game.setOpponentId(request.getUserId());
        game.setOpponentName(request.getFullName());
        game.setOpponentProtocol(request.getProtocol());
        game.setOpponentBoard(new String[16][16]);
        game.setRules(request.getRules());
        game.setGameOwner(request.getStarting());
        game.setGameId(response.getGameId());
        game.setUserId(response.getUserId());
        game.setUserName(response.getFullName());
        game.setMode(GameStatus.Mode.player_turn);
        game.setUserBoard(new String[16][16]);

        idGameMap.put(game.getGameId(), game);

        putBattlefield(game.getGameId());
    }
}
