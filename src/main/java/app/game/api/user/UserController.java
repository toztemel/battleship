package app.game.api.user;

import io.javalin.Context;

public class UserController {

    public void onNewGame(Context context) {

    }

    public void onFire(Context context) {
    }

    public void auto(Context context) {
    }

    public void onStatus(Context ctx) {
        Status status = new Status();
        ctx.status(200).json(status);
    }

}
