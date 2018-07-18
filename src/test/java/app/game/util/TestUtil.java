package app.game.util;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.game.Rule;
import app.game.fire.Coordinates;

import java.util.Arrays;

public class TestUtil {

    public static final String LOCALHOST_7000 = "localhost:7000";
    public static final String LOCALHOST_7001 = "localhost:7001";

    public static NewGame newGameRequest() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRule(Rule.STANDARD);
        return newGameRequest;
    }

    public static FiringRequest aiming(Coordinates... coordinates) {
        String[] shots = Arrays.stream(coordinates)
                .map(Coordinates::toHexString)
                .toArray(String[]::new);
        FiringRequest fire = new FiringRequest();
        fire.setShots(shots);
        return fire;
    }

}
