package app.game.ship;

public class DamagedShip implements Ship {

    private static Ship instance = new DamagedShip();

    private DamagedShip() {
    }

    public static Ship getInstance() {
        return instance;
    }
}
