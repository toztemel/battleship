package app.game.service.rule;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.service.cache.Game;

import java.util.Random;

// TODO parametrize X and send over to opponent
class XShot implements GameRule {

    @Override
    public void validateIncomingShots(FiringRequest s, Game game) {
        int shotsAllowed = game.getOpponentShots();
        if (s.getShots().length > shotsAllowed) {
            throw new GameRuleViolationException("Number of shots cannot exceed " + shotsAllowed);
        }
    }

    @Override
    public void validateOutgoingResponse(FiringResponse firingResponse, Game game) {

    }

    @Override
    public void processIncomingGameRequest(NewGame request, NewGame response, Game game) {
        int xShot = new Random().nextInt(11);
        game.setOpponentShots(xShot);
        game.setUserShots(xShot);
    }

}
