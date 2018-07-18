package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.status.GameStatus;
import app.game.api.dto.status.OpponentStatus;
import app.game.api.dto.status.SelfStatus;
import app.game.api.dto.status.StatusResponse;
import app.game.fire.Coordinates;
import app.game.service.GameCache;
import app.game.ship.Angle;
import app.game.ship.SWing;
import app.game.util.api.UserTestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static app.game.util.TestUtil.LOCALHOST_7000;
import static app.game.util.TestUtil.newGameRequest;
import static org.junit.Assert.*;

@Ignore
public class UserAPI_Status_ITest {

    private BattleshipGame ownServer;
    private UserTestClient ownUser;
    private BattleshipClient opponentServer;
    private NewGame newGame;

    @Before
    public void setUp() throws Exception {
        ownServer = new BattleshipGame();
        ownServer.start();

        ownUser = new UserTestClient(LOCALHOST_7000);

        opponentServer = BattleshipClient.getInstance().target(LOCALHOST_7000);
        newGame = opponentServer.challengeOpponent(newGameRequest());
    }

    @After
    public void tearDown() throws Exception {
        ownServer.stop();
    }

    @Ignore
    @Test
    public void server_returns_new_game_owner_when_game_status_changes() {
        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);
        String owner = gameStatusResponse.getGame().getOwner();
        // ownServer proceeds

        StatusResponse gameStatusResponse2 = ownUser.queryGameStatus(newGame);
        String newOwner = gameStatusResponse2.getGame().getOwner();

    }

    @Test
    public void server_returns_game_status_by_gameId() {

        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        assertNotNull(gameStatusResponse.getGame());
        assertEquals(GameStatus.Status.player_turn, gameStatusResponse.getGame().getStatus());
        assertEquals("challenger-Y", gameStatusResponse.getGame().getOwner());

        SelfStatus self = gameStatusResponse.getSelf();
        assertNotNull(self);
        assertEquals("challenger-Y", self.getUserId());

        OpponentStatus opponent = gameStatusResponse.getOpponent();
        assertNotNull(opponent);
        assertEquals("challenger-X", opponent.getUserId());
        assertTrue(emptyOrUnknownBattlefield(opponent));
    }

    @Test
    public void server_returns_game_status_including_a_ship() {
        GameCache.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new Angle()).at(Coordinates.of(0, 0));

        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        SelfStatus self = gameStatusResponse.getSelf();
        String[] board = self.getBoard();

        assertEquals("*...............", board[0]);
        assertEquals("*...............", board[1]);
        assertEquals("*...............", board[2]);
        assertEquals("***.............", board[3]);
        assertEquals("................", board[4]);

    }

    @Test
    public void server_returns_game_status_including_two_ships() {
        GameCache.getInstance()
                .getBattlefield(newGame.getGameId())
                .with(new Angle()).at(Coordinates.of(0, 0))
                .with(new SWing()).at(Coordinates.of(10, 10));

        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        SelfStatus self = gameStatusResponse.getSelf();
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
        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        OpponentStatus opponent = gameStatusResponse.getOpponent();
        assertTrue(emptyOrUnknownBattlefield(opponent));
    }

    @Ignore
    @Test
    public void server_returns_opponent_board_with_misses() {
        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        OpponentStatus opponent = gameStatusResponse.getOpponent();
    }

    @Ignore
    @Test
    public void server_returns_opponent_board_with_hits() {
        StatusResponse gameStatusResponse = ownUser.queryGameStatus(newGame);

        OpponentStatus opponent = gameStatusResponse.getOpponent();
    }

    private boolean emptyOrUnknownBattlefield(OpponentStatus opponent) {
        return Arrays.stream(opponent.getBoard()).allMatch(s -> s.equals("................"));
    }

}