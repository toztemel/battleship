package app.game.service.rule;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.firing.FiringResults;
import app.game.api.dto.game.NewGame;
import app.game.fire.Shot;
import app.game.service.cache.Game;

class Desperation implements GameRule {

    public void validateIncomingShots(FiringRequest s, Game game) {
        int shotsAllowed = game.getOpponentShots();
        if (s.getShots().length > shotsAllowed) {
            throw new GameRuleViolationException("Number of shots cannot exceed " + shotsAllowed);
        }
    }

    public void validateOutgoingResponse(FiringResponse firingResponse, Game game) {
        FiringResults shotResults = firingResponse.getShots();
        if (shotResults.containsValue(Shot.Damage.KILL)) {
            long killCount = shotResults.values()
                    .stream()
                    .filter(v -> v.equals(Shot.Damage.KILL))
                    .count();
            game.incrementOpponentShots(killCount);
        }
    }

    public void processIncomingGameRequest(NewGame request, NewGame response, Game game) {
        game.setUserShots(1);
        game.setOpponentShots(1);
    }
}
