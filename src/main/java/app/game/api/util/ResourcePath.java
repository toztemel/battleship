package app.game.api.util;

public class ResourcePath {

    public static class Protocol {
        public static final String NEW_GAME = "/protocol/game/new";
        public static final String FIRE = "/protocol/game/:gameId";
    }

    public static class User {
        public static final String STATUS = "/user/game/:gameId";
        public static final String NEW_GAME = "/user/game/new";
        public static final String FIRE = "/user/game/:gameId/fire";
        public static final String AUTO = "/user/game/:gameId/auto";
    }
}
