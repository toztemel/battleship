package app.game.api.controller;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.api.dto.status.OpponentStatus;
import app.game.api.dto.status.SelfStatus;
import app.game.api.dto.status.StatusResponse;
import app.game.service.ActiveGames;
import app.game.service.Game;
import app.game.service.ProtocolService;
import app.game.service.UserService;
import app.game.util.DoubleArrays;
import io.javalin.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private BattleshipClient battleshipClient;
    private ActiveGames activeGames;
    private UserService userService;
    private ProtocolService protocolService;

    public void onNewGame(Context ctx) {
        try {
            NewGame userRequest = ctx.bodyAsClass(NewGame.class);
            NewGame serverRequest = new NewGame();
            serverRequest.setRule(userRequest.getRule());
            serverRequest.setUserId(userRequest.getUserId());
            serverRequest.setFullName(userRequest.getFullName());
            serverRequest.setProtocol(protocolService.getOwnProtocol());
            NewGame opponentResponse = battleshipClient.target(userRequest.getProtocol())
                    .challengeOpponent(serverRequest);
            activeGames.onOutgoingNewGameRequest(userRequest, opponentResponse);
            ctx.status(201).json(opponentResponse);
            LOG.info("Created new game. Id=", opponentResponse.getGameId());
        } catch (Exception e) {
            LOG.error("Error occured onNewGame:", e);
            throw new UserApiException(e);
        }
    }

    public void onFire(Context context) {
        String gameId = context.param("gameId");
        try {
            validateGameId(gameId);
            validateGameStatus(gameId);
            FiringRequest firingRequest = context.bodyAsClass(FiringRequest.class);
            validateGameRules(gameId, firingRequest);

            String opponentProtocol = activeGames.getGame(gameId).getOpponentProtocol();
            FiringResponse firingResponse = battleshipClient.target(opponentProtocol)
                    .fire(gameId, firingRequest);

            activeGames.firedAt(gameId, firingResponse);
            context.status(200).json(firingResponse);
        } catch (Exception e) {
            LOG.error("Error occured while firing.", e);
            throw new UserApiException(e);
        }
    }

    private void validateGameId(String gameId) {
        boolean activeGame = activeGames.containsGame(gameId);
        if (!activeGame) {
            throw new UserApiException("Game not found with id:" + gameId);
        }
    }

    private void validateGameStatus(String gameId) {
        if (activeGames.isOpponentsTurn(gameId)) {
            throw new UserApiException("Owner cannot shoot. It is opponent's turn");
        }
    }

    // TODO check game rules
    private void validateGameRules(String gameId, FiringRequest incomingShots) throws IllegalArgumentException {
        if (false) {
            throw new UserApiException("Invalid number of shots in gameRules:");
        }
    }

    public void auto(Context context) {
        try {
        } catch (Exception e) {
            LOG.error("Error occured in auto.", e);
            throw new UserApiException(e);
        }

    }

    public void onStatus(Context ctx) {
        try {
            StatusResponse statusResponse = new StatusResponse();

            String gameId = ctx.param("gameId");
            Game cachedGame = activeGames.getGame(gameId);

            GameStatus game = new GameStatus();
            game.setOwner(cachedGame.getGameOwner());
            game.setStatus(cachedGame.getStatus());
            statusResponse.setGame(game);

            SelfStatus self = new SelfStatus();
            self.setUserId(cachedGame.getUserId());
            self.setBoard(ownBattlefieldAsStringArray(gameId));
            statusResponse.setSelf(self);

            OpponentStatus opponent = new OpponentStatus();
            opponent.setUserId(cachedGame.getOpponentId());
            opponent.setBoard(opponentBattlefieldAsStringArray(cachedGame));
            statusResponse.setOpponent(opponent);

            ctx.status(200).json(statusResponse);
            LOG.info("Return statusResponse of game Id=", gameId);
        } catch (Exception e) {
            LOG.error("Error occured during status check.", e);
            throw new UserApiException(e);
        }
    }

    private String[] ownBattlefieldAsStringArray(String gameId) {
        return activeGames.getBattlefield(gameId).asString();
    }

    private String[] opponentBattlefieldAsStringArray(Game cachedGame) {
        String[][] opponentBoard = cachedGame.getOpponentBoard();
        return DoubleArrays.asString(opponentBoard);
    }

    public UserController setBattleshipClient(BattleshipClient battleshipClient) {
        this.battleshipClient = battleshipClient;
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

    public UserController setProtocolService(ProtocolService protocolService) {
        this.protocolService = protocolService;
        return this;
    }
}
