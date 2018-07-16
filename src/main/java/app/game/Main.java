package app.game;

public class Main {

    public static void main(String[] args) {
        configureLogger();
        startGame();
    }

    private static void startGame() {
        BattleshipGame battleship = new BattleshipGame();
        battleship.start();
    }

    private static void configureLogger() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
    }

}
