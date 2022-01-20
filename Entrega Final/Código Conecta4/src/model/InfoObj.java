package model;

public class InfoObj { //Objeto dedicado a pasar informacion entre clases
	
	private String hostName, enemyName, winnerID;
	private int actualRound, totalRound, wonHostRounds, wonEnemyRounds, winnerPoints;
	
	public InfoObj(String a, String b, int c, int d, int e, int f, int winP, String winId) {
		this.hostName = a;
		this.enemyName = b;
		this.actualRound = c;
		this.totalRound = d;
		this.wonHostRounds = e;
		this.wonEnemyRounds = f;
		this.winnerID=winId;
		this.winnerPoints=winP;
	}
	
	public InfoObj(String a, String b) {
		this(a, b, -1, -1, -1, -1, -1, "");
	}
	
	public InfoObj(int d) {
		this("", "", 0, d, 0, 0, -1, "");
	}

	public String getHostName() {
		return hostName;
	}

	public String getEnemyName() {
		return enemyName;
	}

	public int getActualRound() {
		return actualRound;
	}

	public int getTotalRound() {
		return totalRound;
	}

	public int getWonHostRounds() {
		return wonHostRounds;
	}

	public int getWonEnemyRounds() {
		return wonEnemyRounds;
	}
	
	public int getWinnerPoints() {
		return winnerPoints;
	}
	
	public void setWinnerPoints(int num) {
		this.winnerPoints=num;
	}
	
	public String getWinnerID() {
		return winnerID;
	}
	
	public void setWinnerID(String s) {
		this.winnerID=s;
	}
}
