package app.game.api.client;

import app.game.api.ResourcePath.Protocol;
import app.game.api.client.rest.RestClient;
import app.game.api.controller.ProtocolApiException;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

public class BattleshipClient {

    private static final String HTTP = "http://";
    private static BattleshipClient instance = new BattleshipClient();

    private RestClient client;

    private BattleshipClient() {
    }

    // TODO there can be more than one opponent => multiple BattleshipClient is needed
    public static BattleshipClient getInstance() {
        return instance;
    }

    public BattleshipClient target(String uri) {
        client = new RestClient(HTTP + uri);
        return this;
    }

    public NewGame challengeOpponent(NewGame newGame) {
        Response response = client.post(Protocol.NEW_GAME, newGame);
        if (!RestClient.isCreated(response)) {
            throw new ProtocolApiException(response.toString());
        }
        return response.readEntity(NewGame.class);
    }

    public FiringResponse fire(String gameId, FiringRequest fire) {
        Response response = client.put("/protocol/game/" + gameId, fire);
        if (!RestClient.isSuccessful(response)) {
            throw new ProtocolApiException(response.toString());
        }
        return response.readEntity(FiringResponse.class);
    }

}
