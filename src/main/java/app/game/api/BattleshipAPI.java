package app.game.api;

import app.game.api.firing.FireController;
import app.game.api.game.NewGameController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.util.ResourcePath;
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

        app.post(ResourcePath.Protocol.NEW_GAME, new NewGameController().newGameHandler());

        app.put(ResourcePath.Protocol.FIRE, new FireController().firingHandler());
    }

    public void start() {
        start(HTTP_SERVER_PORT);
    }
}
