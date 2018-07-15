package app.game.api.user;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.Game;
import app.game.api.game.NewGame;
import app.game.battlefield.Battlefield;
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

        NewGame response = client.challengeOpponent(newRequest);
        ctx.status(201).json(response);
    }

    public void onFire(Context context) {
    }

    public void auto(Context context) {
    }

    public void onStatus(Context ctx) {
        Game game = new Game();
        game.setOwner(getOwnerId());
        game.setStatus(getGameStatus());

        Self self = new Self();
        self.setUserId(getSelfId());
        self.setBoard(boardToString());

        Opponent opponent = new Opponent();
        opponent.setUserId(getOpponentId());
        opponent.setBoard(boardToString());

        Status status = new Status();
        status.setGame(game);
        status.setSelf(self);
        status.setOpponent(opponent);

        ctx.status(200).json(status);
    }

    private Game.GameStatus getGameStatus() {
        return Game.GameStatus.player_turn;
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
