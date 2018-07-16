package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.controller.FiringProtocolController;
import app.game.api.controller.NewGameProtocolController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.controller.UserController;
import app.game.battlefield.Battlefield;
import app.game.battlefield.BattlefieldFactory;
import org.slf4j.LoggerFactory;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

class BattleshipGame {

    final org.slf4j.Logger log = LoggerFactory.getLogger(BattleshipGame.class);
    private Battlefield battlefield;
    private BattleshipAPI api;

    {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
    }


    BattleshipGame() {
//        log.trace("trace");
//        log.debug("debug");
//        log.info("info");
//        log.warn("warning");
//        log.error("error");
    }

    void start() {
        start(HTTP_SERVER_PORT);
    }

    void start(int port) {
        startBattlefield();
        startApi(port);
    }

    private void startBattlefield() {
        battlefield = new BattlefieldFactory().createNew();
    }

    private void startApi(int httpServerPort) {
        api = new BattleshipAPI();
        FiringProtocolController fireController = new FiringProtocolController(battlefield);
        NewGameProtocolController newGameController = new NewGameProtocolController();
        UserController userController = new UserController(battlefield);

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

    Battlefield battlefield() {
        return battlefield;
    }
}
