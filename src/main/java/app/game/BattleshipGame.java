package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.client.BattleshipClient;
import app.game.api.controller.FiringProtocolController;
import app.game.api.controller.NewGameProtocolController;
import app.game.api.controller.UserController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.battlefield.BattlefieldFactory;
import app.game.conf.HTTPServerConf;
import app.game.service.ActiveGames;
import app.game.service.IDGenerator;
import app.game.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BattleshipGame {

    private static final Logger log = LoggerFactory.getLogger(BattleshipGame.class);

    private BattleshipAPI api;

    {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
    }

    BattleshipGame() {
    }

    void start() {
        start(new HTTPServerConf().httpServerPort());
    }

    void start(int port) {
        startApi(port);
    }

    private void startApi(int httpServerPort) {
        api = new BattleshipAPI();

        ActiveGames.getInstance()
                .setBattlefieldFactory(new BattlefieldFactory());

        NewGameProtocolController newGameController = new NewGameProtocolController()
                .setUserService(UserService.getInstance())
                .setActiveGamesService(ActiveGames.getInstance())
                .setIDGeneratorService(IDGenerator.getInstance());

        FiringProtocolController fireController = new FiringProtocolController()
                .setActiveGames(ActiveGames.getInstance());

        UserController userController = new UserController()
                .setUserService(UserService.getInstance())
                .setActiveGames(ActiveGames.getInstance())
                .setClient(BattleshipClient.getInstance());

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
