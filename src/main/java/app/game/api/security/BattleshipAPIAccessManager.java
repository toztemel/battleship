package app.game.api.security;

import app.game.service.UserService;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class BattleshipAPIAccessManager {

    private static final Logger LOG = LoggerFactory.getLogger(BattleshipAPIAccessManager.class);
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private UserService userService;

    public void manageAccess(Handler handler, Context ctx, List<Role> permittedRoles) throws Exception {
        BattleshipAPIRoles userRole = getUserRole(ctx);
        if (permittedRoles.contains(userRole)) {
            handler.handle(ctx);
        } else {
            LOG.warn("Unauthorized request for ", ctx.param("gameId"));
            ctx.status(401).result("Unauthorized");
        }
    }

    private BattleshipAPIRoles getUserRole(Context context) {
        if (context.headerMap().containsKey(HEADER_AUTHORIZATION)) {
            return findUserRole(context);
        }
        return BattleshipAPIRoles.ANYONE;
    }

    private BattleshipAPIRoles findUserRole(Context context) {
        String token = context.header(HEADER_AUTHORIZATION);
        return userService.checkLogin(token.substring("Bearer ".length())
                , context.param("gameId"));
    }

    public BattleshipAPIAccessManager setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
