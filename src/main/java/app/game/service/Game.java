package app.game.service;

import app.game.api.dto.game.Rules;
import app.game.api.dto.status.GameStatus;

public class Game {

    private String gameId;
    private GameStatus.Mode mode;
    private String gameOwner;
    private Rules rules;

    private String opponentId;
    private String opponentProtocol;
    private String opponentName;
    private String[][] opponentBoard;

    private String userId;
    private String userName;
    private String userProtocol;
    private String[][] userBoard;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameStatus.Mode getMode() {
        return mode;
    }

    public void setMode(GameStatus.Mode mode) {
        this.mode = mode;
    }

    public String getGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getOpponentProtocol() {
        return opponentProtocol;
    }

    public void setOpponentProtocol(String opponentProtocol) {
        this.opponentProtocol = opponentProtocol;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String[][] getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(String[][] opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProtocol() {
        return userProtocol;
    }

    public void setUserProtocol(String userProtocol) {
        this.userProtocol = userProtocol;
    }

    public String[][] getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(String[][] userBoard) {
        this.userBoard = userBoard;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }
}
