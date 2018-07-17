package app.game.api.controller;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.api.dto.status.OpponentStatus;
import app.game.api.dto.status.SelfStatus;
import app.game.api.dto.status.Status;
import app.game.service.ActiveGames;
import app.game.service.UserService;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private BattleshipClient client;
    private ActiveGames activeGames;
    private UserService userService;

    public void onNewGame(Context ctx) {
        try {
            NewGame userRequest = ctx.bodyAsClass(NewGame.class);

            NewGame serverRequest = new NewGame();
            serverRequest.setRules(userRequest.getRules());
            serverRequest.setUserId(userRequest.getUserId());
            serverRequest.setFullName(userRequest.getFullName());
            serverRequest.setProtocol(userService.ownProtocol());

            NewGame opponentResponse = client.target("http://" + userRequest.getProtocol())
                    .challengeOpponent(serverRequest);

            activeGames.onNewGameRequestReceived(userRequest, opponentResponse);

            ctx.status(201).json(opponentResponse);
            LOG.info("Created new game. Id=", opponentResponse.getGameId());
        } catch (Exception e) {
            LOG.error("Error occured onNewGame:", e);
            throw new UserApiException(e);
        }
    }

    public void onFire(Context context) {
        try {
        } catch (Exception e) {
            throw new UserApiException(e);
        }

    }

    public void auto(Context context) {
        try {
        } catch (Exception e) {
            throw new UserApiException(e);
        }

    }

    public void onStatus(Context ctx) {
        try {
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
            LOG.info("Return status of game Id=", gameId);
        } catch (Exception e) {
            throw new UserApiException(e);
        }
    }

    private GameStatus.Mode getGameStatus() {
        return GameStatus.Mode.player_turn;
    }

    private String getSelfId() {
        return getOwnerId();
    }

    private String getOwnerId() {
        return userService.ownUserId();
    }

    private String getOpponentId() {
        return "challenger-X";
    }

    private String[] boardToString(String gameId) {
        return ActiveGames.getInstance().getBattlefield(gameId).asString();
    }

    public UserController setClient(BattleshipClient client) {
        this.client = client;
        return this;
    }

    public UserController setActiveGames(ActiveGames activeGames) {
        this.activeGames = activeGames;
        return this;
    }

    public UserController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

}
