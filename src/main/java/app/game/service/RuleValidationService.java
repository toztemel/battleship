package app.game.service;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.game.Rule;
import app.game.service.rule.GameRuleFactory;
import app.game.service.rule.GameRuleViolationException;

public class RuleValidationService {

    private static final RuleValidationService instance = new RuleValidationService();

    private RuleValidationService() {
    }

    public static RuleValidationService getInstance() {
        return instance;
    }

    public void onFiringProtocolRequestReceived(String gameId, FiringRequest firingRequest) {
        Game game = ActiveGames.getInstance().getGame(gameId);
        validateGameOwner(game);
        validateNumberOfShots(firingRequest, game);
    }

    private void validateNumberOfShots(FiringRequest firingRequest, Game game) {
        Rule gameRule = game.getGameRule();
        GameRuleFactory.getInstance()
                .create(gameRule)
                .validateIncomingShots(firingRequest, game);
    }

    private void validateGameOwner(Game game) {
        String ownUser = game.getUserId();
        if (game.getGameOwner().equals(ownUser)) {
            throw new GameRuleViolationException("Game owner is " + ownUser);
        }
    }

    public void onFiringProtocolResponseSent(String gameId, FiringResponse firingResponse) {
        Game game = ActiveGames.getInstance().getGame(gameId);
        GameRuleFactory.getInstance()
                .create(game.getGameRule())
                .validateOutgoingResponse(firingResponse, game);

    }

    public void onNewGameProtocolRequestReceived(NewGame request, NewGame response) {
        Game game = ActiveGames.getInstance().getGame(response.getGameId());

        GameRuleFactory.getInstance()
                .create(request.getRule())
                .processIncomingGameRequest(request, response, game);
    }
}
