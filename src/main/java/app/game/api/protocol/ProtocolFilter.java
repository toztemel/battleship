package app.game.api.protocol;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;

interface ProtocolFilter {

    void preFilter(String gameId, FiringRequest firingRequest);

    void postFilter(String gameId, FiringResponse firingResponse);

}
