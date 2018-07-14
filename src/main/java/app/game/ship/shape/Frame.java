package app.game.ship.shape;

import app.game.ship.DamagedShip;
import app.game.ship.Ship;
import app.game.ship.fire.Shot;
import app.game.ship.fire.Shot.Damage;

import java.util.Arrays;

public abstract class Frame {

    Ship[][] frame;

    Ship[][] getFrame() {
        return frame;
    }

    public Damage hitBy(Shot shot) {
        if (outOfRow(shot) || outOfColumn(shot)) {
            return Damage.MISS;
        }
        if (empty(shot)) {
            return Damage.MISS;
        }
        getHitBy(shot);
        return isDestroyed() ? Damage.DESTROYED : Damage.HIT;
    }

    private boolean isDestroyed() {
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

    private boolean outOfColumn(Shot shot) {
        return frame[0].length <= shot.column;
    }

    private boolean outOfRow(Shot shot) {
        return frame.length <= shot.row;
    }


}
