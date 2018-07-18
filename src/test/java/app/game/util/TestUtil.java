package app.game.util;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.game.Rule;
import app.game.fire.Coordinates;
import app.game.ship.Ship;

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

    public static void print2DArray(Ship[][] frame) {
        System.out.println();
        for (int i = 0; i < frame.length; i++) {
            System.out.print("[");
            for (int j = 0; j < frame[0].length; j++) {
                System.out.print(frame[i][j].toStringAt(Coordinates.of(i, j)));
                if (j < frame[0].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }
}
