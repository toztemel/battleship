package app.game.api.controller;

import app.game.api.dto.game.NewGame;
import app.game.service.IDGenerator;
import io.javalin.Context;

public class NewGameProtocolController {

    public void onNewGame(Context ctx) throws Exception {
        NewGame newGame = new NewGame();
        newGame.setUserId("challenger-Y");
        newGame.setFullName("Lunatech FR Champion");
        newGame.setGameId(IDGenerator.generate());
        NewGame request = ctx.bodyAsClass(NewGame.class);
        newGame.setStarting(request.getUserId());
        newGame.setRules(request.getRules());
        ctx.status(201).json(newGame);
    }
}
