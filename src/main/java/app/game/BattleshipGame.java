package app.game;

import app.game.api.BattleshipAPI;

public class BattleshipGame {

    BattleshipGame() {

    }

    void start() {
        BattleshipAPI.getInstance().start();
    }

    void stop() {
        BattleshipAPI.getInstance().stop();
    }

}
