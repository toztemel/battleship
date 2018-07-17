package app.game.util;

import app.game.fire.Coordinates;
import app.game.ship.NullShipObject;
import app.game.ship.Ship;

import java.util.Arrays;
import java.util.stream.Stream;

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

    public static void fillEmpty(Ship[][] ships) {
        Arrays.stream(ships).forEach(row -> {
            Arrays.fill(row, NullShipObject.instance());
        });
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
