package app.game.fire;

public class Shot {

    public final int row;
    public final int column;

    public Shot(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public enum Damage {
        MISS, HIT, DESTROYED;
    }

}
