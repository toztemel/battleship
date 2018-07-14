package app.game.util;

import app.game.ship.Emptiness;
import app.game.ship.Ship;

import java.util.Arrays;

public class Utility {

    public static void print2DArray(Object[][] frame) {
        System.out.println();
        for (int i = 0; i < frame.length; i++) {
            System.out.println(Arrays.deepToString(frame[i]));
        }
        System.out.println();
    }

    public static void fillEmpty(Ship[][] ships) {
        for (int i = 0; i < ships.length; i++) {
            Arrays.fill(ships[i], Emptiness.instance());
        }
    }
}
