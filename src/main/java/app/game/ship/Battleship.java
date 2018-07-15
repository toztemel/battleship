package app.game.ship;

import app.game.fire.Shot;

public interface Battleship extends Ship {

    void hitBy(Shot shot);

}
