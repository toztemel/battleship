package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.game.Rule;
import app.game.api.dto.status.GameStatus;
import app.game.battlefield.Battlefield;
import app.game.service.cache.GameCacheService;
import app.game.service.Game;
import app.game.service.ProtocolService;
import app.game.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static app.game.util.TestUtil.LOCALHOST_7000;
import static org.junit.Assert.*;

public class ProtocolAPI_IncomingNewGame_ITest {

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

    @Test
    public void server_generates_a_battlefield_with_random_ships_when_newgame_request_is_received() {
        NewGame request = new NewGame();
        request.setUserId("challenger-X");
        request.setFullName("Lunatech NL Champion");
        request.setProtocol("192.168.0.10:8080");
        request.setRule(Rule.STANDARD);

        NewGame response = opponent.challengeOpponent(request);

        Battlefield battlefield = GameCacheService.getInstance().getBattlefield(response.getGameId());
        assertNotNull(battlefield);
        assertFalse(battlefield.allShipsKilled());
//        System.out.println(Arrays.deepToString(battlefield.asString()));
    }

    @Test
    public void server_generates_a_new_activegame_when_newgame_request_is_received() {
        NewGame request = new NewGame();
        request.setFullName("Tayfun");
        request.setUserId("t123");
        request.setProtocol("10.10.0.123:7070");
        request.setRule(Rule.STANDARD);

        NewGame response = opponent.challengeOpponent(request);

        Game game = GameCacheService.getInstance().getGame(response.getGameId());
        assertNotNull(game);
        assertEquals(request.getUserId(), game.getOpponentId());
        assertEquals(request.getUserId(), game.getGameOwner());
        assertEquals(request.getFullName(), game.getOpponentName());
        assertEquals(request.getProtocol(), game.getOpponentProtocol());
        assertEquals(request.getRule(), game.getGameRule());
        assertEquals(GameStatus.Status.player_turn, game.getGameStatus());
        assertEquals(response.getUserId(), game.getUserId());
        assertEquals(response.getFullName(), game.getUserName());
        assertEquals(response.getProtocol(), game.getUserProtocol());
    }

    @Test
    public void server_responds_to_a_new_game_invitation() throws UnknownHostException {
        NewGame request = new NewGame();
        request.setUserId("challenger-X");
        request.setFullName("Lunatech NL Champion");
        request.setProtocol("192.168.0.10:8080");
        request.setRule(Rule.DESPERATION);

        NewGame response = opponent.challengeOpponent(request);

        UserService userService = UserService.getInstance();
        ProtocolService protocolService = ProtocolService.getInstance();
        assertEquals(userService.ownUserId(), response.getUserId());
        assertEquals(userService.ownFullName(), response.getFullName());
        assertEquals(protocolService.getOwnProtocol(), response.getProtocol());
        assertNotNull(response.getGameId());
        assertEquals(request.getUserId(), response.getStarting());
        assertEquals(request.getRule(), response.getRule());


    }

}