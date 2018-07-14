package app.game.ship.frame;

import app.game.ship.DamagedShip;
import app.game.ship.Ship;
import app.game.fire.Shot;
import app.game.fire.Shot.Damage;

import java.util.Arrays;

public abstract class Frame {

    Ship[][] frame;

    Ship[][] getFrame() {
        return frame;
    }

    public Damage hitBy(Shot shot) {
        if (outOfBoundaries(shot) || empty(shot)) {
            return Damage.MISS;
        }
        getHitBy(shot);
        return allPartsHit() ? Damage.DESTROYED : Damage.HIT;
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


}
