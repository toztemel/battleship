package app.game.api.game;

import org.junit.Test;

public class GameIdGeneratorTest {

    @Test
    public void generate() {
        GameIdGenerator gameIdGenerator = new GameIdGenerator();
        System.out.println(gameIdGenerator.generate());
        System.out.println(gameIdGenerator.generate());
        System.out.println(gameIdGenerator.generate());
    }
}