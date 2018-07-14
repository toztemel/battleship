package app.game.ship.frame;

import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import app.game.ship.DamagedShip;
import app.game.ship.Emptiness;
import app.game.ship.Ship;
import app.game.ship.frame.rotation.Matrix;
import app.game.util.DoubleArrays;

import java.util.Arrays;

public abstract class Frame {

    Ship[][] frame;
    Ship ship;

    Frame(Ship owner, Ship[][] ships) {
        initializeFrame(owner, ships);
    }

    private void initializeFrame(Ship owner, Ship[][] ships) {
        DoubleArrays.fillEmpty(ships);
        frame = ships;
        ship = owner;
    }

    public void rotate() {
        frame = Matrix.rotateRandomly(frame);
        DoubleArrays.print2DArray(frame);
    }

    public Damage hitBy(Shot shot) {
        if (outOfBoundaries(shot) || empty(shot)) {
            return Damage.MISS;
        }
        getHitBy(shot);
        return allPartsHit() ? Damage.KILL : Damage.HIT;
    }

    void fill(Coordinates coordinates) {
        frame[coordinates.row()][coordinates.column()] = ship;
    }

    private boolean outOfBoundaries(Shot shot) {
        return frame.length <= shot.row()
                || frame[0].length <= shot.col();
    }

    private boolean allPartsHit() {
        return Arrays.stream(frame)
                .flatMap(Arrays::stream)
                .noneMatch(cell -> !empty(cell) && !damaged(cell));
    }

    private void getHitBy(Shot shot) {
        frame[shot.row()][shot.col()] = DamagedShip.getInstance();
    }

    private boolean damaged(Ship ship) {
        return ship instanceof DamagedShip;
    }

    private boolean empty(Shot shot) {
        return empty(frame[shot.row()][shot.col()]);
    }

    private boolean empty(Ship ship) {
        return ship instanceof Emptiness;
    }

    public int length() {
        return frame.length;
    }

    public int width() {
        return frame[0].length;
    }

    public String toStringAt(Coordinates coordinates) {
        Ship ship = frame[coordinates.row()][coordinates.column()];
        if (ship instanceof DamagedShip) {
            return "X";
        } else if (ship instanceof Emptiness) {
            return ".";
        }
        return "*";
    }
}
