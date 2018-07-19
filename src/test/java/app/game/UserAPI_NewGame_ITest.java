package app.game;

import app.game.api.dto.game.Rule;
import app.game.api.BattlefieldAPIConf;
import app.game.service.user.UserServiceConf;
import app.game.util.api.UserTestClient;
import app.game.api.dto.game.NewGame;
import app.game.battlefield.Battlefield;
import app.game.service.cache.GameCacheService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static app.game.util.TestUtil.LOCALHOST_7000;
import static app.game.util.TestUtil.LOCALHOST_7001;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class UserAPI_NewGame_ITest {

    private BattleshipGame ownServer;
    private BattleshipGame opponentServer;
    private UserTestClient user;
    private UserTestClient opponent;
    private UserServiceConf opponentConf;
    private UserServiceConf userConf;

    @Before
    public void setUp() throws Exception {
        user = new UserTestClient(LOCALHOST_7000);
        ownServer = new BattleshipGame();
        userConf = new UserServiceConf();
        userConf.defaultUserName("challenger-X");
        userConf.defaultUserName("Lunatech NL Champion");
        ownServer.start(new BattlefieldAPIConf().httpServerPort(7000), userConf);

        opponent = new UserTestClient(LOCALHOST_7001);
        opponentServer = new BattleshipGame();
        opponentConf = new UserServiceConf();
        opponentConf.defaultUserName("challenger-Y");
        opponentConf.defaultUserName("Lunatech FR Champion");
        opponentServer.start(new BattlefieldAPIConf().httpServerPort(7001), opponentConf);
    }

    @After
    public void tearDown() throws Exception {
        ownServer.stop();
        opponentServer.stop();
    }

    @Ignore
    @Test
    public void server_sends_an_invitation_when_user_starts_a_new_game() {
    }


    @Test
    public void server_creates_new_board_when_user_starts_new_game() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setRule(Rule.STANDARD);
        newGameRequest.setProtocol("localhost:7001");
        NewGame newGame = user.challangeOpponent(newGameRequest);

        Battlefield ownBattlefield = GameCacheService.getInstance().getBattlefield(newGame.getGameId());
    }

    @Test
    public void server_challanges_opponent_when_user_starts_new_game() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setRule(Rule.STANDARD);
        newGameRequest.setProtocol("localhost:7001");
        NewGame newGame = user.challangeOpponent(newGameRequest);

        assertEquals("challenger-Y", newGame.getUserId());
        assertEquals("Lunatech FR Champion", newGame.getFullName());
        assertEquals("challenger-X", newGame.getStarting());
        assertEquals("standard", newGame.getRule());
        assertNotNull(newGame.getGameId());
    }

    /**
     * When user starts a match against an opponent
     * 1. a new board is  generated
     * 2. ships are placed randomly
     * 3. request is forwarded to opponent
     */

}