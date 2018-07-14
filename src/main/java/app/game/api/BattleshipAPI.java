package app.game.api;

import app.game.api.ResourcePath.Protocol;
import app.game.api.ResourcePath.User;
import app.game.api.firing.FireController;
import app.game.api.game.NewGameController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;
import io.javalin.Javalin;
import io.javalin.translator.json.JavalinJacksonPlugin;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

public class BattleshipAPI {

    private static BattleshipAPI instance = new BattleshipAPI();

    private Javalin app;

    private BattleshipAPI() {
    }

    public static BattleshipAPI getInstance() {
        return instance;
    }

    public void stop() {
        app.stop();
    }

    public void start() {
        start(HTTP_SERVER_PORT);
    }

    private void start(int port) {
        app = Javalin.start(port);

        configureJsonMapper();

        app.post(Protocol.NEW_GAME, NewGameController::newGameHandler);

        app.put(Protocol.FIRE, FireController::firingHandler);

        app.get(User.STATUS, UserController::statusHandler);

        app.post(User.NEW_GAME, UserController::newGame);

        app.put(User.FIRE, UserController::fire);

        app.put(User.AUTO, UserController::auto);
    }

    private void configureJsonMapper() {
        JavalinJacksonPlugin.configure(new BattleshipObjectMapper().getDefaultObjectMapper());
    }


}
