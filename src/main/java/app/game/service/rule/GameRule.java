package app.game.service.rule;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.service.cache.Game;

interface GameRule {

    void validateIncomingShots(FiringRequest s, Game game);

    void validateOutgoingResponse(FiringResponse firingResponse, Game game);

    void processIncomingGameRequest(NewGame request, NewGame response, Game game);

    void processOutgoingGameRequest(NewGame request, NewGame response, Game game);
}
