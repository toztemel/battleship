package app.game.api;

import app.game.api.ResourcePath.Protocol;
import app.game.api.ResourcePath.User;
import app.game.api.protocol.ProtocolApiException;
import app.game.api.protocol.client.ProtocolApiClientException;
import app.game.api.security.BattleshipAPIRoles;
import app.game.api.user.UserApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.ErrorHandler;
import io.javalin.Handler;
import io.javalin.Javalin;
import io.javalin.security.AccessManager;
import io.javalin.translator.json.JavalinJacksonPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static io.javalin.security.Role.roles;

public class BattleshipAPI {

    private static final BattleshipAPI instance = new BattleshipAPI();
    private static final Logger LOG = LoggerFactory.getLogger(BattleshipAPI.class);

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
                .exception(ProtocolApiClientException.class, (e, ctx) -> {
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

    public BattleshipAPI listen(BattlefieldAPIConf conf) {
        app = Javalin.create().port(conf.httpServerPort());
        return this;
    }

    public BattleshipAPI withMapper(Supplier<ObjectMapper> s) {
        JavalinJacksonPlugin.configure(s.get());
        return this;
    }

    public BattleshipAPI accessManager(AccessManager handler) {
        app.accessManager(handler);
        return this;
    }

    public BattleshipAPI onProtocolNewGame(Handler handler) {
        app.post(Protocol.NEW_GAME, handler, roles(BattleshipAPIRoles.PROTOCOL));
        return this;
    }

    public BattleshipAPI onProtocolFire(Handler handler) {
        app.put(Protocol.FIRE, handler, roles(BattleshipAPIRoles.PROTOCOL_RESTRICTED));
        return this;
    }

    public BattleshipAPI onProtocolLogin(Handler handler) {
        app.post(Protocol.LOGIN, handler, roles(BattleshipAPIRoles.PROTOCOL));
        return this;
    }

    public BattleshipAPI onUserStartNewGame(Handler newGame) {
        app.post(User.NEW_GAME, newGame, roles(BattleshipAPIRoles.ANYONE));
        return this;
    }

    public BattleshipAPI onUserAsksStatus(Handler statusHandler) {
        app.get(User.STATUS, statusHandler, roles(BattleshipAPIRoles.USER));
        return this;
    }

    public BattleshipAPI onUserFires(Handler userFireHandler) {
        app.put(User.FIRE, userFireHandler, roles(BattleshipAPIRoles.USER));
        return this;
    }

    public BattleshipAPI onUserEnablesAutoPilot(Handler autoHandler) {
        app.put(User.AUTO, autoHandler, roles(BattleshipAPIRoles.USER));
        return this;
    }

    public BattleshipAPI onUserLogin(Handler autoHandler) {
        app.post(User.LOGIN, autoHandler, roles(BattleshipAPIRoles.ANYONE));
        return this;
    }

    public BattleshipAPI on400Error(ErrorHandler errorHandler) {
        app.error(400, errorHandler);
        return this;
    }

}
