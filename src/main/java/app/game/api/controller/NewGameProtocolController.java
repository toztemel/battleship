package app.game.api.controller;

import app.game.ActiveGames;
import app.game.api.dto.game.NewGame;
import app.game.battlefield.BattlefieldFactory;
import app.game.service.IDGenerator;
import io.javalin.Context;

public class NewGameProtocolController {

    public void onNewGame(Context ctx) {
        NewGame request = ctx.bodyAsClass(NewGame.class);
        NewGame newGame = new NewGame();
        newGame.setUserId(getOwnUserId());
        newGame.setFullName(getOwnUserName());
        newGame.setGameId(IDGenerator.generate());
        newGame.setStarting(request.getUserId());
        newGame.setRules(request.getRules());

        ActiveGames.getInstance().newGame(request, newGame);
        ActiveGames.getInstance().putBattlefield(newGame.getGameId(), BattlefieldFactory.createNew());

        ctx.status(201).json(newGame);
    }

    private String getOwnUserName() {
        return "Lunatech FR Champion";
    }

    private String getOwnUserId() {
        return "challenger-Y";
    }


}
