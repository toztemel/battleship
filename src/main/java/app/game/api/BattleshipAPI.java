package app.game.api;

import app.game.api.firing.FireController;
import app.game.api.game.NewGameController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;
import app.game.api.util.ResourcePath.Protocol;
import app.game.api.util.ResourcePath.User;
import io.javalin.Javalin;
import io.javalin.translator.json.JavalinJacksonPlugin;

public class BattleshipAPI {

    private static final int HTTP_SERVER_PORT = 7000;
    private static BattleshipAPI battleshipAPI = new BattleshipAPI();

    private Javalin app;

    private BattleshipAPI() {
    }

    public static BattleshipAPI getInstance() {
        return battleshipAPI;
    }

    public void stop() {
        app.stop();
    }

    private void start(int port) {
        app = Javalin.start(port);

        JavalinJacksonPlugin.configure(new BattleshipObjectMapper().getDefaultObjectMapper());

        app.post(Protocol.NEW_GAME, new NewGameController().newGameHandler());

        app.put(Protocol.FIRE, new FireController().firingHandler());

        app.get(User.STATUS, UserController::getGameStatus);

        app.post(User.NEW_GAME, UserController::newGame);

        app.put(User.FIRE, UserController::fire);

        app.put(User.AUTO, UserController::auto);
    }

    public void start() {
        start(HTTP_SERVER_PORT);
    }

}
