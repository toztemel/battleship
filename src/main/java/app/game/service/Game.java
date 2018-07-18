package app.game.service;

import app.game.api.dto.game.Rule;
import app.game.api.dto.status.GameStatus;

public class Game {

    private String gameId;
    private GameStatus.Status status;
    private String gameOwner;
    private Rule rule;

    private String opponentId;
    private String opponentProtocol;
    private String opponentName;
    private String[][] opponentBoard;

    private String userId;
    private String userName;
    private String userProtocol;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameStatus.Status getStatus() {
        return status;
    }

    public void setStatus(GameStatus.Status status) {
        this.status = status;
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

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
