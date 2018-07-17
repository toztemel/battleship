package app.game.api.dto.status;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GameStatus {

    private Status status;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Status {
        player_turn, won
    }
}
