package app.game.service.rule;

public class GameRuleViolationException extends IllegalArgumentException {

    public GameRuleViolationException(String s) {
        super(s);
    }
}
