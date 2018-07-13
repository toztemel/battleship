package app.game.api.game;

import io.javalin.Handler;

public class NewGameController {

    private final Handler newGameHandler = createNewGameHandler();

    private Handler createNewGameHandler() {
        return ctx -> {
            NewGame newGame = new NewGame();
            newGame.setUserId("challenger-Y");
            newGame.setFullName("Lunatech FR Champion");
            newGame.generateGameId();
            NewGame request = ctx.bodyAsClass(NewGame.class);
            newGame.setStarting(request.getUserId());
            newGame.setRules(request.getRules());
            ctx.status(201).json(newGame);
        };
    }

    public Handler newGameHandler() {
        return newGameHandler;
    }
}
