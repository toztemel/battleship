package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.firing.FireController;
import app.game.api.game.NewGameController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.user.UserController;

import static app.game.conf.HTTPServerConf.HTTP_SERVER_PORT;

public class BattleshipGame {

    BattleshipGame() {

    }

    void start() {
        startApi();
    }

    private void startApi() {
        FireController fireController = new FireController();
        NewGameController newGameController = new NewGameController();
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
