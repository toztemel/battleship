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

        NewGame newGame = new NewGame();
        newGame.setUserId(userService.ownUserId());
        newGame.setFullName(userService.ownFullName());
        newGame.setGameId(idGeneratorService.generate());
        newGame.setStarting(request.getUserId());
        newGame.setRules(request.getRules());

        activeGamesService.newGameInvitation(request, newGame);

        ctx.status(201).json(newGame);
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
