package app.game;

import app.game.api.BattlefieldAPIConf;
import app.game.api.BattleshipAPI;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.protocol.ProtocolFiringController;
import app.game.api.protocol.ProtocolFiringFilter;
import app.game.api.protocol.ProtocolLoginController;
import app.game.api.protocol.ProtocolNewGameController;
import app.game.api.protocol.client.ProtocolApiClient;
import app.game.api.security.BattleshipAPIAccessManager;
import app.game.api.user.*;
import app.game.battlefield.BattlefieldConf;
import app.game.battlefield.BattlefieldFactory;
import app.game.service.IDGenerator;
import app.game.service.ProtocolService;
import app.game.service.cache.GameCacheService;
import app.game.service.rule.GameRuleValidationService;
import app.game.service.user.UserService;
import app.game.service.user.UserServiceConf;
import com.fasterxml.jackson.databind.ObjectMapper;

class BattleshipGame {

    private BattleshipAPI api;

    BattleshipGame() {
    }

    void start() {
        start(new BattlefieldAPIConf(), new UserServiceConf());
    }

    void start(BattlefieldAPIConf apiConf, UserServiceConf userConf) {
        configureServices(apiConf, userConf);
        startApi(apiConf);
    }

    private void configureServices(BattlefieldAPIConf conf, UserServiceConf userServiceConf) {

        ProtocolService.getInstance()
                .setHttpConf(conf);

        UserService.getInstance()
                .setUserConf(userServiceConf);

        BattlefieldFactory.getInstance()
                .configure(new BattlefieldConf());

        GameCacheService.getInstance()
                .setBattlefieldFactory(BattlefieldFactory.getInstance())
                .setProtocolService(ProtocolService.getInstance());
    }

    private void startApi(BattlefieldAPIConf conf) {

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

        ProtocolLoginController loginController = new ProtocolLoginController()
                .setUserService(UserService.getInstance());

        UserFiringController userFiringController = new UserFiringController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setProtocolApiClient(ProtocolApiClient.getInstance());

        UserAutoController userAutoController = new UserAutoController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setProtocolApiClient(ProtocolApiClient.getInstance());

        UserNewGameController userNewGameController = new UserNewGameController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setProtocolApiClient(ProtocolApiClient.getInstance())
                .setProtocolService(ProtocolService.getInstance())
                .setGameRulesValidationService(GameRuleValidationService.getInstance());

        UserStatusController userStatusController = new UserStatusController()
                .setGameCacheService(GameCacheService.getInstance());

        UserLoginController userLoginController = new UserLoginController()
                .setUserService(UserService.getInstance())
                .setGameCacheService(GameCacheService.getInstance())
                .setProtocolApiClient(ProtocolApiClient.getInstance());

        BattleshipAPIAccessManager accessManager = new BattleshipAPIAccessManager()
                .setUserService(UserService.getInstance());

        api = BattleshipAPI.getInstance()
                .listen(conf)
                .withMapper(this::defaultObjectMapper)
                .accessManager(accessManager::manageAccess)
                .onProtocolNewGame(newGameController::onNewGame)
                .onProtocolFire(fireController::onFire)
                .onProtocolLogin(loginController::login)
                .onUserStartNewGame(userNewGameController::onNewGame)
                .onUserAsksStatus(userStatusController::onStatus)
                .onUserFires(userFiringController::onFire)
                .onUserEnablesAutoPilot(userAutoController::auto)
                .onUserLogin(userLoginController::login)
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
