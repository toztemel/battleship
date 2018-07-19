package app.game.api.protocol.client.rest;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static app.game.api.security.SecurityConstants.HEADER_AUTHORIZATION;
import static app.game.api.security.SecurityConstants.HEADER_GAME_ID;

class BattleshipProtocolFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final Map<String, String> idJwtMap = new HashMap<>();

    @Override
    public void filter(ClientRequestContext reqCtx) throws IOException {
        String gameId = (String) reqCtx.getProperty("gameId");
        if (idJwtMap.containsKey(gameId)) {
            String jwt = idJwtMap.get(gameId);
            reqCtx.getHeaders().add(HEADER_AUTHORIZATION, jwt);
        }
        reqCtx.removeProperty("gameId");
    }

    @Override
    public void filter(ClientRequestContext reqCtx, ClientResponseContext resCtx) throws IOException {
        if (resCtx.getHeaders().containsKey(HEADER_AUTHORIZATION)) {
            String jwt = resCtx.getHeaders().get(HEADER_AUTHORIZATION).get(0);
            String gameId = resCtx.getHeaderString(HEADER_GAME_ID);
            idJwtMap.put(gameId, jwt);
        }
    }
}
