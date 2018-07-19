package app.game;

import app.game.api.BattlefieldAPIConf;
import app.game.service.user.UserServiceConf;

public class Main {

    public static void main(String... args) {
        configureLogger();
        new BattleshipGame().start(apiConf(args), userConf(args));
    }

    private static UserServiceConf userConf(String[] args) {
        UserServiceConf userConf = new UserServiceConf();
        if (args.length == 3) {
            userConf.defaultUserId(args[1]);
            userConf.defaultUserName(args[2]);
        }
        return userConf;
    }

    private static BattlefieldAPIConf apiConf(String[] args) {
        BattlefieldAPIConf apiConf = new BattlefieldAPIConf();
        if (args != null) {
            apiConf.httpServerPort(Integer.parseInt(args[0]));
        }
        return apiConf;
    }

    private static void configureLogger() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
    }

}
