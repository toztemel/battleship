package app.game.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BoardStatus {

    @JsonProperty("user_id")
    private String userId;
    private String[] board;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }
}
