package app.game.api;

import app.game.api.ResourcePath.Protocol;
import app.game.api.ResourcePath.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Handler;
import io.javalin.Javalin;
import io.javalin.translator.json.JavalinJacksonPlugin;

import java.util.function.Supplier;

public class BattleshipAPI {

    private static BattleshipAPI instance = new BattleshipAPI();

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
                .start();
        return this;
    }

    public void stop() {
        Javalin stop = app.stop();
    }

    public BattleshipAPI listen(int httpPort) {
        app = Javalin.create().port(httpPort);
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

}
