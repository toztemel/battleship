package app.game.ship;

public class Emptiness implements Ship {

    private static Ship instance = new Emptiness();

    private Emptiness() {
    }

    public static Ship instance() {
        return instance;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public String toString() {
        return ".";
    }
}
