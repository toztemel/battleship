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
        if (1 < args.length) {
            userConf.securityEnabled(Boolean.parseBoolean(args[1]));
        }
        if (2 < args.length) {
            userConf.defaultUserId(args[2]);
        }
        if (3 < args.length) {
            userConf.defaultUserName(args[3]);
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
