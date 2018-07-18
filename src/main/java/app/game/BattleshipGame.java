package app.game;

import app.game.api.BattleshipAPI;
import app.game.api.client.BattleshipClient;
import app.game.api.user.*;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.protocol.ProtocolFiringController;
import app.game.api.protocol.ProtocolFiringFilter;
import app.game.api.protocol.ProtocolNewGameController;
import app.game.battlefield.BattlefieldFactory;
import app.game.conf.BattlefieldConf;
import app.game.conf.HTTPServerConf;
import app.game.conf.UserConf;
import app.game.service.IDGenerator;
import app.game.service.ProtocolService;
import app.game.service.UserService;
import app.game.service.cache.GameCacheService;
import app.game.service.rule.GameRuleValidationService;
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

        GameCacheService.getInstance()
                .setBattlefieldFactory(BattlefieldFactory.getInstance())
                .setProtocolService(ProtocolService.getInstance());

    }

    private void startApi(HTTPServerConf conf) {

        ProtocolNewGameController newGameController = new ProtocolNewGameController()
                .setUserService(UserService.getInstance())
                .setGameCacheServiceService(GameCacheService.getInstance())
                .setProtocolService(ProtocolService.getInstance())
                .setIDGeneratorService(IDGenerator.getInstance())
                .setGameRuleValidationService(GameRuleValidationService.getInstance());


        ProtocolFiringFilter fireFilter = new ProtocolFiringFilter()
                .setGameCacheService(GameCacheService.getInstance())
                .setGameRuleValidationService(GameRuleValidationService.getInstance());

        ProtocolFiringController fireController = new ProtocolFiringController()
                .setGameCacheService(GameCacheService.getInstance())
                .setFilter(fireFilter);

        UserFiringController userFiringController = new UserFiringController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setBattleshipClient(BattleshipClient.getInstance());

        UserAutoController userAutoController = new UserAutoController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setBattleshipClient(BattleshipClient.getInstance());

        UserNewGameController userNewGameController = new UserNewGameController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setBattleshipClient(BattleshipClient.getInstance())
                .setProtocolService(ProtocolService.getInstance());

        UserStatusController userStatusController = new UserStatusController()
                .setGameCacheService(GameCacheService.getInstance());


        api = BattleshipAPI.getInstance()
                .listen(conf)
                .withMapper(this::defaultObjectMapper)
                .onProtocolNewGame(newGameController::onNewGame)
                .onProtocolFire(fireController::onFire)
                .onUserStartNewGame(userNewGameController::onNewGame)
                .onUserAsksStatus(userStatusController::onStatus)
                .onUserFires(userFiringController::onFire)
                .onUserEnablesAutoPilot(userAutoController::auto)
                .on400Error(ctx -> {
                    String gameId = ctx.param("gameId");
                    GameCacheService.getInstance().clear(gameId);
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
