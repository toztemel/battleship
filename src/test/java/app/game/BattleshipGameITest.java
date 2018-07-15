package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.firing.FiringRequest;
import app.game.api.firing.FiringResponse;
import app.game.api.game.NewGame;
import app.game.fire.Coordinates;
import app.game.ship.Angle;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static app.game.ShotDamageMatcher.at;
import static app.game.TestUtil.*;
import static app.game.fire.Shot.Damage.*;
import static org.junit.Assert.*;

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
    public void server_sends_an_invitation_when_user_starts_a_new_game() {
    }

    @Ignore
    @Test
    public void server_responds_with_error_to_firing_without_initialization() {
    }

    @Ignore
    @Test
    public void server_responds_with_error_to_firing_after_game_ends() {
    }

    @Test
    public void server_responds_to_a_new_game_invitation() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertNotNull(newGame.getGameId());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
    }

    @Test
    public void server_returns_MISS_when_opponent_misses_single_shot() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame, aiming(coordinates));

        assertThat(result, at(coordinates).is(MISS));
    }

    @Test
    public void server_returns_MISS_when_opponent_misses_multiple_shots() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        Coordinates[] coordinateList = new Coordinates[]
                {
                        Coordinates.of(1, 11)
                        , Coordinates.of(0, 10)
                        , Coordinates.of(5, 1)
                };

        FiringRequest fire = aiming(coordinateList);
        FiringResponse firingResponse = opponent.fire(newGame, fire);

        assertNotNull(firingResponse.getGame());
        assertEquals("challanger-Y", firingResponse.getGame().getOwner());
        assertEquals("player_turn", firingResponse.getGame().getStatus());

        assertNotNull(firingResponse.getShots());
        for (Coordinates coordinates : coordinateList) {
            assertThat(firingResponse, at(coordinates).is(MISS));
        }

    }

    @Test
    public void server_returns_HIT_when_opponent_hits_single_shot() {

        game.battlefield().insert(new Angle()).at(Coordinates.of(0, 0));

        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame, aiming(coordinates));

        assertThat(result, at(coordinates).is(HIT));
    }

    @Test
    public void server_returns_KILL_when_opponent_kills_a_single_ship() {

        game.battlefield().insert(new Angle()).at(Coordinates.of(0, 0));

        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(1, 0);
        result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(2, 0);
        result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 0);
        result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 1);
        result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 2);
        result = opponent.fire(newGame, aiming(coordinates));
        assertThat(result, at(coordinates).is(KILL));
    }

}