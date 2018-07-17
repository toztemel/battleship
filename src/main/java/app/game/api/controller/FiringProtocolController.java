package app.game.api.controller;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.firing.FiringResults;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.fire.CoordinatesFormatter;
import app.game.fire.Shot;
import app.game.service.ActiveGames;
import io.javalin.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiringProtocolController {

    private ActiveGames activeGames;

    public void onFire(Context ctx) {
        try {
            String gameId = ctx.param("gameId");

            validateGameId(gameId);
            validateGameMode(gameId);

            FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
            String[] incomingShots = firingRequest.getShots();

            validateGameRules(gameId, firingRequest);

            List<Shot> shotList = Arrays.stream(incomingShots)
                    .map(CoordinatesFormatter::fromProtocolString)
                    .map(Shot::new)
                    .collect(Collectors.toList());


            Battlefield battlefield = getBattlefield(gameId);
            FiringResults firingResults = new FiringResults();
            for (Shot shot : shotList) {
                Shot.Damage result = battlefield.fireAt(shot);
                firingResults.put(shot.asHexString(), result.toString());
            }

            GameStatus gameStatus = new GameStatus();
            if (battlefield.allShipsKilled()) {
                gameStatus.setStatus(GameStatus.Mode.won);
            } else {
                gameStatus.setStatus(GameStatus.Mode.player_turn);
            }
            gameStatus.setOwner(getGameOwner(gameId));

            updateGameStatus(gameId, gameStatus);

            FiringResponse response = new FiringResponse();
            response.setShots(firingResults);
            response.setGame(gameStatus);

            ctx.status(200).json(response);
        } catch (Exception e) {
            throw new ProtocolApiException(e);
        }
    }

    private String getGameOwner(String gameId) {
        return activeGames.getGame(gameId).getOpponentId();
    }

    private void updateGameStatus(String gameId, GameStatus game) {
        // TODO update activeGames,
        // TODO update game rules
    }

    private Battlefield getBattlefield(String gameId) {
        return activeGames.getBattlefield(gameId);
    }

    private void validateGameMode(String gameId) {
        if (!activeGames.isOpponentsTurn(gameId)) {
            throw new ProtocolApiException("Opponent cannot shoot. It is owner's turn");
        }
    }

    private void validateGameId(String gameId) {
        boolean activeGame = activeGames.containsGame(gameId);
        if (!activeGame) {
            throw new ProtocolApiException("Game not found with id:" + gameId);
        }
    }

    // TODO check game rules
    private void validateGameRules(String gameId, FiringRequest incomingShots) throws IllegalArgumentException {
        if (false) {
            throw new ProtocolApiException("Invalid number of shots in gameRules:");
        }
    }

    public FiringProtocolController setActiveGames(ActiveGames activeGames) {
        this.activeGames = activeGames;
        return this;
    }
}
