package app.game.service.rule;

import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.fire.Shot;
import app.game.service.cache.Game;

class SuperCharge extends Standard {

    @Override
    public void validateOutgoingResponse(FiringResponse firingResponse, Game game) {
        boolean killed = firingResponse.getShots().containsValue(Shot.Damage.KILL);
        if (killed) {
            game.setGameOwner(game.getOpponentId());
            firingResponse.getGame().setOwner(game.getOpponentId());
        }
    }

}
