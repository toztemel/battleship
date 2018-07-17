package app.game.fire;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Shot {

    private Coordinates coordinates;

    public Shot(Coordinates target) {
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

    public String asHexString() {
        return coordinates.toHexString();
    }

    public enum Damage {
        @JsonProperty("miss") MISS,
        @JsonProperty("hit") HIT,
        @JsonProperty("kill") KILL
    }

}
