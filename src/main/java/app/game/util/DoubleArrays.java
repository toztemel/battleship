package app.game.util;

import app.game.fire.Coordinates;
import app.game.ship.Ship;

import java.util.stream.Stream;

public final class DoubleArrays {

    private DoubleArrays() {
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

    public static String[] asString(String[][] frame) {
        String[] result = new String[frame.length];

        StringBuilder sb;
        for (int i = 0; i < frame.length; i++) {
            sb = new StringBuilder();
            for (int j = 0; j < frame[0].length; j++) {
                sb.append(frame[i][j]);
            }
            result[i] = sb.toString();
        }
        return result;
    }

    public static void print2DArray(String[] strings) {
        Stream.of(strings)
                .forEach(System.out::println);
    }

    public static void fill(String[][] matrix, String s) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = s;
            }
        }
    }
}
