package app.game.ship;

public class DamagedShip implements Ship {

    private static Ship instance = new DamagedShip();

    private DamagedShip() {
    }

    public static Ship getInstance() {
        return instance;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public int width() {
        return 1;
    }

    @Override
    public String toString() {
        return "X";
    }
}
