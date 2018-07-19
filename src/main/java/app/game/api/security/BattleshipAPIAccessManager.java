package app.game.api.security;

import app.game.api.ResourcePath;
import app.game.service.user.UserService;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static app.game.api.security.SecurityConstants.*;

public class BattleshipAPIAccessManager {

    private static final Logger LOG = LoggerFactory.getLogger(BattleshipAPIAccessManager.class);

    private UserService userService;

    public void manageAccess(Handler handler, Context ctx, List<Role> permittedRoles) throws Exception {
        if (userService.isSecurityDisabled()) {
            handler.handle(ctx);
        } else {
            doFilterAccess(handler, ctx, permittedRoles);
        }
    }

    private void doFilterAccess(Handler handler, Context ctx, List<Role> permittedRoles) throws Exception {
        BattleshipAPIRoles apiRole = getApiRole(ctx);
        if (permittedRoles.contains(apiRole)) {
            handler.handle(ctx);
        } else {
            LOG.warn("Unauthorized request for ", ctx.param("gameId"));
            ctx.status(401).result(UNAUTHORIZED_ERROR);
        }
    }

    private BattleshipAPIRoles getApiRole(Context context) {
        if (isAuthHeaderSet(context)) {
            String token = getAuthHeader(context);
            if (token != null) {
                return userService.checkLogin(decodeAuthorization(token), context.param("gameId"));
            }
        } else if (isProtocolRequest(context)) {
            return BattleshipAPIRoles.PROTOCOL;
        }
        return BattleshipAPIRoles.ANYONE;
    }

    private String getAuthHeader(Context context) {
        return context.header(HEADER_AUTHORIZATION);
    }

    private boolean isProtocolRequest(Context context) {
        return context.uri().startsWith(ResourcePath.Protocol.BASE);
    }

    private boolean isAuthHeaderSet(Context context) {
        return context.headerMap().containsKey(HEADER_AUTHORIZATION);
    }

    public BattleshipAPIAccessManager setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
