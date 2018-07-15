package app.game.util;

import app.game.fire.Coordinates;
import app.game.ship.Emptiness;
import app.game.ship.Ship;

import java.util.Arrays;

public final class DoubleArrays {

    private DoubleArrays() {
    }

    public static void print2DArray(Ship[][] frame) {
        System.out.println();
        for (int i = 0; i < frame.length; i++) {
            System.out.print("[");
            for (int j = 0; j < frame[0].length; j++) {
                System.out.print(frame[i][j].toStringAt(Coordinates.of(i, j)));
                if (j < frame[0].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public static String[] asString(Ship[][] frame) {
        String[] result = new String[frame.length];

        StringBuilder sb;
        for (int i = 0; i < frame.length; i++) {
            sb = new StringBuilder();
            for (int j = 0; j < frame[0].length; j++) {
                sb.append(frame[i][j].toStringAt(Coordinates.of(i, j)));
            }
            result[i] = sb.toString();
        }
        return result;
    }

    public static void fillEmpty(Ship[][] ships) {
        for (int i = 0; i < ships.length; i++) {
            Arrays.fill(ships[i], Emptiness.instance());
        }
    }
}
