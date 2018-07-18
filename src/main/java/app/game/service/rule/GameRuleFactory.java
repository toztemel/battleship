package app.game.service.rule;

import app.game.api.dto.game.Rule;

import java.util.EnumMap;
import java.util.Map;

class GameRuleFactory {

    private final Map<Rule, GameRule> ruleMap;

    GameRuleFactory() {
        ruleMap = new EnumMap<>(Rule.class);
        ruleMap.put(Rule.STANDARD, new Standard());
        ruleMap.put(Rule.DESPERATION, new Desperation());
        ruleMap.put(Rule.SUPER_CHARGE, new SuperCharge());
        ruleMap.put(Rule.X_SHOT, new XShot());
    }

    GameRule get(Rule rule) {
        return ruleMap.get(rule);
    }
}
