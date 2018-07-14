package app.game.ship.rotation.random;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class RandomLayoutTest {

    private RandomLayout randomLayout;

    @Before
    public void setup() {
        randomLayout = new RandomLayout();
    }

    @Test
    public void randomMapPosition() {

    }

    @Test
    public void defaultRotation() {
        String[][] piece = {
                {"x", "x", "", "", ""},
                {"x", "", "x", "", ""},
                {"x", "x", "", "", ""},
                {"", "", "", "x", ""},
                {"", "", "", "x", ""}
        };

        System.out.println(Arrays.deepToString(piece));

        Collections.rotate(Arrays.asList(piece), 1);
        System.out.println(Arrays.deepToString(piece));

    }

    @Test
    public void randomRotation() {

        int[] results = new int[4];
        for (int i = 0; i < 1000; i++) {
            int rotation = randomLayout.randomRotation();
            results[rotation]++;
        }

        System.out.println("Random rotation probabilities:" + Arrays.toString(results));
    }
}