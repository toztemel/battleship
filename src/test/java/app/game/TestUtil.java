package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.game.NewGame;
import app.game.fire.Coordinates;

import java.util.Arrays;

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

    static FiringRequest getFiringRequest(Coordinates... coordinates) {
        String[] shots = Arrays.stream(coordinates)
                .map(Coordinates::toProtocolString)
                .toArray(String[]::new);
        FiringRequest fire = new FiringRequest();
        fire.setShots(shots);
        return fire;
    }

    static NewGame startNewGame(BattleshipClient client) {
        return client.challengeOpponent(newGameRequest());
    }

}
