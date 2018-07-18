package app.game.api.controller;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.status.GameStatus;
import app.game.service.ActiveGames;
import app.game.service.RuleValidationService;
import app.game.service.rule.GameRuleViolationException;

public class FiringProtocolFilter implements ProtocolFilter {

    private ActiveGames activeGames;
    private RuleValidationService ruleValidationService;

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
        ruleValidationService.onFiringProtocolResponseSent(gameId, firingResponse);
    }

    // TODO
    private void validateFields(FiringRequest firingRequest) {
        if (false) {
            throw new ProtocolApiException("Missing mandatory field");
        }
    }

    private void validateGame(String gameId) {
        if (!activeGames.containsGame(gameId)) {
            throw new ProtocolApiException("Game does not exist");
        }
        if (activeGames.getGame(gameId).getGameStatus() != GameStatus.Status.player_turn) {
            throw new ProtocolApiException("Game has already finished");
        }
    }

    private void validateGameRules(String gameId, FiringRequest firingRequest) {
        try {
            ruleValidationService.onFiringProtocolRequestReceived(gameId, firingRequest);
        } catch (GameRuleViolationException e) {
            throw new ProtocolApiException("Invalid number of shots in gameRules:");
        }
    }

    public FiringProtocolFilter setActiveGames(ActiveGames instance) {
        activeGames = instance;
        return this;
    }

    public FiringProtocolFilter setRuleValidationService(RuleValidationService instance) {
        ruleValidationService = instance;
        return this;
    }
}
