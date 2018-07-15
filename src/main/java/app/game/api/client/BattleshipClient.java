package app.game.api.client;

import app.game.api.ResourcePath.Protocol;
import app.game.api.firing.FiringRequest;
import app.game.api.firing.FiringResponse;
import app.game.api.game.NewGame;
import app.game.api.mapper.BattleshipObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class BattleshipClient {

    private static final MediaType JSON = MediaType.APPLICATION_JSON_TYPE;

    private final WebTarget target;

    public BattleshipClient(String uri) {
        this.target = ClientBuilder.newClient()
                .register(new BattleshipClientFilter())
                .register(BattleshipObjectMapper.class)
                .register(JacksonFeature.class)
                .property("my-property", true)
                .target(uri);
    }

    public NewGame challengeOpponent(NewGame newGame) {
        Response response = post(Protocol.NEW_GAME, newGame);
        NewGame newGameResponse = response.readEntity(NewGame.class);
        if (!isCreated(response)) {
            System.err.println(response);
        }
        return newGameResponse;
    }

    public FiringResponse fire(NewGame game, FiringRequest fire) {
        Response response = put("/protocol/game/" + game.getGameId(), fire);
        if (!isSuccessful(response)) {
            System.err.println(response);
        }
        return response.readEntity(FiringResponse.class);
    }

    private boolean isSuccessful(Response response) {
        return response.getStatus() == SC_OK;
    }

    private boolean isCreated(Response response) {
        return response.getStatus() == SC_CREATED;
    }

    private Response post(String resource, Object request) {
        return target.path(resource)
                .request(JSON)
                .header("some-header", "true")
                .post(Entity.entity(request, JSON));
    }

    private Response put(String resource, Object request) {
        return target.path(resource)
                .request(JSON)
                .put(Entity.entity(request, JSON));
    }

    private class BattleshipClientFilter {
    }

}
