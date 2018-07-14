package app.game.api.game;

import java.util.UUID;

class IDGenerator {

    String generate() {
        return UUID.randomUUID().toString();
    }

}
