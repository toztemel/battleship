package app.game.fire;

public final class Shot {

    private Coordinates coordinates;

    private Shot(Coordinates target) {
        coordinates = target;
    }

    public static Shot at(int i, int j) {
        return new Shot(Coordinates.of(i, j));
    }

    public int row() {
        return coordinates.row();
    }

    public int col() {
        return coordinates.column();
    }

    public enum Damage {
        MISS, HIT, KILL;
    }

}
