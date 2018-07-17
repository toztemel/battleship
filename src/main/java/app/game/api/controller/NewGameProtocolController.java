package app.game.api.controller;

import app.game.api.dto.game.NewGame;
import app.game.service.ActiveGames;
import app.game.service.UserService;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewGameProtocolController {

    private static final Logger LOG = LoggerFactory.getLogger(NewGameProtocolController.class);

    private UserService userService;
    private ActiveGames activeGamesService;

    public void onNewGame(Context ctx) {
        try {

            NewGame request = ctx.bodyAsClass(NewGame.class);

            NewGame response = new NewGame();
            response.setStarting(request.getUserId());
            response.setRules(request.getRules());
            response.setUserId(userService.ownUserId());
            response.setFullName(userService.ownFullName());

            String gameId = activeGamesService.onNewGameResponseReceived(request, response);

            response.setGameId(gameId);

            ctx.status(201).json(response);

        } catch (Exception e) {
            throw new ProtocolApiException(e);
        }
    }

    public NewGameProtocolController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public NewGameProtocolController setActiveGamesService(ActiveGames activeGamesService) {
        this.activeGamesService = activeGamesService;
        return this;
    }

}
