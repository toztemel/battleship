package app.game.service.rule;

import app.game.api.dto.game.Rule;

public class GameRuleFactory {

    private static final GameRuleFactory instance = new GameRuleFactory();

    private GameRuleFactory() {
    }

    public static GameRuleFactory getInstance() {
        return instance;
    }

    // TODO parametrize X SHOT
    public GameRule create(Rule r) {
        GameRule rule;
        switch (r) {
            case DESPERATION:
                rule = new Desperation();
                break;
            case SUPER_CHARGE:
                rule = new SuperCharge();
                break;
            case X_SHOT:
                rule = new XShot();
                break;
            case STANDARD:
            default:
                rule = new Standard();
                break;
        }
        return rule;
    }
}
