package app.game.api.security;

public final class SecurityConstants {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_GAME_ID = "gameId";

    public static final String UNAUTHORIZED_ERROR = "Unauthorized";

    private SecurityConstants() {
    }

    public static String encodeAuthorization(String jwt) {
        return "Bearer " + jwt;
    }

    public static String decodeAuthorization(String token) {
        return token.substring("Bearer ".length());
    }

}
