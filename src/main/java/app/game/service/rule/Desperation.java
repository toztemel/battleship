package app.game.service.rule;

import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.firing.FiringResults;
import app.game.api.dto.game.NewGame;
import app.game.fire.Shot;
import app.game.service.cache.Game;

class Desperation extends Standard {

    @Override
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

}
