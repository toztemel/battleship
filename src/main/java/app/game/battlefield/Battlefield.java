package app.game.battlefield;

import app.game.fire.Coordinates;
import app.game.ship.Ship;
import app.game.util.DoubleArrays;

import static app.game.battlefield.Constants.*;

public class Battlefield {

    private Ship[][] field;

    Battlefield() {
        initializeField();
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

    Battlefield insertAt(Ship ship, Coordinates coordinates) {
        int row = coordinates.row();
        int column = coordinates.column();
        for (int i = 0; i < ship.length(); i++) {
            for (int j = 0; j < ship.width(); j++) {
                field[row + i][column + j] = ship;
                ship.insertedAt(coordinates);
            }
        }
        DoubleArrays.print2DArray(field);
        return this;
    }

    Ship shipAt(Coordinates c) {
        return field[c.row()][c.column()];
    }
}
