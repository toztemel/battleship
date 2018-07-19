package app.game.api.protocol.client.rest;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class BattleshipProtocolFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final Map<String, String> idJwtMap = new HashMap<>();

    @Override
    public void filter(ClientRequestContext reqCtx) throws IOException {
        String gameId = (String) reqCtx.getProperty("gameId");
        if (idJwtMap.containsKey(gameId)){
            String jwt = idJwtMap.get(gameId);
            reqCtx.getHeaders().add("Authorization", jwt);
        }
        reqCtx.removeProperty("gameId");
    }

    @Override
    public void filter(ClientRequestContext reqCtx, ClientResponseContext resCtx) throws IOException {
        if (resCtx.getHeaders().containsKey("Authorization")) {
            String jwt = resCtx.getHeaders().get("Authorization").get(0);
            String gameId = resCtx.getHeaderString("gameId");
            idJwtMap.put(gameId, jwt);
        }
    }
}
