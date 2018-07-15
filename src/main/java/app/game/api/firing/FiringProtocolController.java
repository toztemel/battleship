package app.game.api.firing;

import app.game.battlefield.Battlefield;
import app.game.fire.CoordinatesFactory;
import app.game.fire.Shot;
import io.javalin.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiringProtocolController {

    private Battlefield battlefield;

    public FiringProtocolController(Battlefield battlefield) {

        this.battlefield = battlefield;
    }

    // TODO refactor
    public void onFire(Context ctx) {
        String gameId = ctx.param("gameId");
        checkGameId(gameId);

        FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
        String[] incomingShots = firingRequest.getShots();
        checkGameRules(gameId, incomingShots);

        List<Shot> shotList = Arrays.stream(incomingShots)
                .map(CoordinatesFactory::fromProtocolString)
                .map(Shot::new)
                .collect(Collectors.toList());

        shotList.forEach(s -> battlefield.fireAt(s));

        Shots shots = new Shots();
        shotList.forEach(shot -> shots.put(shot.asHexString(), shot.result().toString()));

        Game game = new Game();
        game.setStatus("player_turn");
        game.setOwner("challanger-Y");

        FiringResponse response = new FiringResponse();
        response.setShots(shots);
        response.setGame(game);

        ctx.status(200).json(response);
    }

    // TODO
    private void checkGameId(String gameId) {

    }

    // TODO check game rules
    private void checkGameRules(String gameId, String[] incomingShots) throws IllegalArgumentException {

    }
}
