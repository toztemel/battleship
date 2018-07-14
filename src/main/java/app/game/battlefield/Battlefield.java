package app.game.battlefield;

import app.game.ship.Ship;
import app.game.util.Printer;

public class Battlefield {

    private static final int SIZE = 16;

    private Ship[][] field;

    Battlefield() {
        field = new Ship[SIZE][SIZE];
    }

    int getLength() {
        return field.length;
    }

    int getWidth() {
        return field[0].length;
    }

    void insert(Ship ship, int row, int column) {
        for (int i = 0; i < ship.length(); i++) {
            for (int j = 0; j < ship.width(); j++) {
                field[row+i][column+j] = ship;
            }
        }
        Printer.print2DArray(field);
    }

    Ship getCell(int i, int j) {
        return field[i][j];
    }
}
