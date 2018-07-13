package app.game.api.game;

import java.util.UUID;

class GameIdGenerator {

    String generate() {
        return UUID.randomUUID().toString();
    }

}
