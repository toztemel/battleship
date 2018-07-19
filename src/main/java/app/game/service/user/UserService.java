package app.game.service.user;

import app.game.api.security.BattleshipAPIRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.sql.Date;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

public class UserService {

    private static UserService instance = new UserService();

    private UserServiceConf conf;

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public String ownUserId() {
        return conf.defaultUserId();
    }

    public String ownFullName() {
        return conf.defaultUserName();
    }

    public String signUser(String userId, String gameId) {
        return sign(userId, gameId, BattleshipAPIRoles.USER);
    }

    public String signProtocol(String userId, String gameId) {
        return sign(userId, gameId, BattleshipAPIRoles.PROTOCOL_RESTRICTED);
    }

    private String sign(String userId, String gameId, BattleshipAPIRoles role) {
        return Jwts.builder()
                .setIssuer("Lunatech")
                .setSubject("Battleship")
                .claim("role", role)
                .claim("user", userId)
                .claim("gameId", gameId)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, DAYS)))
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(conf.signature()))
                .compact();
    }

    public BattleshipAPIRoles checkLogin(String compactJws, String gameId) {
        try {
            Claims claims = Jwts.parser()
                    .requireSubject("Battleship")
                    .requireIssuer("Lunatech")
                    .setSigningKey(conf.signature())
                    .parseClaimsJws(compactJws)
                    .getBody();

            if (claims.get("gameId").equals(gameId)) {
                String scope = claims.get("role", String.class);
                return BattleshipAPIRoles.valueOf(scope);
            }

        } catch (Exception ignored) {

        }
        return BattleshipAPIRoles.ANYONE;
    }

    public UserService setUserConf(UserServiceConf conf) {
        this.conf = conf;
        return this;
    }

    public boolean isSecurityDisabled() {
        return !conf.securityEnabled();
    }
}
