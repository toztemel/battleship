package app.game.api.user;

import app.game.api.dto.status.GameStatus;
import app.game.api.dto.status.OpponentStatus;
import app.game.api.dto.status.SelfStatus;
import app.game.api.dto.status.StatusResponse;
import app.game.service.cache.Game;
import app.game.service.cache.GameCacheService;
import app.game.util.DoubleArrays;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserStatusController {

    private static Logger LOG = LoggerFactory.getLogger(UserStatusController.class);

    private GameCacheService gameCacheService;

    public void onStatus(Context ctx) {
        try {
            String gameId = ctx.param("gameId");

            validateGameId(gameId);

            Game cachedGame = gameCacheService.getGame(gameId);

            GameStatus game = new GameStatus();
            game.setOwner(cachedGame.getGameOwner());
            game.setStatus(cachedGame.getGameStatus());

            SelfStatus self = new SelfStatus();
            self.setUserId(cachedGame.getUserId());
            self.setBoard(ownBattlefieldAsStringArray(gameId));

            OpponentStatus opponent = new OpponentStatus();
            opponent.setUserId(cachedGame.getOpponentId());
            opponent.setBoard(opponentBattlefieldAsStringArray(cachedGame));

            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setGame(game);
            statusResponse.setSelf(self);
            statusResponse.setOpponent(opponent);

            ctx.status(200).json(statusResponse);
            LOG.info("Return statusResponse of game Id=", gameId);
        } catch (Exception e) {
            LOG.error("Error occured during status check.", e);
            throw new UserApiException(e);
        }
    }

    private void validateGameId(String gameId) {
        if (!gameCacheService.containsGame(gameId)) {
            throw new UserApiException("Game not found with id:" + gameId);
        }
    }

    private String[] ownBattlefieldAsStringArray(String gameId) {
        return gameCacheService.getBattlefield(gameId).asString();
    }

    private String[] opponentBattlefieldAsStringArray(Game cachedGame) {
        String[][] opponentBoard = cachedGame.getOpponentBoard();
        return DoubleArrays.asString(opponentBoard);
    }

    public UserStatusController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

}
