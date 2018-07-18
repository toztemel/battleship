package app.game.api.controller;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.firing.FiringResults;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.service.GameCache;
import app.game.service.Game;
import io.javalin.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiringProtocolController {

    private GameCache gameCache;
    private FiringProtocolFilter fireFilter;

    public void onFire(Context ctx) {
        try {
            FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
            String gameId = ctx.param("gameId");
            fireFilter.preFilter(gameId, firingRequest);

            List<Shot> shotList = extractShotList(firingRequest);
            Battlefield battlefield = gameCache.getBattlefield(gameId);
            FiringResults firingResults = battlefield.fireAt(shotList);
            Game cachedGame = gameCache.getGame(gameId);
            GameStatus gameStatus = new GameStatus();
            if (battlefield.allShipsKilled()) {
                gameStatus.setOwner(cachedGame.getOpponentId());
                gameStatus.setStatus(GameStatus.Status.won);
            } else {
                gameStatus.setOwner(cachedGame.getUserId());
                gameStatus.setStatus(GameStatus.Status.player_turn);
            }
            cachedGame.setGameStatus(gameStatus.getStatus());
            cachedGame.setGameOwner(gameStatus.getOwner());
            FiringResponse response = new FiringResponse();
            response.setShots(firingResults);
            response.setGame(gameStatus);

            fireFilter.postFilter(gameId, response);

            ctx.status(200).json(response);
        } catch (Exception e) {
            throw new ProtocolApiException(e);
        }
    }

    private List<Shot> extractShotList(FiringRequest firingRequest) {
        return Arrays.stream(firingRequest.getShots())
                .map(Coordinates::fromProtocolString)
                .map(Shot::new)
                .collect(Collectors.toList());
    }

    public FiringProtocolController setGameCache(GameCache gameCache) {
        this.gameCache = gameCache;
        return this;
    }

    public FiringProtocolController setFilter(FiringProtocolFilter fireFilter) {
        this.fireFilter = fireFilter;
        return this;
    }
}
