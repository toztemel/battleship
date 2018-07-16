package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.controller.FiringProtocolController;
import app.game.api.controller.NewGameProtocolController;
import app.game.api.controller.UserController;
import app.game.api.mapper.BattleshipObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

class BattleshipGame {

    private static final Logger log = LoggerFactory.getLogger(BattleshipGame.class);
    private BattleshipAPI api;

    {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
    }

    BattleshipGame() {
    }

    void start() {
        start(HTTP_SERVER_PORT);
    }

    void start(int port) {
        startApi(port);
    }

    private void startApi(int httpServerPort) {
        api = new BattleshipAPI();
        FiringProtocolController fireController = new FiringProtocolController();
        NewGameProtocolController newGameController = new NewGameProtocolController();
        UserController userController = new UserController();

        api.listen(httpServerPort)
                .withMapper(() -> new BattleshipObjectMapper().getDefaultObjectMapper())
                .onProtocolNewGame(newGameController::onNewGame)
                .onProtocolFire(fireController::onFire)
                .onUserStartNewGame(userController::onNewGame)
                .onUserAsksStatus(userController::onStatus)
                .onUserFires(userController::onFire)
                .onUserEnablesAutoPilot(userController::auto)
                .start();
    }

    void stop() {
        api.stop();
    }

}
