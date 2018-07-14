package app.game.ship.rotation;

import app.game.ship.rotation.random.RandomLayout;

class Matrix {

    void rotate(String[][] array) {
        int count = new RandomLayout().randomRotation();
        for (int i = 1; i <= count; i++) {
            rotateClockwise(array);
        }
    }

    private void rotateClockwise(String[][] array) {

    }
}
