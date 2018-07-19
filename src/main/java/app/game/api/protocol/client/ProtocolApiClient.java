package app.game.api.protocol.client;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.protocol.client.rest.RestClient;
import app.game.api.user.Login;

import javax.ws.rs.core.Response;

import static app.game.api.ResourcePath.Protocol.BASE;
import static app.game.api.ResourcePath.Protocol.LOGIN;
import static app.game.api.ResourcePath.Protocol.NEW_GAME;

public class ProtocolApiClient {

    private static final String HTTP = "http://";
    private static final String HEADER_AUTHENTICATION = "Authentication";
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
        Response response = client.post(NEW_GAME, newGame, newGame.getGameId());
        if (!RestClient.isCreated(response)) {
            throw new ProtocolApiClientException(response.toString());
        }
        return response.readEntity(NewGame.class);
    }

    public FiringResponse fire(String gameId, FiringRequest fire) {
        Response response = client.put(BASE + gameId, fire, gameId);
        if (!RestClient.isSuccessful(response)) {
            throw new ProtocolApiClientException(response.toString());
        }
        return response.readEntity(FiringResponse.class);
    }

    public void loginRequest(Login login) {
        Response response = client.post(LOGIN, login, login.getGameId());
        if (!RestClient.isCreated(response)) {
            throw new ProtocolApiClientException(response.toString());
        }
    }
}