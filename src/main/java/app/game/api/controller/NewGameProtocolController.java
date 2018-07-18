package app.game.api.controller;

import app.game.api.dto.game.NewGame;
import app.game.service.*;
import io.javalin.Context;

public class NewGameProtocolController {

    private UserService userService;
    private GameCache gameCacheService;
    private ProtocolService protocolService;
    private IDGenerator idGenerator;
    private GameRuleValidationService gameRuleValidationService = GameRuleValidationService.getInstance();

    public void onNewGame(Context ctx) {
        try {
            NewGame request = ctx.bodyAsClass(NewGame.class);
            NewGame response = new NewGame();
            response.setStarting(request.getUserId());
            response.setRule(request.getRule());
            response.setUserId(userService.ownUserId());
            response.setFullName(userService.ownFullName());
            response.setProtocol(protocolService.getOwnProtocol());
            response.setGameId(idGenerator.generate());
            gameCacheService.onIncomingNewGameRequest(request, response);
            gameRuleValidationService.onNewGameProtocolRequestReceived(request, response);
            ctx.status(201).json(response);
        } catch (Exception e) {
            throw new ProtocolApiException(e);
        }
    }

    public NewGameProtocolController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public NewGameProtocolController setGameCacheService(GameCache gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

    public NewGameProtocolController setProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
        return this;
    }

    public NewGameProtocolController setIDGeneratorService(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
        return this;
    }
}
