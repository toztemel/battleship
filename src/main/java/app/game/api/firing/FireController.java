package app.game.api.firing;

import io.javalin.Context;

public class FireController {

    public static void firingHandler(Context ctx) {
        String gameId = ctx.param("gameId");
        System.out.println(gameId);

        FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
        Shots shots = new Shots();
        shots.put(firingRequest.getShots()[0], "hit");
        shots.put(firingRequest.getShots()[1], "kill");
        shots.put(firingRequest.getShots()[2], "miss");
        FiringResponse response = new FiringResponse();
        response.setShots(shots);

        Game game = new Game();
        game.setStatus("player_turn");
        game.setOwner("challanger-Y");
        response.setGame(game);

        ctx.status(200).json(response);
    }

}
