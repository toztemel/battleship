package app.game.api.firing;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Game {

    private GameStatus status;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum GameStatus {
        player_turn, won
    }
}
