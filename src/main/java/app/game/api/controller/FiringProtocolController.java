package app.game.api.controller;

import app.game.api.dto.firing.FiringRequest;
import app.game.api.dto.firing.FiringResponse;
import app.game.api.dto.status.GameStatus;
import app.game.api.dto.firing.FiringResults;
import app.game.battlefield.Battlefield;
import app.game.fire.CoordinatesFormatter;
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
        try {
            String gameId = ctx.param("gameId");
            checkGameId(gameId);

            FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
            String[] incomingShots = firingRequest.getShots();
            checkGameRules(gameId, incomingShots);

            List<Shot> shotList = Arrays.stream(incomingShots)
                    .map(CoordinatesFormatter::fromProtocolString)
                    .map(Shot::new)
                    .collect(Collectors.toList());

            FiringResults shots = new FiringResults();
            for(Shot shot : shotList) {
                Shot.Damage d = battlefield.fireAt(shot);
                shots.put(shot.asHexString(), d.toString());
            }

            GameStatus game = new GameStatus();

            if (battlefield.allShipsKilled()) {
                game.setStatus(GameStatus.Mode.won);
            } else {
                game.setStatus(GameStatus.Mode.player_turn);
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
