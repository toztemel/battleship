package app.game.api.user;

import app.game.api.dto.security.Login;
import app.game.api.protocol.client.ProtocolApiClient;
import app.game.service.UserService;
import app.game.service.cache.GameCacheService;
import io.javalin.Context;

import static app.game.api.security.SecurityConstants.HEADER_AUTHORIZATION;
import static app.game.api.security.SecurityConstants.encodeAuthorization;

public class UserLoginController {

    private UserService userService;
    private ProtocolApiClient protocolApiClient;
    private GameCacheService gameCacheService;

    public void login(Context ctx) {
        try {
            Login login = ctx.bodyAsClass(Login.class);

            String opponentProtocol = gameCacheService.getGame(login.getGameId())
                    .getOpponentProtocol();

            protocolApiClient.target(opponentProtocol).loginRequest(login);

            String jwt = userService.signUser(login.getUserId(), login.getGameId());

            ctx.status(201)
                    .header(HEADER_AUTHORIZATION, encodeAuthorization(jwt));
        } catch (Exception e) {
            throw new UserApiException(e);
        }
    }

    public UserLoginController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public UserLoginController setProtocolApiClient(ProtocolApiClient protocolApiClient) {
        this.protocolApiClient = protocolApiClient;
        return this;
    }

    public UserLoginController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }
}
