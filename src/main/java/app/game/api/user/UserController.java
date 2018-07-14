package app.game.api.user;

import io.javalin.Context;

public class UserController {


    public static void getGameStatus(Context context) {

    }

    public static void newGame(Context context) {

    }

    public static void fire(Context context) {
    }

    public static void auto(Context context) {
    }

    public static void statusHandler(Context ctx) {
        Status status = new Status();
        ctx.status(200).json(status);
    }

}
