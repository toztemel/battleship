package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.client.UserTestClient;
import app.game.api.firing.Game;
import app.game.api.game.NewGame;
import app.game.api.user.Opponent;
import app.game.api.user.Self;
import app.game.api.user.Status;
import app.game.fire.Coordinates;
import app.game.ship.Angle;
import app.game.ship.SWing;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static app.game.TestUtil.LOCALHOST_7000;
import static app.game.TestUtil.newGameRequest;
import static org.junit.Assert.*;

public class UserNewGameITest {

    private BattleshipGame game;
    private UserTestClient user;
    private BattleshipClient opponent;
    private NewGame newGame;

    @Before
    public void setUp() throws Exception {
        game = new BattleshipGame();
        game.start();

        user = new UserTestClient(LOCALHOST_7000);

        opponent = new BattleshipClient(LOCALHOST_7000);
        newGame = opponent.challengeOpponent(newGameRequest());
    }

    @After
    public void tearDown() throws Exception {
        game.stop();
    }

    @Ignore
    @Test
    public void server_returns_new_game_owner_when_game_status_changes() {
        Status gameStatus = user.queryGameStatus(newGame);
        String owner = gameStatus.getGame().getOwner();
        // game proceeds

        Status gameStatus2 = user.queryGameStatus(newGame);
        String newOwner = gameStatus2.getGame().getOwner();

    }

    @Test
    public void server_returns_game_status_by_gameId() {

        Status gameStatus = user.queryGameStatus(newGame);

        assertNotNull(gameStatus.getGame());
        assertEquals(Game.GameStatus.player_turn, gameStatus.getGame().getStatus());
        assertEquals("challenger-Y", gameStatus.getGame().getOwner());

        Self self = gameStatus.getSelf();
        assertNotNull(self);
        assertEquals("challenger-Y", self.getUserId());
        assertTrue(Arrays.stream(self.getBoard()).allMatch(s -> s.equals("................")));

        Opponent opponent = gameStatus.getOpponent();
        assertNotNull(opponent);
        assertEquals("challenger-X", opponent.getUserId());
        assertTrue(Arrays.stream(opponent.getBoard()).allMatch(s -> s.equals("................")));
    }

    @Test
    public void server_returns_game_status_including_a_ship() {
        game.battlefield().with(new Angle()).at(Coordinates.of(0, 0));

        Status gameStatus = user.queryGameStatus(newGame);

        Self self = gameStatus.getSelf();
        String[] board = self.getBoard();
        assertEquals("*...............", board[0]);
        assertEquals("*...............", board[1]);
        assertEquals("*...............", board[2]);
        assertEquals("***.............", board[3]);
        assertEquals("................", board[4]);

    }

    @Test
    public void server_returns_game_status_including_two_ships() {
        game.battlefield()
                .with(new Angle()).at(Coordinates.of(0, 0))
                .with(new SWing()).at(Coordinates.of(10, 10));

        Status gameStatus = user.queryGameStatus(newGame);

        Self self = gameStatus.getSelf();
        String[] board = self.getBoard();
        assertEquals("*...............", board[0]);
        assertEquals("*...............", board[1]);
        assertEquals("*...............", board[2]);
        assertEquals("***.............", board[3]);
        assertEquals("................", board[4]);

        assertEquals("...........**...", board[10]);
        assertEquals("...........*....", board[11]);
        assertEquals("..........**....", board[12]);
    }

    @Test
    public void server_returns_empty_opponent_board() {
        Status gameStatus = user.queryGameStatus(newGame);

        Opponent opponent = gameStatus.getOpponent();
        assertTrue(Arrays.stream(opponent.getBoard()).allMatch(s -> s.equals("................")));
    }

    @Ignore
    @Test
    public void server_returns_opponent_board_with_misses() {
        Status gameStatus = user.queryGameStatus(newGame);

        Opponent opponent = gameStatus.getOpponent();
    }

    @Ignore
    @Test
    public void server_returns_opponent_board_with_hits() {
        Status gameStatus = user.queryGameStatus(newGame);

        Opponent opponent = gameStatus.getOpponent();
    }


}