package app.game.api.client;

import app.game.api.ResourcePath.Protocol;
import app.game.api.client.rest.RestClient;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;

import javax.ws.rs.core.Response;

public class BattleshipClient {

    private final RestClient client;

    public BattleshipClient(String uri) {
        client = new RestClient(uri);
    }

    public NewGame challengeOpponent(NewGame newGame) {
        Response response = client.post(Protocol.NEW_GAME, newGame);
        NewGame newGameResponse = response.readEntity(NewGame.class);
        if (!RestClient.isCreated(response)) {
            // TODO
            System.err.println(response);
        }
        return newGameResponse;
    }

    public FiringResponse fire(NewGame game, FiringRequest fire) {
        Response response = client.put("/protocol/game/" + game.getGameId(), fire);
        if (!RestClient.isSuccessful(response)) {
            // TODO
            System.err.println(response);
        }
        return response.readEntity(FiringResponse.class);
    }

}
