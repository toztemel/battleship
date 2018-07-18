package app.game.api.user;

import app.game.api.client.BattleshipClient;
import app.game.service.UserService;
import app.game.service.cache.GameCacheService;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAutoController {

    private static Logger LOG = LoggerFactory.getLogger(UserAutoController.class);

    private BattleshipClient battleshipClient;
    private GameCacheService gameCacheService;
    private UserService userService;

    public void auto(Context context) {
        try {
            // do nothing yet
        } catch (Exception e) {
            LOG.error("Error occured in auto.", e);
            throw new UserApiException(e);
        }

    }

    public UserAutoController setBattleshipClient(BattleshipClient battleshipClient) {
        this.battleshipClient = battleshipClient;
        return this;
    }

    public UserAutoController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

    public UserAutoController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

}
