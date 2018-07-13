package app.game.api.client;

import app.game.api.firing.FiringRequest;
import app.game.api.game.NewGame;
import app.game.api.mapper.BattleshipObjectMapper;
import app.game.api.util.ResourcePath.Protocol;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    public Response challangeFriend(NewGame newGame) {
        return post(Protocol.NEW_GAME, newGame);
    }

    public Response fireFriend(String gameId, FiringRequest fire) {
        return put("/protocol/game/" + gameId, fire);
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
