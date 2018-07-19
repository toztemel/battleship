package app.game.api;

public class ResourcePath {

    public static class Protocol {
        public static final String BASE = "/protocol/game/";
        public static final String NEW_GAME = "/protocol/game/new";
        public static final String FIRE = "/protocol/game/:gameId";
    }

    static class User {
        static final String NEW_GAME = "/user/game/new";
        static final String STATUS = "/user/game/:gameId";
        static final String FIRE = "/user/game/:gameId/fire";
        static final String AUTO = "/user/game/:gameId/auto";
    }
}
