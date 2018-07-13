package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.firing.FiringResponse;
import app.game.api.firing.Game;
import app.game.api.firing.Shots;
import app.game.api.game.NewGame;
import app.game.api.util.ResourcePath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BattleshipAPIEndToEndTest {

    private static final String LOCALHOST_7000 = "http://localhost:7000";

    private BattleshipGame game;
    private BattleshipClient client;

    @Before
    public void setUp() throws Exception {
        game = new BattleshipGame();
        game.start();

        client = new BattleshipClient(LOCALHOST_7000);
    }

    @After
    public void tearDown() throws Exception {
        game.stop();
    }

    @Test
    public void game_sends_an_invitation_when_user_starts_a_new_game() {
        FiringRequest fire = new FiringRequest();
        fire.setShots(new String[]{"1xB", "0xA","5x1"});

        Response response = client.put(ResourcePath.Protocol.FIRE, fire);

        assertEquals(200, response.getStatus());

        FiringResponse firingResponse = response.readEntity(FiringResponse.class);
        Game game = firingResponse.getGame();
        assertNotNull(game);
        assertEquals("challanger-Y", game.getOwner());
        assertEquals("player_turn", game.getStatus());

        Shots shots = firingResponse.getShots();
        assertNotNull(shots);
        assertEquals("hit", shots.get("1xB"));
        assertEquals("kill", shots.get("0xA"));
        assertEquals("miss", shots.get("5x1"));
    }

    @Test
    public void game_responds_to_a_new_game_invitation() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRules("standard");

        Response response = client.post(ResourcePath.Protocol.NEW_GAME, newGameRequest);

        assertEquals(201, response.getStatus());
        NewGame newGame = response.readEntity(NewGame.class);
        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertNotNull(newGame.getGameId());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
    }
}