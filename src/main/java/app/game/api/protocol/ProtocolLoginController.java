package app.game.api.protocol;

import app.game.api.dto.security.Login;
import app.game.service.user.UserService;
import io.javalin.Context;

import static app.game.api.security.SecurityConstants.*;

public class ProtocolLoginController {

    private UserService userService;

    public void login(Context ctx) {
        if (userService.isSecurityDisabled()) {
            ctx.status(201);
            return;
        }
        Login login = ctx.bodyAsClass(Login.class);
        String jwt = userService.signProtocol(login.getUserId(), login.getGameId());
        ctx.status(201)
                .header(HEADER_AUTHORIZATION, encodeAuthorization(jwt))
                .header(HEADER_GAME_ID, login.getGameId());
    }

    public ProtocolLoginController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
