package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.firing.FiringProtocolController;
import app.game.api.game.NewGameProtocolController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;
import app.game.battlefield.Battlefield;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

class BattleshipGame {

    private Battlefield battlefield;

    BattleshipGame() {

    }

    void start() {
        start(HTTP_SERVER_PORT);
    }

    void start(int port) {
        startBattlefield();
        startApi(port);
    }

    private void startBattlefield() {
        battlefield = Battlefield.getInstance()
                .build();
    }

    private void startApi(int httpServerPort) {
        FiringProtocolController fireController = new FiringProtocolController(battlefield);
        NewGameProtocolController newGameController = new NewGameProtocolController();
        UserController userController = new UserController(battlefield);

        BattleshipAPI.getInstance()
                .listen(httpServerPort)
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
        BattleshipAPI.getInstance().stop();
    }

    Battlefield battlefield() {
        return battlefield;
    }
}
