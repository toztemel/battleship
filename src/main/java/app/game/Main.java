package app.game;

import app.game.conf.HTTPServerConf;

public class Main {

    public static void main(String... args) {
        configureLogger();

        BattleshipGame game = new BattleshipGame();

        if (null != args[0]) {
            int port = Integer.parseInt(args[0]);
            HTTPServerConf httpServerConf = new HTTPServerConf().httpServerPort(port);
            game.start(httpServerConf);
        } else {
            game.start();
        }
    }

    private static void configureLogger() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
    }

}
