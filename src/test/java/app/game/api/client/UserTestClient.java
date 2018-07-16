package app.game.api.client;

import app.game.api.ResourcePath;
import app.game.api.client.rest.RestClient;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.Status;

import javax.ws.rs.core.Response;

public class UserTestClient {

    private final RestClient client;

    public UserTestClient(String uri) {
        client = new RestClient(uri);
    }

    public Status queryGameStatus(NewGame game) {
        Response response = client.get(ResourcePath.User.USER_API.concat(game.getGameId()));
        if (!RestClient.isSuccessful(response)) {
            // TODO process error
        }
        return response.readEntity(Status.class);
    }

    public NewGame challangeOpponent(NewGame newGameRequest) {
        Response response = client.post(ResourcePath.User.NEW_GAME, newGameRequest);
        if(!RestClient.isCreated(response)){
            // TODO process game start error
        }
        return response.readEntity(NewGame.class);
    }
}
