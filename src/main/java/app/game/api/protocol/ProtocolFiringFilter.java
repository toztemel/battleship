package app.game.api.protocol;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.status.GameStatus;
import app.game.service.cache.GameCacheService;
import app.game.service.rule.GameRuleValidationService;
import app.game.service.rule.GameRuleViolationException;

public class ProtocolFiringFilter implements ProtocolFilter {

    private GameCacheService gameCacheService;
    private GameRuleValidationService gameRuleValidationService;

    void preFilter(String gameId, FiringRequest firingRequest) {
        if (null == gameId) {
            throw new ProtocolApiException("gameId does not exist");
        }
        validateGame(gameId);
        validateFields(firingRequest);
        validateGameRules(gameId, firingRequest);
    }

    void postFilter(String gameId, FiringResponse firingResponse) {
        validateFiringResponse(gameId, firingResponse);
    }

    private void validateFiringResponse(String gameId, FiringResponse firingResponse) {
        gameRuleValidationService.onFiringProtocolResponseSent(gameId, firingResponse);
    }

    // TODO
    private void validateFields(FiringRequest firingRequest) {
        if (false) {
            throw new ProtocolApiException("Missing mandatory field");
        }
    }

    private void validateGame(String gameId) {
        if (!gameCacheService.containsGame(gameId)) {
            throw new ProtocolApiException("Game does not exist");
        }
        if (gameCacheService.getGame(gameId).getGameStatus() != GameStatus.Status.player_turn) {
            throw new ProtocolApiException("Game has already finished");
        }
    }

    private void validateGameRules(String gameId, FiringRequest firingRequest) {
        try {
            gameRuleValidationService.onFiringProtocolRequestReceived(gameId, firingRequest);
        } catch (GameRuleViolationException e) {
            throw new ProtocolApiException("Invalid number of shots");
        }
    }

    public ProtocolFiringFilter setGameCacheService(GameCacheService instance) {
        gameCacheService = instance;
        return this;
    }

    public ProtocolFiringFilter setGameRuleValidationService(GameRuleValidationService instance) {
        gameRuleValidationService = instance;
        return this;
    }
}
