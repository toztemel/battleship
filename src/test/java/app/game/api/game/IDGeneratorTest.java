package app.game.api.game;

import org.junit.Test;

public class IDGeneratorTest {

    @Test
    public void generate() {
        IDGenerator IDGenerator = new IDGenerator();
        System.out.println(IDGenerator.generate());
        System.out.println(IDGenerator.generate());
        System.out.println(IDGenerator.generate());
    }
}