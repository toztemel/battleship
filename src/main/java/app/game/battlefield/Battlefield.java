package app.game.battlefield;

import app.game.conf.BattlefieldConf;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.Battleship;
import app.game.ship.NullShipObject;
import app.game.ship.Ship;
import app.game.util.DoubleArrays;

import java.util.Arrays;
import java.util.stream.Stream;

public class Battlefield {

    private Ship[][] field;
    private Ship aShip;
    private BattlefieldConf conf;

    Battlefield() {
    }

    int length() {
        return field.length;
    }

    int width() {
        return field[0].length;
    }

    Ship shipAt(Coordinates c) {
        return field[c.row()][c.column()];
    }

    public Battlefield with(Ship s) {
        aShip = s;
        return this;
    }

    public String[] asString() {
        return DoubleArrays.asString(field);
    }

    public Battlefield at(Coordinates coordinates) {
        aShip.goTo(field, coordinates);
        return this;
    }

    public Shot.Damage fireAt(Shot shot) {
        Ship ship = field[shot.row()][shot.col()];
        if (ship == NullShipObject.instance()) {
            return Shot.Damage.MISS;
        } else {
            return ((Battleship) ship).hitBy(shot);
        }
    }

    public boolean allShipsKilled() {
        return Stream.of(field)
                .flatMap(Arrays::stream)
                .noneMatch(Ship::isAlive);
    }

    Battlefield setConf(BattlefieldConf conf) {
        this.conf = conf;
        return this;
    }

    Battlefield build() {
        field = new Ship[conf.size()][conf.size()];
        DoubleArrays.fillEmpty(field);
        return this;
    }
}
