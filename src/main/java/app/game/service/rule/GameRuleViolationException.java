package app.game.service.rule;

public class GameRuleViolationException extends IllegalArgumentException {

    GameRuleViolationException(String s) {
        super(s);
    }
}
