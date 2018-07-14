package app.game.api.user;

import io.javalin.Context;
import io.javalin.Handler;

public class UserController {


    public static void getGameStatus(Context context) {

    }

    public static void newGame(Context context) {

    }

    public static void fire(Context context) {
    }

    public static void auto(Context context) {
    }

    public Handler statusHandler() {
        return ctx -> {
            Status status = new Status();
            ctx.status(200).json(status);
        };
    }
}
