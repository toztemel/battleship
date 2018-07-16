package app.game;

import app.game.api.client.BattleshipClient;
import app.game.api.dto.game.NewGame;
import app.game.battlefield.Battlefield;
import app.game.service.ActiveGames;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static app.game.TestUtil.LOCALHOST_7000;
import static app.game.TestUtil.newGameRequest;
import static org.junit.Assert.assertNotNull;

public class BattlefieldITest {

    private BattleshipClient opponent;
    private BattleshipGame ownServer;

    @Before
    public void setup() {
        ownServer = new BattleshipGame();
        ownServer.start();

        opponent = BattleshipClient.getInstance()
                .target(LOCALHOST_7000);
    }

    @After
    public void cleanup() {
        ownServer.stop();
    }

    @Test
    public void battlefield_with_random_ships_is_generated_when_newgame_request_is_received() {
        NewGame response = opponent.challengeOpponent(newGameRequest());
        String gameId = response.getGameId();

        Battlefield battlefield = ActiveGames.getInstance().getBattlefield(gameId);

        assertNotNull(battlefield);
        System.out.println(Arrays.deepToString(battlefield.asString()));


    }
}
