package app.game.conf;

public final class UserConf {

    private static final String DEFAULT_USER_ID = "challenger-Y";
    private static final String DEFAULT_FULL_NAME = "Lunatech FR Champion";

    public String defaultUserId() {
        return DEFAULT_USER_ID;
    }

    public String defaultUserName() {
        return DEFAULT_FULL_NAME;
    }

}
