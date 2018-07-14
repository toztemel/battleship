package app.game.api.util;

public class ResourcePath {

    public static class Protocol {
        public static final String NEW_GAME = "/protocol/app.game/new";
        public static final String FIRE = "/protocol/app.game/:gameId";
    }

    public static class User {
        public static final String STATUS = "/user/app.game/:gameId";
        public static final String NEW_GAME = "/user/app.game/new";
        public static final String FIRE = "/user/app.game/:gameId/hitBy";
        public static final String AUTO = "/user/app.game/:gameId/auto";
    }
}
