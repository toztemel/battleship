package app.game.service;

import app.game.conf.UserConf;

public class UserService {

    private static UserService instance = new UserService();

    private UserConf conf;

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

    public String ownProtocol() {
        return conf.defaultUserProtocol();
    }

    public UserService setUserConf(UserConf conf) {
        this.conf = conf;
        return this;
    }
}
