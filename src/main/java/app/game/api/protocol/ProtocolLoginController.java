package app.game.api.protocol;

import app.game.api.dto.security.Login;
import app.game.service.UserService;
import io.javalin.Context;

public class ProtocolLoginController {
    private UserService userService;

    public void login(Context ctx) {
        Login login = ctx.bodyAsClass(Login.class);
        String jwt = userService.signProtocol(login.getUserId(), login.getGameId());
        ctx.status(201)
                .header("Authorization", "Bearer " + jwt)
                .header("gameId", login.getGameId());
    }

    public ProtocolLoginController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
