package app.game;

import app.game.api.client.UserTestClient;
import app.game.api.game.NewGame;
import app.game.battlefield.Battlefield;
import app.game.util.DoubleArrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static app.game.TestUtil.LOCALHOST_7000;
import static app.game.TestUtil.LOCALHOST_7001;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserNewGameE2EITest {

    private BattleshipGame ownServer;
    private BattleshipGame opponentServer;
    private UserTestClient user;
    private UserTestClient opponent;

    @Before
    public void setUp() throws Exception {
        user = new UserTestClient(LOCALHOST_7000);
        ownServer = new BattleshipGame();
        ownServer.start(7000);

        opponent = new UserTestClient(LOCALHOST_7001);
        opponentServer = new BattleshipGame();
        opponentServer.start(7001);
    }

    @After
    public void tearDown() throws Exception {
        ownServer.stop();
        opponentServer.stop();
    }

    @Test
    public void server_creates_new_board_when_user_starts_new_game() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setRules("standard");
        newGameRequest.setProtocol("localhost:7001");
        user.challangeOpponent(newGameRequest);

        Battlefield ownBattlefield = ownServer.battlefield();
    }

    @Test
    public void server_challanges_opponent_when_user_starts_new_game() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setRules("standard");
        newGameRequest.setProtocol("localhost:7001");
        NewGame newGame = user.challangeOpponent(newGameRequest);

        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRules());
        assertNotNull(newGame.getGameId());
    }

    /**
     * When user starts a match against an opponent
     * 1. a new board is  generated
     * 2. ships are placed randomly
     * 3. request is forwarded to opponent
     */

}