package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.game.NewGame;

import javax.ws.rs.core.Response;
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

    static FiringRequest getFiringRequest() {
        FiringRequest fire = new FiringRequest();
        fire.setShots(new String[]{"1xB", "0xA", "5x1"});
        return fire;
    }

    static NewGame startNewGame(BattleshipClient client) {
        NewGame newGameRequest = newGameRequest();

        Response response = client.challangeFriend(newGameRequest);
        return response.readEntity(NewGame.class);
    }

    public static void print2DArray(Object[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.deepToString(matrix[i]));
        }
        System.out.println();
    }
}
