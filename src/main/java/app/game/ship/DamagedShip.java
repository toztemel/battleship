package app.game.ship;

public class DamagedShip implements Ship {

    private static Ship instance = new DamagedShip();

    private DamagedShip() {
    }

    public static Ship getInstance() {
        return instance;
    }

    public static void setInstance(Ship ship) {
        instance = ship;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int width() {
        return 0;
    }
}
