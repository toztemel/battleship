package app.game.service;

import java.util.UUID;

public class IDGenerator {

    private static IDGenerator instance = new IDGenerator();

    private IDGenerator() {
    }

    public static IDGenerator getInstance() {
        return instance;
    }

    public String generate() {
        return UUID.randomUUID().toString();
    }

}
