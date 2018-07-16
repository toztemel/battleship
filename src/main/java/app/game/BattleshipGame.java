package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.client.BattleshipClient;
import app.game.api.controller.FiringProtocolController;
import app.game.api.controller.NewGameProtocolController;
import app.game.api.controller.UserController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.battlefield.BattlefieldFactory;
import app.game.conf.BattlefieldConf;
import app.game.conf.HTTPServerConf;
import app.game.conf.UserConf;
import app.game.service.ActiveGames;
import app.game.service.IDGenerator;
import app.game.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

class BattleshipGame {

    private BattleshipAPI api;

    BattleshipGame() {
    }

    void start() {
        start(new HTTPServerConf().httpServerPort());
    }

    void start(int port) {
        configureServices();
        startApi(port);
    }

    private void configureServices() {
        UserService.getInstance()
                .setUserConf(new UserConf());

        BattlefieldFactory.getInstance()
                .setConf(new BattlefieldConf());

        ActiveGames.getInstance()
                .setBattlefieldFactory(BattlefieldFactory.getInstance());

    }

    private void startApi(int httpServerPort) {

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

        api = BattleshipAPI.getInstance()
                .listen(httpServerPort)
                .withMapper(this::defaultObjectMapper)
                .onProtocolNewGame(newGameController::onNewGame)
                .onProtocolFire(fireController::onFire)
                .onUserStartNewGame(userController::onNewGame)
                .onUserAsksStatus(userController::onStatus)
                .onUserFires(userController::onFire)
                .onUserEnablesAutoPilot(userController::auto)
                .start();
    }

    private ObjectMapper defaultObjectMapper() {
        return new BattleshipObjectMapper().getDefaultObjectMapper();
    }

    void stop() {
        api.stop();
    }

}
