package app.game.api.user;

import app.game.api.MyRole;
import app.game.service.UserService;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.security.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class UserLoginController {

    private static final Logger LOG = LoggerFactory.getLogger(UserLoginController.class);
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static String SIGNATURE = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private UserService userService;

    public void onLogin(Context ctx) {

        String name = "Tayfun Oztemel";
        String scope = MyRole.USER.toString();
        String jws = Jwts.builder()
                .setIssuer("Lunatech")
                .setSubject("Battleship")
                .claim("name", name)
                .claim("role", scope)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, DAYS)))
                .signWith(SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(SIGNATURE))
                .compact();
        ctx.header(HEADER_AUTHORIZATION, "Bearer " + jws);
    }

    MyRole checkLogin(String compactJws) {
        try {

            Claims claims = Jwts.parser()
                    .requireSubject("Battleship")
                    .requireIssuer("Lunatech")
                    .setSigningKey(SIGNATURE)
                    .parseClaimsJws(compactJws)
                    .getBody();

            String name = claims.get("name", String.class);
            String scope = claims.get("role", String.class);
            //OK, we can trust this JWT

            return MyRole.valueOf(scope);
        } catch (Exception ignored) {

        }
        return MyRole.ANYONE;
    }

    public UserLoginController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public void manageAccess(Handler handler, Context ctx, List<Role> permittedRoles) throws Exception {
        MyRole userRole = getUserRole(ctx);
        if (permittedRoles.contains(userRole)) {
            handler.handle(ctx);
        } else {
            ctx.status(401).result("Unauthorized");
        }
    }

    private MyRole getUserRole(Context context) {
        if (context.headerMap().containsKey(HEADER_AUTHORIZATION)) {
            String authorizationHeader = context.header(HEADER_AUTHORIZATION);
            return checkLogin(Objects.requireNonNull(authorizationHeader).substring("Bearer ".length()));
        }
        return MyRole.ANYONE;
    }
}
