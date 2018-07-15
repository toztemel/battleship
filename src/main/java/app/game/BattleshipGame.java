package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.firing.FiringProtocolController;
import app.game.api.game.NewGameProtocolController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;
import app.game.battlefield.Battlefield;
import app.game.fire.Coordinates;
import app.game.ship.SWing;
import app.game.ship.XWing;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

class BattleshipGame {

    private Battlefield battlefield;

    BattleshipGame() {

    }

    void start() {
        startBattlefield();
        startApi();
    }

    private void startBattlefield() {
        battlefield = Battlefield.getInstance()
                .build()
                .print();
    }

    private void startApi() {
        FiringProtocolController fireController = new FiringProtocolController(battlefield);
        NewGameProtocolController newGameController = new NewGameProtocolController();
        UserController userController = new UserController();

        BattleshipAPI.getInstance()
                .listen(HTTP_SERVER_PORT)
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

}
