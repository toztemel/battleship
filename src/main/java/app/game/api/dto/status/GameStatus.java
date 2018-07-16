package app.game.api.dto.status;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GameStatus {

    private Mode status;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Mode getStatus() {
        return status;
    }

    public void setStatus(Mode status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Mode {
        player_turn, won
    }
}
