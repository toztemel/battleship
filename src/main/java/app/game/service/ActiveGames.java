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

    public void newGame(NewGame newGame, NewGame newGameResponse) {
        Game game = new Game();
        game.setOpponentId(newGame.getUserId());
        game.setOpponentName(newGame.getFullName());
        game.setOpponentProtocol(newGame.getProtocol());
        game.setOpponentBoard(new String[16][16]);
        game.setRules(newGame.getRules());
        game.setGameOwner(newGame.getStarting());
        game.setGameId(newGameResponse.getGameId());
        game.setUserId(newGameResponse.getUserId());
        game.setUserName(newGameResponse.getFullName());
        game.setMode(GameStatus.Mode.player_turn);
        game.setUserBoard(new String[16][16]);

        idGameMap.put(game.getGameId(), game);

        putBattlefield(game.getGameId());
    }

    public Battlefield getBattlefield(String gameId) {
        return idBattlefieldMap.get(gameId);
    }

    public void putBattlefield(String gameId) {
        Battlefield bf = battlefieldFactory.createNew();
        idBattlefieldMap.put(gameId, bf);
    }

    public ActiveGames setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

    public void newGameInvitation(NewGame request, NewGame newGame) {
        newGame(request, newGame);
    }
}
