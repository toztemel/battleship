package app.game.api.user;

import app.game.api.protocol.client.ProtocolApiClient;
import app.game.api.dto.game.NewGame;
import app.game.service.ProtocolService;
import app.game.service.UserService;
import app.game.service.cache.GameCacheService;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNewGameController {

    private static Logger LOG = LoggerFactory.getLogger(UserNewGameController.class);

    private ProtocolApiClient protocolApiClient;
    private GameCacheService gameCacheService;
    private UserService userService;
    private ProtocolService protocolService;

    public void onNewGame(Context ctx) {
        try {
            NewGame userRequest = ctx.bodyAsClass(NewGame.class);
            NewGame serverRequest = new NewGame();
            serverRequest.setRule(userRequest.getRule());
            serverRequest.setUserId(userRequest.getUserId());
            serverRequest.setFullName(userRequest.getFullName());
            serverRequest.setProtocol(protocolService.getOwnProtocol());
            NewGame opponentResponse = protocolApiClient.target(userRequest.getProtocol())
                    .challengeOpponent(serverRequest);
            gameCacheService.onOutgoingNewGameRequest(userRequest, opponentResponse);
            ctx.status(201).json(opponentResponse);
            LOG.info("Created new game. Id=", opponentResponse.getGameId());
        } catch (Exception e) {
            LOG.error("Error occured onNewGame:", e);
            throw new UserApiException(e);
        }
    }

    public UserNewGameController setProtocolApiClient(ProtocolApiClient protocolApiClient) {
        this.protocolApiClient = protocolApiClient;
        return this;
    }

    public UserNewGameController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

    public UserNewGameController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public UserNewGameController setProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
        return this;
    }
}
