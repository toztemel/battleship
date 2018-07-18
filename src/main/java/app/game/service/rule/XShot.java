package app.game.service.rule;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.service.cache.Game;

class XShot implements GameRule {

    private static final int X_SHOT = 10;

    @Override
    public void validateIncomingShots(FiringRequest s, Game game) {
        if (s.getShots().length > game.getOpponentShots()) {
            throw new GameRuleViolationException("Number of shots cannot exceed " + game.getOpponentShots());
        }
    }

    @Override
    public void validateOutgoingResponse(FiringResponse firingResponse, Game game) {

    }

    @Override
    public void processIncomingGameRequest(NewGame request, NewGame response, Game game) {
        game.setOpponentShots(X_SHOT);
        game.setUserShots(X_SHOT);
    }

}
