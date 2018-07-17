package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.fire.Coordinates;
import app.game.service.ActiveGames;
import app.game.ship.Angle;
import app.game.ship.SWing;
import app.game.ship.XWing;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static app.game.ShotDamageMatcher.at;
import static app.game.fire.Shot.Damage.*;
import static app.game.util.TestUtil.*;
import static org.junit.Assert.*;

@Ignore
public class ProtocolAPI_OutgoingNewGame_ITest {

    private BattleshipGame game;
    private BattleshipClient opponent;

    @Before
    public void setUp() {
        game = new BattleshipGame();
        game.start();

        opponent = BattleshipClient.getInstance().target(LOCALHOST_7000);
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

    @Ignore
    @Test
    public void server_responds_with_error_to_firing_multiple_times_in_simple_mode() {
    }

    @Ignore
    @Test
    public void server_responds_with_error_to_consequtive_fires_in_simple_mode() {
    }

    @Ignore
    @Test
    public void server_responds_to_a_new_game_invitation() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertNotNull(newGame.getGameId());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
    }

    @Ignore
    @Test
    public void server_returns_MISS_when_opponent_misses_single_shot() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());
        ActiveGames.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new XWing()).at(Coordinates.of(10, 10));

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame.getGameId(), aiming(coordinates));

        assertThat(result, at(coordinates).is(MISS));
    }

    @Test
    public void server_returns_MISS_when_opponent_misses_multiple_shots() {
        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        ActiveGames.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new XWing()).at(Coordinates.of(10, 10));

        Coordinates[] coordinateList = new Coordinates[]
                {
                        Coordinates.of(1, 11)
                        , Coordinates.of(0, 10)
                        , Coordinates.of(5, 1)
                };

        FiringRequest fire = aiming(coordinateList);
        FiringResponse firingResponse = opponent.fire(newGame.getGameId(), fire);

        assertNotNull(firingResponse.getGame());
        assertEquals("challenger-Y", firingResponse.getGame().getOwner());
        assertEquals(GameStatus.Status.player_turn, firingResponse.getGame().getStatus());

        assertNotNull(firingResponse.getShots());
        for (Coordinates coordinates : coordinateList) {
            assertThat(firingResponse, at(coordinates).is(MISS));
        }

    }

    @Test
    public void server_returns_HIT_when_opponent_hits_single_shot() {


        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        ActiveGames.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new Angle()).at(Coordinates.of(0, 0));

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame.getGameId(), aiming(coordinates));

        assertThat(result, at(coordinates).is(HIT));
    }

    @Test
    public void server_returns_KILL_when_opponent_kills_a_single_ship() {

        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        ActiveGames.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new Angle()).at(Coordinates.of(0, 0));

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(1, 0);
        result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(2, 0);
        result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 0);
        result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 1);
        result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(HIT));

        coordinates = Coordinates.of(3, 2);
        result = opponent.fire(newGame.getGameId(), aiming(coordinates));
        assertThat(result, at(coordinates).is(KILL));
    }

    @Test
    public void server_returns_WIN_when_opponent_kills_all_ships() {

        NewGame newGame = opponent.challengeOpponent(newGameRequest());

        ActiveGames.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new Angle()).at(Coordinates.of(0, 0))
                .with(new SWing()).at(Coordinates.of(10, 10));


        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(0, 0)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(1, 0)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(2, 0)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(3, 0)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(3, 1)));
        FiringResponse response = opponent.fire(newGame.getGameId(), aiming(Coordinates.of(3, 2)));

        assertEquals(GameStatus.Status.player_turn, response.getGame().getStatus());
        assertThat(response, at(Coordinates.of(3, 2)).is(KILL));

        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(10, 11)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(10, 12)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(11, 11)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(12, 11)));

        response = opponent.fire(newGame.getGameId(), aiming(Coordinates.of(12, 10)));

        assertThat(response, at(Coordinates.of(12, 10)).is(KILL));
        assertEquals(GameStatus.Status.won, response.getGame().getStatus());
    }

}