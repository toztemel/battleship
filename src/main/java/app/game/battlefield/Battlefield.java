package app.game.battlefield;

import app.game.api.game.NewGame;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.*;
import app.game.util.DoubleArrays;

import java.util.Arrays;
import java.util.stream.Stream;

import static app.game.battlefield.Constants.BATTLEFIELD_SIZE;

public class Battlefield implements ShipHolder, Inserter {

    private Ship[][] field;
    private Ship aShip;

    Battlefield() {
        initializeField();
    }

    public static Inserter getNewInstance() {
        return new Battlefield();
    }

    private void initializeField() {
        field = new Ship[BATTLEFIELD_SIZE][BATTLEFIELD_SIZE];
        DoubleArrays.fillEmpty(field);
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

    @Override
    public ShipHolder with(Ship s) {
        aShip = s;
        return this;
    }

    @Override
    public Battlefield build() {
        return this;
    }

    public String[] asString() {
        return DoubleArrays.asString(field);
    }

    @Override
    public Battlefield at(Coordinates coordinates) {
        int row = coordinates.row();
        int column = coordinates.column();
        for (int i = 0; i < aShip.length(); i++) {
            for (int j = 0; j < aShip.width(); j++) {
                field[row + i][column + j] = aShip;
                aShip.insertedAt(coordinates);
            }
        }
        return this;
    }

    public Shot.Damage fireAt(Shot shot) {
        Ship ship = field[shot.row()][shot.col()];
        if (ship == Emptiness.instance()) {
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

    public void reset(NewGame newRequest) {
        initializeField();
    }
}
