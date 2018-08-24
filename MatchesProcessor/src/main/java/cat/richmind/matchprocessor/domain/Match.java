package cat.richmind.matchprocessor.domain;

import java.io.Serializable;

import cat.richmind.matchprocessor.Utils;

public class Match implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String matchID;
	private String playerName;
	private int gameResult;
	
	public Match() {}
	public Match(String matchId, String playerName, int gameResult) {
		this.matchID = matchId;
		this.playerName = playerName;
		this.gameResult = gameResult;
	}
	
	public String getMatchID() {
		return matchID;
	}
	
	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getGameResult() {
		return gameResult;
	}
	
	public void setGameResult(int gameResult) {
		this.gameResult = gameResult;
	}
	
	@Override
	public String toString() {
		return "Match [matchID=" + matchID + ", playerName=" + playerName + ", gameResult=" + (gameResult == Utils.Constants.WIN.valueOf() ? "WIN" : "LOSS") + "]";
	}
}