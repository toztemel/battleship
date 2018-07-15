package app.game.api.game;

import io.javalin.Context;

public class NewGameProtocolController {

    public void newGameProtocol(Context ctx) throws Exception {
        NewGame newGame = new NewGame();
        newGame.setUserId("challenger-Y");
        newGame.setFullName("Lunatech FR Champion");
        newGame.generateGameId();
        NewGame request = ctx.bodyAsClass(NewGame.class);
        newGame.setStarting(request.getUserId());
        newGame.setRules(request.getRules());
        ctx.status(201).json(newGame);
    }
}
