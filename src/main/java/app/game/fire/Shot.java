package app.game.fire;

public final class Shot {

    private Coordinates coordinates;
    private Damage result;

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

    public Damage result() {
        return result;
    }

    public String asHexString() {
        return coordinates.toHexString();
    }

    public void missed() {
        this.result = Damage.MISS;
    }

    public void killed() {
        this.result = Damage.KILL;
    }

    public void hit() {
        this.result = Damage.HIT;
    }

    public enum Damage {
        MISS, HIT, KILL;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}
