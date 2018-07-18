package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.client.BattleshipClient;
import app.game.api.controller.FiringProtocolController;
import app.game.api.controller.FiringProtocolFilter;
import app.game.api.controller.NewGameProtocolController;
import app.game.api.controller.UserController;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.battlefield.BattlefieldFactory;
import app.game.conf.BattlefieldConf;
import app.game.conf.HTTPServerConf;
import app.game.conf.UserConf;
import app.game.service.*;
import app.game.service.rule.GameRuleFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

class BattleshipGame {

    private BattleshipAPI api;

    BattleshipGame() {
    }

    void start() {
        start(new HTTPServerConf());
    }

    void start(HTTPServerConf conf) {
        configureServices(conf);
        startApi(conf);
    }

    private void configureServices(HTTPServerConf conf) {

        ProtocolService.getInstance()
                .setHttpConf(conf);

        UserService.getInstance()
                .setUserConf(new UserConf());

        BattlefieldFactory.getInstance()
                .configure(new BattlefieldConf());

        GameCache.getInstance()
                .setBattlefieldFactory(BattlefieldFactory.getInstance())
                .setProtocolService(ProtocolService.getInstance());

        GameRuleValidationService.getInstance()
                .setGameRuleFactory(GameRuleFactory.getInstance());

    }

    private void startApi(HTTPServerConf conf) {

        NewGameProtocolController newGameController = new NewGameProtocolController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCache.getInstance())
                .setProtocolService(ProtocolService.getInstance())
                .setIDGeneratorService(IDGenerator.getInstance());


        FiringProtocolFilter fireFilter = new FiringProtocolFilter()
                .setGameCache(GameCache.getInstance())
                .setGameRuleValidationService(GameRuleValidationService.getInstance());

        FiringProtocolController fireController = new FiringProtocolController()
                .setGameCache(GameCache.getInstance())
                .setFilter(fireFilter);

        UserController userController = new UserController()
                .setUserService(UserService.getInstance())
                .setGameCache(GameCache.getInstance())
                .setBattleshipClient(BattleshipClient.getInstance())
                .setProtocolService(ProtocolService.getInstance());


        api = BattleshipAPI.getInstance()
                .listen(conf)
                .withMapper(this::defaultObjectMapper)
                .onProtocolNewGame(newGameController::onNewGame)
                .onProtocolFire(fireController::onFire)
                .onUserStartNewGame(userController::onNewGame)
                .onUserAsksStatus(userController::onStatus)
                .onUserFires(userController::onFire)
                .onUserEnablesAutoPilot(userController::auto)
                .on400Error(ctx -> {
                    String gameId = ctx.param("gameId");
                    GameCache.getInstance().onError(gameId);
                    ctx.result("HTTP 400");
                })
                .start();
    }

    private ObjectMapper defaultObjectMapper() {
        return new BattleshipObjectMapper().getDefaultObjectMapper();
    }

    void stop() {
        api.stop();
    }

}
