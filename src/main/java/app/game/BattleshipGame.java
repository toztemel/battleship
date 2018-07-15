package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.firing.FireProtocolController;
import app.game.api.game.NewGameProtocolController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

class BattleshipGame {

    BattleshipGame() {

    }

    void start() {
        startApi();
    }

    private void startApi() {
        FireProtocolController fireController = new FireProtocolController();
        NewGameProtocolController newGameController = new NewGameProtocolController();
        UserController userController = new UserController();

        BattleshipAPI.getInstance()
                .listen(HTTP_SERVER_PORT)
                .withMapper(() -> new BattleshipObjectMapper().getDefaultObjectMapper())
                .onProtocolFire(fireController::fireProtocol)
                .onProtocolNewGame(newGameController::newGameProtocol)
                .onUserStartNewGame(userController::newGame)
                .onUserAsksStatus(userController::statusHandler)
                .onUserFires(userController::fire)
                .onUserEnablesAutoPilot(userController::auto)
                .start();
    }

    void stop() {
        BattleshipAPI.getInstance().stop();
    }

}
