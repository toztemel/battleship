package app.game.service.rule;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.service.cache.Game;
import app.game.service.cache.GameCacheService;

public class GameRuleValidationService {

    private static final GameRuleValidationService instance = new GameRuleValidationService();

    private GameRuleFactory gameRuleFactory;

    private GameRuleValidationService() {
        gameRuleFactory = new GameRuleFactory();
    }

    public static GameRuleValidationService getInstance() {
        return instance;
    }

    public void onFiringProtocolRequestReceived(String gameId, FiringRequest firingRequest) {
        Game game = GameCacheService.getInstance().getGame(gameId);
        validateGameOwner(game);
        validateNumberOfShots(firingRequest, game);
    }

    private void validateNumberOfShots(FiringRequest firingRequest, Game game) {
        gameRuleFactory.get(game.getGameRule())
                .validateIncomingShots(firingRequest, game);
    }

    private void validateGameOwner(Game game) {
        String ownUser = game.getUserId();
        if (game.getGameOwner().equals(ownUser)) {
            throw new GameRuleViolationException("Game owner is " + ownUser);
        }
    }

    public void onFiringProtocolResponseSent(String gameId, FiringResponse firingResponse) {
        Game game = GameCacheService.getInstance().getGame(gameId);
        gameRuleFactory.get(game.getGameRule())
                .validateOutgoingResponse(firingResponse, game);
    }

    public void onNewGameProtocolRequestReceived(NewGame request, NewGame response) {
        Game game = GameCacheService.getInstance().getGame(response.getGameId());
        gameRuleFactory.get(request.getRule())
                .processIncomingGameRequest(request, response, game);
    }

}
