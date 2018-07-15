package app.game.api.client;

import app.game.api.ResourcePath;
import app.game.api.game.NewGame;
import app.game.api.user.Status;

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

}
