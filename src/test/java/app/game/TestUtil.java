package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.game.NewGame;

import javax.ws.rs.core.Response;

public class TestUtil {

    static final String LOCALHOST_7000 = "http://localhost:7000";

    static NewGame newGameRequest() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRules("standard");
        return newGameRequest;
    }

    static FiringRequest getFiringRequest() {
        FiringRequest fire = new FiringRequest();
        fire.setShots(new String[]{"1xB", "0xA", "5x1"});
        return fire;
    }

    static NewGame startNewGame(BattleshipClient client) {
        NewGame newGameRequest = newGameRequest();

        Response response = client.challengeOpponent(newGameRequest);
        return response.readEntity(NewGame.class);
    }

}
