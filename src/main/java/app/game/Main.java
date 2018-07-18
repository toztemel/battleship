package app.game;

import app.game.conf.HTTPServerConf;

public class Main {

    public static void main(String... args) {
        configureLogger();
        startGame(args[0]);
    }

    private static void startGame(String arg) {
        HTTPServerConf conf = new HTTPServerConf();
        if (null != arg) {
            conf.httpServerPort(Integer.parseInt(arg));
        }

        new BattleshipGame().start(conf);
    }

    private static void configureLogger() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
    }

}
