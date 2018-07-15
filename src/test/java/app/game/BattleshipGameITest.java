package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.firing.FiringResponse;
import app.game.api.game.NewGame;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static app.game.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BattleshipGameITest {

    private BattleshipGame game;
    private BattleshipClient opponent;

    @Before
    public void setUp() throws Exception {
        game = new BattleshipGame();
        game.start();

        opponent = new BattleshipClient(LOCALHOST_7000);
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
        NewGame newGame = startNewGame(opponent);
        FiringRequest fire = getFiringRequest(Coordinates.of(1, 11),
                Coordinates.of(0, 10),
                Coordinates.of(5, 1));
        FiringResponse firingResponse = opponent.fire(newGame, fire);

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
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertNotNull(newGame.getGameId());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
    }

    @Test
    public void returns_MISS_when_opponent_misses_single_shot() {
        NewGame newGame = startNewGame(opponent);
        Coordinates c = Coordinates.of(0, 0);
        Coordinates c2 = Coordinates.of(0, 1);
        FiringRequest fire = getFiringRequest(c, c2);
        FiringResponse response = opponent.fire(newGame, fire);

        assertEquals(Shot.Damage.MISS.toString(), response.getShots().get(c.toProtocolString()));
    }

}