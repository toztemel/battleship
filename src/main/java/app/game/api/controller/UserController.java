package app.game.api.controller;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.status.GameStatus;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.OpponentStatus;
import app.game.api.dto.status.SelfStatus;
import app.game.api.dto.status.Status;
import app.game.battlefield.Battlefield;
import app.game.service.UserService;
import io.javalin.Context;

public class UserController {

    private Battlefield battlefield;

    public UserController(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void onNewGame(Context ctx) {
        NewGame userRequest = ctx.bodyAsClass(NewGame.class);
        BattleshipClient client = new BattleshipClient("http://" + userRequest.getProtocol());

        NewGame newRequest = new NewGame();
        newRequest.setRules(userRequest.getRules());
        newRequest.setUserId(userRequest.getUserId());
        newRequest.setFullName(userRequest.getFullName());
        newRequest.setProtocol(UserService.ownProtocol());

        battlefield.reset(newRequest);
        NewGame response = client.challengeOpponent(newRequest);
        ctx.status(201).json(response);
    }

    public void onFire(Context context) {
    }

    public void auto(Context context) {
    }

    public void onStatus(Context ctx) {
        GameStatus game = new GameStatus();
        game.setOwner(getOwnerId());
        game.setStatus(getGameStatus());

        SelfStatus self = new SelfStatus();
        self.setUserId(getSelfId());
        self.setBoard(boardToString());

        OpponentStatus opponent = new OpponentStatus();
        opponent.setUserId(getOpponentId());
        opponent.setBoard(boardToString());

        Status status = new Status();
        status.setGame(game);
        status.setSelf(self);
        status.setOpponent(opponent);

        ctx.status(200).json(status);
    }

    private GameStatus.Mode getGameStatus() {
        return GameStatus.Mode.player_turn;
    }

    private String getSelfId() {
        return getOwnerId();
    }

    private String getOwnerId() {
        return "challenger-Y";
    }

    private String getOpponentId() {
        return "challenger-X";
    }

    private String[] boardToString() {
        return battlefield.asString();
    }

}
