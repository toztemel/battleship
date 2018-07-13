package app.game.api.client;

import app.game.api.mapper.BattleshipObjectMapper;
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

    public Response post(String resource, Object request) {
        return target.path(resource)
                .request(JSON)
                .header("some-header", "true")
                .post(Entity.entity(request, JSON));
    }

    public Response put(String resource, Object request) {
        return target.path(resource)
                .request(JSON)
                .put(Entity.entity(request, JSON));
    }


    private class BattleshipClientFilter {
    }

}
