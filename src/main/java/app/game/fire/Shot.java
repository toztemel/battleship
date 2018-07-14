package app.game.fire;

import app.game.common.Coordinates;

public class Shot {

    private Coordinates coordinates;

    public Shot(Coordinates target) {
        coordinates = target;
    }

    public static Shot at(int i, int j) {
        return new Shot(Coordinates.of(i, j));
    }

    public Coordinates target() {
        return coordinates;
    }

    public enum Damage {
        MISS, HIT, KILL;
    }

}
