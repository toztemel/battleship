package app.game.api;

import app.game.api.ResourcePath.Protocol;
import app.game.api.ResourcePath.User;
import app.game.api.controller.ProtocolApiException;
import app.game.api.controller.UserApiException;
import app.game.conf.HTTPServerConf;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.ErrorHandler;
import io.javalin.Handler;
import io.javalin.Javalin;
import io.javalin.translator.json.JavalinJacksonPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class BattleshipAPI {

    private static final Logger LOG = LoggerFactory.getLogger(BattleshipAPI.class);
    private static final BattleshipAPI instance = new BattleshipAPI();

    private Javalin app;

    private BattleshipAPI() {
    }

    public static BattleshipAPI getInstance() {
        return instance;
    }

    public BattleshipAPI start() {
        app.disableStartupBanner()
                .disableRequestCache()
                .enableStandardRequestLogging()
                .exception(ProtocolApiException.class, (e, ctx) -> {
                    ctx.status(400).result(e.getUserMessage());
                    LOG.error(e.getMessage());
                })
                .exception(UserApiException.class, (e, ctx) -> {
                    ctx.status(200).result(e.getUserMessage());
                    LOG.error(e.getMessage());
                })
                .start();
        return this;
    }

    public void stop() {
        app.stop();
    }

    public BattleshipAPI listen(HTTPServerConf conf) {
        app = Javalin.create().port(conf.httpServerPort());
        return this;
    }

    public BattleshipAPI withMapper(Supplier<ObjectMapper> s) {
        JavalinJacksonPlugin.configure(s.get());
        return this;
    }

    public BattleshipAPI onProtocolNewGame(Handler handler) {
        app.post(Protocol.NEW_GAME, handler);
        return this;
    }

    public BattleshipAPI onProtocolFire(Handler handler) {
        app.put(Protocol.FIRE, handler);
        return this;
    }

    public BattleshipAPI onUserStartNewGame(Handler newGame) {
        app.post(User.NEW_GAME, newGame);
        return this;
    }

    public BattleshipAPI onUserAsksStatus(Handler statusHandler) {
        app.get(User.STATUS, statusHandler);
        return this;
    }

    public BattleshipAPI onUserFires(Handler userFireHandler) {
        app.put(User.FIRE, userFireHandler);
        return this;
    }

    public BattleshipAPI onUserEnablesAutoPilot(Handler autoHandler) {
        app.put(User.AUTO, autoHandler);
        return this;
    }

    public BattleshipAPI on400Error(ErrorHandler errorHandler) {
        app.error(400, errorHandler);
        return this;
    }

}
