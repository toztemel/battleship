package app.game.api;

public class ResourcePath {

    public static class Protocol {
        public static final String NEW_GAME = "/protocol/game/new";
        public static final String FIRE = "/protocol/game/:gameId";
    }

    public static class User {
        public static final String USER_API = "/user/game/";
        public static final String STATUS = "/user/game/:gameId";
        public static final String NEW_GAME = "/user/game/new";
        public static final String FIRE = "/user/game/:gameId/hitBy";
        public static final String AUTO = "/user/game/:gameId/auto";
    }
}
