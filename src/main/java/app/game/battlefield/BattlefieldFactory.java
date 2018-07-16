package app.game.battlefield;

import app.game.conf.BattlefieldConf;

public class BattlefieldFactory {

    public Battlefield createNew() {
        return new Battlefield().setConf(new BattlefieldConf()).build();
    }
}
