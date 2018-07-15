package app.game.api.firing;

import io.javalin.Handler;

import java.util.Arrays;

public class FireController {

    public static Handler firingHandler = ctx -> {
        String gameId = ctx.param("gameId");
        System.out.println(gameId);

        FiringRequest firingRequest = ctx.bodyAsClass(FiringRequest.class);
        Shots shots = new Shots();
//        Arrays.stream(firingRequest.getShots())
//                .forEach(s -> {shots.put(s, )});
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
    };

}
