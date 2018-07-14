package app.game.ship.frame.rotation;

import app.game.ship.Ship;

import java.util.Random;

public final class Matrix {

    private Matrix() {
    }

    public static Ship[][] rotateRandomly(Ship[][] array) {
        return new Matrix().doRotateRandomly(array);
    }

    private Ship[][] doRotateRandomly(Ship[][] array) {
        int count = new Random().nextInt(4);
        return rotate(array, count);
    }

    private Ship[][] rotate(Ship[][] array, int count) {
        for (int i = 1; i <= count; i++) {
            array = rotateClockwise(array);
        }
        return array;
    }

    private Ship[][] rotateClockwise(Ship[][] arr) {
        Ship[][] ret = new Ship[arr[0].length][arr.length];
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[0].length; c++) {
                ret[c][arr.length - 1 - r] = arr[r][c];
            }
        }
        return ret;
    }
}
