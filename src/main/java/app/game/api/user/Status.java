package app.game.api.user;

import app.game.api.firing.Game;

public class Status {

    private Game game;
    private Self self;
    private Oponent oponent;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Oponent getOponent() {
        return oponent;
    }

    public void setOponent(Oponent oponent) {
        this.oponent = oponent;
    }
}
