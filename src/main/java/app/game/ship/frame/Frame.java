package app.game.ship.frame;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import app.game.ship.DamagedShip;
import app.game.ship.Ship;
import app.game.ship.frame.rotation.Matrix;

import java.util.Arrays;

public abstract class Frame {

    Ship[][] frame;
    Ship ship;

    Frame(Ship owner, Ship[][] ships) {
        frame = ships;
        ship = owner;
    }

    public void rotate() {
        frame = new Matrix().rotateRandomly(frame);
        printFrame();
    }

    public Damage hitBy(Shot shot) {
        if (outOfBoundaries(shot) || empty(shot)) {
            return Damage.MISS;
        }
        getHitBy(shot);
        return allPartsHit() ? Damage.DESTROYED : Damage.HIT;
    }

    void fill(int row, int column) {
        frame[row][column] = ship;
    }

    private boolean outOfBoundaries(Shot shot) {
        return frame.length <= shot.row || frame[0].length <= shot.column;
    }

    private boolean allPartsHit() {
        return Arrays.stream(frame)
                .flatMap(Arrays::stream)
                .noneMatch(cell -> !empty(cell) && !damaged(cell));
    }

    private void getHitBy(Shot shot) {
        frame[shot.row][shot.column] = DamagedShip.getInstance();
    }

    private boolean damaged(Ship ship) {
        return ship instanceof DamagedShip;
    }

    private boolean empty(Shot shot) {
        return empty(frame[shot.row][shot.column]);
    }

    private boolean empty(Ship ship) {
        return ship == null;
    }

    void printFrame() {
        System.out.println();
        for (int i = 0; i < frame.length; i++) {
            System.out.println(Arrays.deepToString(frame[i]));
        }
        System.out.println();
    }

}
