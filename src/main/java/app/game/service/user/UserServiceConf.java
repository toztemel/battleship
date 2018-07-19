package app.game.service.user;

public final class UserServiceConf {

    private static final String DEFAULT_USER_ID = "challenger-Y";
    private static final String DEFAULT_FULL_NAME = "Lunatech FR Champion";
    private static final String SIGNATURE = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private String userId = DEFAULT_USER_ID;
    private String userName = DEFAULT_FULL_NAME;
    private boolean securityEnabled = true;

    String signature() {
        return SIGNATURE;
    }

    String defaultUserId() {
        return userId;
    }

    String defaultUserName() {
        return userName;
    }

    boolean securityEnabled() {
        return securityEnabled;
    }

    public void defaultUserId(String userId) {
        this.userId = userId;
    }

    public void defaultUserName(String userName) {
        this.userName = userName;
    }

    public void securityEnabled(boolean b) {
        securityEnabled = b;
    }
}
