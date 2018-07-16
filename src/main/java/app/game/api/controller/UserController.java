package app.game.api.controller;

import app.game.ActiveGames;
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

    public void onNewGame(Context ctx) {
        NewGame userRequest = ctx.bodyAsClass(NewGame.class);
        BattleshipClient client = new BattleshipClient("http://" + userRequest.getProtocol());

        NewGame newRequest = new NewGame();
        newRequest.setRules(userRequest.getRules());
        newRequest.setUserId(userRequest.getUserId());
        newRequest.setFullName(userRequest.getFullName());
        newRequest.setProtocol(UserService.ownProtocol());

        NewGame response = client.challengeOpponent(newRequest);

        ActiveGames.getInstance().newGame(userRequest, response);
        ActiveGames.getInstance().putBattlefield(newRequest.getGameId(), Battlefield.newBattlefield());

        ctx.status(201).json(response);
    }

    public void onFire(Context context) {
    }

    public void auto(Context context) {
    }

    public void onStatus(Context ctx) {
        String gameId = ctx.param("gameId");

        GameStatus game = new GameStatus();
        game.setOwner(getOwnerId());
        game.setStatus(getGameStatus());

        SelfStatus self = new SelfStatus();
        self.setUserId(getSelfId());
        self.setBoard(boardToString(gameId));

        OpponentStatus opponent = new OpponentStatus();
        opponent.setUserId(getOpponentId());
        opponent.setBoard(boardToString(gameId));

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

    private String[] boardToString(String gameId) {
        return ActiveGames.getInstance().getBattlefield(gameId).asString();
    }

}
