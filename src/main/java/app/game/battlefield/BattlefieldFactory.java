package app.game.battlefield;

import app.game.conf.BattlefieldConf;

public class BattlefieldFactory {

    private static BattlefieldFactory instance = new BattlefieldFactory();

    private BattlefieldConf conf;

    private BattlefieldFactory() {
    }

    public static BattlefieldFactory getInstance() {
        return instance;
    }

    public Battlefield createEmpty() {
        return new Battlefield()
                .setConf(conf)
                .build();
    }

    public Battlefield createRandom() {
        return new Battlefield()
                .setConf(conf)
                .random()
                .build();
    }

    public BattlefieldFactory setConf(BattlefieldConf c) {
        conf = c;
        return this;
    }
}
