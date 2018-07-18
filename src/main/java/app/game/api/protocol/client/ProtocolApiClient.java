package app.game.api.protocol.client;

import app.game.api.ResourcePath.Protocol;
import app.game.api.protocol.client.rest.RestClient;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;

import javax.ws.rs.core.Response;

public class ProtocolApiClient {

    private static final String HTTP = "http://";
    private static ProtocolApiClient instance = new ProtocolApiClient();

    private RestClient client;

    private ProtocolApiClient() {
    }

    // TODO there can be more than one opponent => multiple ProtocolApiClient is needed
    public static ProtocolApiClient getInstance() {
        return instance;
    }

    public ProtocolApiClient target(String uri) {
        client = new RestClient(HTTP + uri);
        return this;
    }

    public NewGame challengeOpponent(NewGame newGame) {
        Response response = client.post(Protocol.NEW_GAME, newGame);
        if (!RestClient.isCreated(response)) {
            throw new ProtocolApiClientException(response.toString());
        }
        return response.readEntity(NewGame.class);
    }

    public FiringResponse fire(String gameId, FiringRequest fire) {
        Response response = client.put("/protocol/game/" + gameId, fire);
        if (!RestClient.isSuccessful(response)) {
            throw new ProtocolApiClientException(response.toString());
        }
        return response.readEntity(FiringResponse.class);
    }

}
