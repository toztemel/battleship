package app.game.api.user;

import app.game.api.protocol.client.ProtocolApiClient;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.service.user.UserService;
import app.game.service.cache.GameCacheService;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFiringController {

    private static Logger LOG = LoggerFactory.getLogger(UserFiringController.class);

    private ProtocolApiClient protocolApiClient;
    private GameCacheService gameCacheService;
    private UserService userService;

    public void onFire(Context context) {
        String gameId = context.param("gameId");
        try {
            validateGameId(gameId);
            validateGameStatus(gameId);
            FiringRequest firingRequest = context.bodyAsClass(FiringRequest.class);
            validateGameRules(gameId, firingRequest);

            String opponentProtocol = gameCacheService.getGame(gameId).getOpponentProtocol();
            FiringResponse firingResponse = protocolApiClient.target(opponentProtocol)
                    .fire(gameId, firingRequest);

            gameCacheService.onOutgoingFireRequest(gameId, firingResponse);

            context.status(200).json(firingResponse);
        } catch (Exception e) {
            LOG.error("Error occured while firing.", e);
            throw e;
        }
    }

    private void validateGameId(String gameId) {
        boolean activeGame = gameCacheService.containsGame(gameId);
        if (!activeGame) {
            throw new UserApiException("Game not found with id:" + gameId);
        }
    }

    private void validateGameStatus(String gameId) {
        if (gameCacheService.isOpponentsTurn(gameId)) {
            throw new UserApiException("Owner cannot shoot. It is opponent's turn");
        }
    }

    // TODO check game rules
    private void validateGameRules(String gameId, FiringRequest incomingShots) throws IllegalArgumentException {
        if (false) {
            throw new UserApiException("Invalid number of shots in gameRules:");
        }
    }

    public UserFiringController setProtocolApiClient(ProtocolApiClient protocolApiClient) {
        this.protocolApiClient = protocolApiClient;
        return this;
    }

    public UserFiringController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

    public UserFiringController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

}
