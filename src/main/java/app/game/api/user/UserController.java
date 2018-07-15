package app.game.api.user;

import io.javalin.Context;

public class UserController {

    public void newGame(Context context) {

    }

    public void fire(Context context) {
    }

    public void auto(Context context) {
    }

    public void statusHandler(Context ctx) {
        Status status = new Status();
        ctx.status(200).json(status);
    }

}
