package app.game.api.dto.firing;

import app.game.api.dto.status.GameStatus;

public class FiringResponse {

    private FiringResults shots;
    private GameStatus game;

    public FiringResults getShots() {
        return shots;
    }

    public void setShots(FiringResults shots) {
        this.shots = shots;
    }

    public GameStatus getGame() {
        return game;
    }

    public void setGame(GameStatus game) {
        this.game = game;
    }

}
