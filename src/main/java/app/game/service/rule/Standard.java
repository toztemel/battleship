package app.game.service.rule;

import app.game.api.dto.game.NewGame;
import app.game.service.cache.Game;

class Standard extends XShot {

    public void processIncomingGameRequest(NewGame request, NewGame response, Game game) {
        game.setUserShots(1);
        game.setOpponentShots(1);
    }
}
