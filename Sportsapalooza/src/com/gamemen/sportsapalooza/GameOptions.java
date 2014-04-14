package com.gamemen.sportsapalooza;

public class GameOptions {
	private static GameModes gameMode;
	private static TimeLimits timeLimit;
	private static ScoreLimits scoreLimit;
	
	///////////////////////////////////////////////////////////
	// ENUMS
	//////////////////////////////////////////////////////////
	
	public enum GameModes {
		TIME_LIMIT,
		SCORE_LIMIT
	}
	
	public enum TimeLimits {
		ONE_MIN(60, "1:00"),
		TWO_MIN(120, "2:00"),
		FIVE_MIN(300, "5:00");
		
		private float time;
		private String timeStr;
		
		TimeLimits(float time, String timeStr) {
			this.time = time;
			this.timeStr = timeStr;
		}
		
		public float getTime() {
			return time;
		}
		public String getTimeStr() {
			return timeStr;
		}
	}
	
	public enum ScoreLimits {
		ONE(1),
		FIVE(5),
		TEN(10);
		
		private int score;
		
		ScoreLimits(int score) {
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
	}

	//////////////////////////////////////////////////////
	// GETTERS AND SETTERS
	/////////////////////////////////////////////////////
	
	public static GameModes getGameMode() {
		return gameMode;
	}
	public static void setGameMode(GameModes gameMode) {
		GameOptions.gameMode = gameMode;
	}
	
	public static TimeLimits getTimeLimit() {
		return timeLimit;
	}
	public static void setTimeLimit(TimeLimits timeLimit) {
		GameOptions.timeLimit = timeLimit;
	}
	
	public static ScoreLimits getScoreLimit() {
		return scoreLimit;
	}
	public static void setScoreLimit(ScoreLimits scoreLimit) {
		GameOptions.scoreLimit = scoreLimit;
	}
	
}
