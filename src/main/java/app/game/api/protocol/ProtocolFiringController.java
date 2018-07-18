package app.game.api.protocol;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.firing.FiringResults;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.service.cache.GameCacheService;
import app.game.service.cache.Game;
import io.javalin.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProtocolFiringController {

    private GameCacheService gameCacheService;
    private ProtocolFiringFilter fireFilter;

    public void onFire(Context ctx) {
        try {
            String gameId = ctx.param("gameId");
            FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);

            fireFilter.preFilter(gameId, firingRequest);

            Battlefield battlefield = gameCacheService.getBattlefield(gameId);
            FiringResults firingResults = battlefield.shotBy(extractShots(firingRequest));

            Game cachedGame = gameCacheService.getGame(gameId);
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

    private List<Shot> extractShots(FiringRequest firingRequest) {
        return Arrays.stream(firingRequest.getShots())
                .map(Coordinates::fromProtocolString)
                .map(Shot::new)
                .collect(Collectors.toList());
    }

    public ProtocolFiringController setGameCacheService(GameCacheService gameCacheService) {
        this.gameCacheService = gameCacheService;
        return this;
    }

    public ProtocolFiringController setFilter(ProtocolFiringFilter fireFilter) {
        this.fireFilter = fireFilter;
        return this;
    }
}
