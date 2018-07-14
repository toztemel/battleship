package app.game.util;

import java.util.Arrays;

public class Printer {

    public static void print2DArray(Object[][] frame) {
        System.out.println();
        for (int i = 0; i < frame.length; i++) {
            System.out.println(Arrays.deepToString(frame[i]));
        }
        System.out.println();
    }

}
