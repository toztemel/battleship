package app.game.api.controller;

import app.game.api.dto.game.NewGame;
import app.game.service.ActiveGames;
import app.game.service.IDGenerator;
import app.game.service.UserService;
import io.javalin.Context;

public class NewGameProtocolController {

    private UserService userService;
    private ActiveGames activeGamesService;
    private IDGenerator idGeneratorService;

    public void onNewGame(Context ctx) {
        NewGame request = ctx.bodyAsClass(NewGame.class);

        NewGame response = new NewGame();
        response.setUserId(userService.ownUserId());
        response.setFullName(userService.ownFullName());
        response.setGameId(idGeneratorService.generate());
        response.setStarting(request.getUserId());
        response.setRules(request.getRules());

        activeGamesService.onNewGameRequestReceived(request, response);

        ctx.status(201).json(response);
    }

    public NewGameProtocolController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public NewGameProtocolController setActiveGamesService(ActiveGames activeGamesService) {
        this.activeGamesService = activeGamesService;
        return this;
    }

    public NewGameProtocolController setIDGeneratorService(IDGenerator idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
        return this;
    }
}
