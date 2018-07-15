package app.game.api.firing;

import app.game.battlefield.Battlefield;
import app.game.fire.CoordinatesFactory;
import app.game.fire.Shot;
import io.javalin.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiringProtocolController {

    private Battlefield battlefield;

    public FiringProtocolController(Battlefield battlefield) {

        this.battlefield = battlefield;
    }

    // TODO refactor
    public void onFire(Context ctx) {
        try {
            String gameId = ctx.param("gameId");
            checkGameId(gameId);

            FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
            String[] incomingShots = firingRequest.getShots();
            checkGameRules(gameId, incomingShots);

            List<Shot> shotList = Arrays.stream(incomingShots)
                    .map(CoordinatesFactory::fromProtocolString)
                    .map(Shot::new)
                    .collect(Collectors.toList());

            Shots shots = new Shots();
            for(Shot shot : shotList) {
                Shot.Damage d = battlefield.fireAt(shot);
                shots.put(shot.asHexString(), d.toString());
            }

            Game game = new Game();

            if (battlefield.allShipsKilled()) {
                game.setStatus(Game.GameStatus.won);
            } else {
                game.setStatus(Game.GameStatus.player_turn);
            }
            game.setOwner("challenger-Y");

            FiringResponse response = new FiringResponse();
            response.setShots(shots);
            response.setGame(game);

            ctx.status(200).json(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO
    private void checkGameId(String gameId) {

    }

    // TODO check game rules
    private void checkGameRules(String gameId, String[] incomingShots) throws IllegalArgumentException {

    }
}
