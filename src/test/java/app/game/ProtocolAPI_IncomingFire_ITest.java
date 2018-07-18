package app.game;

import app.game.api.protocol.client.ProtocolApiClient;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.game.NewGame;
import app.game.api.dto.game.Rule;
import app.game.battlefield.Battlefield;
import app.game.fire.Coordinates;
import app.game.service.cache.GameCacheService;
import app.game.ship.Angle;
import app.game.ship.NullShipObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static app.game.ShotDamageMatcher.at;
import static app.game.battlefield.BattlefieldTestDecorator.decorate;
import static app.game.fire.Shot.Damage.HIT;
import static app.game.fire.Shot.Damage.MISS;
import static app.game.util.TestUtil.*;
import static org.junit.Assert.assertThat;

public class ProtocolAPI_IncomingFire_ITest {

    private BattleshipGame game;
    private ProtocolApiClient opponent;

    @Before
    public void setUp() {
        game = new BattleshipGame();
        game.start();

        opponent = ProtocolApiClient.getInstance().target(LOCALHOST_7000);
    }

    @After
    public void tearDown() throws Exception {
        game.stop();
    }

    @Test(expected = BadRequestException.class)
    public void server_responds_with_error_to_firing_without_initialization() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRule(Rule.STANDARD);

        NewGame newGameResponse = opponent.challengeOpponent(newGameRequest);
        newGameResponse.setGameId("AN_ARBITRARY_ID");

        opponent.fire(newGameResponse.getGameId(), aiming(Coordinates.of(0, 0)));
    }

    @Ignore
    @Test
    public void server_responds_with_error_to_firing_after_game_ends() {
    }

    @Ignore
    @Test
    public void server_responds_with_error_to_firing_multiple_times_in_simple_mode() {
    }

    @Test(expected = BadRequestException.class)
    public void server_responds_with_error_to_consecutive_fires_in_simple_mode() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRule(Rule.STANDARD);

        NewGame newGame = opponent.challengeOpponent(newGameRequest);

        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(0, 0)));
        opponent.fire(newGame.getGameId(), aiming(Coordinates.of(0, 0)));
    }

    @Test
    public void server_returns_MISS_when_opponent_misses_single_shot() {
        NewGame newGameRequest = new NewGame();
        newGameRequest.setUserId("challenger-X");
        newGameRequest.setFullName("Lunatech NL Champion");
        newGameRequest.setProtocol("192.168.0.10:8080");
        newGameRequest.setRule(Rule.STANDARD);

        NewGame newGame = opponent.challengeOpponent(newGameRequest);

        Coordinates coordinates = Coordinates.of(0, 0);
        Battlefield battlefield = GameCacheService.getInstance()
                .getBattlefield(newGame.getGameId());
        decorate(battlefield)
                .with(NullShipObject.instance(), coordinates);

        FiringResponse result = opponent.fire(newGame.getGameId(), aiming(coordinates));

        assertThat(result, at(coordinates).is(MISS));
    }

    @Test
    public void server_returns_HIT_when_opponent_hits_single_shot() {


        NewGame newGame = opponent.challengeOpponent(newGameRequest());
        Battlefield battlefield = GameCacheService.getInstance()
                .getBattlefield(newGame.getGameId());
        decorate(battlefield)
                .with(new Angle(), Coordinates.of(0, 0));

        Coordinates coordinates = Coordinates.of(0, 0);
        FiringResponse result = opponent.fire(newGame.getGameId(), aiming(coordinates));

        assertThat(result, at(coordinates).is(HIT));
    }

}