package app.game.service.cache;

import app.game.api.dto.game.Rule;
import app.game.api.dto.status.GameStatus;

public class Game {

    private String gameId;
    private GameStatus.Status gameStatus;
    private String gameOwner;
    private Rule gameRule;

    private String opponentId;
    private String opponentProtocol;
    private String opponentName;
    private String[][] opponentBoard;
    private int opponentShots=1;

    private String userId;
    private String userName;
    private String userProtocol;
    private int userShots=1;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public GameStatus.Status getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus.Status status) {
        this.gameStatus = status;
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

    public Rule getGameRule() {
        return gameRule;
    }

    public void setGameRule(Rule rule) {
        this.gameRule = rule;
    }

    public int getOpponentShots() {
        return opponentShots;
    }

    public void setOpponentShots(int opponentShots) {
        this.opponentShots = opponentShots;
    }

    public int getUserShots() {
        return userShots;
    }

    public void setUserShots(int userShots) {
        this.userShots = userShots;
    }

    public void incrementOpponentShots(long killCount) {
        opponentShots += killCount;
    }
}
