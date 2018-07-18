package app.game.service.cache;

import app.game.battlefield.Battlefield;
import app.game.battlefield.BattlefieldFactory;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.service.Game;

import java.util.HashMap;
import java.util.Map;

final class GameCache {

    private Map<String, Game> idGameMap;
    private Map<String, Battlefield> idBattlefieldMap;
    private BattlefieldFactory battlefieldFactory;

    GameCache() {
        idGameMap = new HashMap<>();
        idBattlefieldMap = new HashMap<>();
    }

    boolean containsGame(String gameId) {
        return idGameMap.containsKey(gameId)
                && idBattlefieldMap.containsKey(gameId);
    }

    Battlefield getBattlefield(String gameId) {
        return idBattlefieldMap.get(gameId);
    }

    Game getGame(String gameId) {
        return idGameMap.get(gameId);
    }

    void cacheGame(Game game) {
        idGameMap.put(game.getGameId(), game);
        idBattlefieldMap.put(game.getGameId(), battlefieldFactory.createInstance());
    }

    boolean isOpponentsTurn(String gameId) {
        Game game = idGameMap.get(gameId);
        return game.getGameOwner()
                .equals(game.getOpponentId());
    }

    void removeGame(String gameId) {
        idGameMap.remove(gameId);
        idBattlefieldMap.remove(gameId);
    }

    void updateOpponentDamage(Game game, Coordinates coordinates, Shot.Damage damage) {
        String result = Shot.Damage.MISS == damage ? "-" : "X";
        String[][] opponentBoard = game.getOpponentBoard();
        opponentBoard[coordinates.row()][coordinates.column()] = result;
    }

    GameCache setBattlefieldFactory(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
        return this;
    }

}
