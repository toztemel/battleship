package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.firing.FiringResponse;
import app.game.api.game.NewGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static app.game.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BattleshipGameITest {

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

    @Ignore
    @Test
    public void test_game_sends_an_invitation_when_user_starts_a_new_game() {
    }

    @Ignore
    @Test
    public void test_game_responds_with_error_to_firing_without_initialization() {
    }

    @Ignore
    @Test
    public void game_responds_with_error_to_firing_after_game_ends() {
    }

    @Test
    public void test_game_responds_to_firing_after_initialization() {
        String gameId = startNewGame(client)
                .getGameId();

        FiringRequest fire = getFiringRequest();
        Response response = client.fireFriend(gameId, fire);

        assertEquals(200, response.getStatus());

        FiringResponse firingResponse = response.readEntity(FiringResponse.class);

        assertNotNull(firingResponse.getGame());
        assertEquals("challanger-Y", firingResponse.getGame().getOwner());
        assertEquals("player_turn", firingResponse.getGame().getStatus());

        assertNotNull(firingResponse.getShots());
        assertEquals("hit", firingResponse.getShots().get("1xB"));
        assertEquals("kill", firingResponse.getShots().get("0xA"));
        assertEquals("miss", firingResponse.getShots().get("5x1"));
    }


    @Test
    public void game_responds_to_a_new_game_invitation() {
        NewGame newGameRequest = newGameRequest();
        Response response = client.challangeFriend(newGameRequest);

        assertEquals(201, response.getStatus());

        NewGame newGame = response.readEntity(NewGame.class);
        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertNotNull(newGame.getGameId());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
    }

}