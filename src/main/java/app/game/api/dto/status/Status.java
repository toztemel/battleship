package app.game.api.dto.status;

public class Status {

    private GameStatus game;
    private SelfStatus self;
    private OpponentStatus opponent;

    public GameStatus getGame() {
        return game;
    }

    public void setGame(GameStatus game) {
        this.game = game;
    }

    public SelfStatus getSelf() {
        return self;
    }

    public void setSelf(SelfStatus self) {
        this.self = self;
    }

    public OpponentStatus getOpponent() {
        return opponent;
    }

    public void setOpponent(OpponentStatus opponent) {
        this.opponent = opponent;
    }
}
