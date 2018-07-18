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

    Battlefield createTestInstance() {
        return new Battlefield()
                .setConf(conf)
                .build();
    }

    public Battlefield createInstance() {
        return new Battlefield()
                .setConf(conf)
                .randomly()
                .build();
    }

    public BattlefieldFactory configure(BattlefieldConf c) {
        conf = c;
        return this;
    }
}
