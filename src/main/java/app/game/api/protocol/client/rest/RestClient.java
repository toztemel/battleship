package app.game.api.protocol.client.rest;

import app.game.api.mapper.BattleshipObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class RestClient {

    private static final MediaType JSON = MediaType.APPLICATION_JSON_TYPE;

    private final WebTarget target;

    public RestClient(String uri) {
        this.target = ClientBuilder.newClient()
                .register(BattleshipObjectMapper.class)
                .register(JacksonFeature.class)
                .register(new BattleshipProtocolFilter())
                .target(uri);
    }

    public static boolean isSuccessful(Response response) {
        return response.getStatus() == SC_OK;
    }

    public static boolean isCreated(Response response) {
        return response.getStatus() == SC_CREATED;
    }

    public Response post(String resource, Object request, String gameId) {
        return target.path(resource)
                .request(JSON)
                .property("gameId", gameId)
                .post(Entity.entity(request, JSON));
    }

    public Response get(String resource, String gameId) {
        return target.path(resource)
                .request(JSON)
                .property("gameId", gameId)
                .get();
    }

    public Response put(String resource, Object request, String gameId) {
        return target.path(resource)
                .request(JSON)
                .property("gameId", gameId)
                .put(Entity.entity(request, JSON));
    }

}
