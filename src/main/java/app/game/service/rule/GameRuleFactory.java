package app.game.service.rule;

import app.game.api.dto.game.Rule;

import java.util.EnumMap;
import java.util.Map;

public class GameRuleFactory {

    private static final GameRuleFactory instance = new GameRuleFactory();
    private final Map<Rule, GameRule> ruleMap;

    private GameRuleFactory() {
        ruleMap = new EnumMap<>(Rule.class);
        ruleMap.put(Rule.STANDARD, new Standard());
        ruleMap.put(Rule.DESPERATION, new Desperation());
        ruleMap.put(Rule.SUPER_CHARGE, new SuperCharge());
        ruleMap.put(Rule.X_SHOT, new XShot());
    }

    public static GameRuleFactory getInstance() {
        return instance;
    }

    public GameRule get(Rule rule) {
        return ruleMap.get(rule);
    }
}
