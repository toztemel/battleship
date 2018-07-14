package app.game.ship.rotation.random;

import java.util.Random;

public class RandomLayout {

    private final Random random;

    public RandomLayout() {
        random = new Random();
    }

    void randomMapPosition(String[][] map) {

    }

    public int randomRotation() {
        return random.nextInt(4);
    }
}
