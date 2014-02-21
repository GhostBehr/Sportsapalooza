package com.gamemen.sportsapalooza;

public class GameOptions {
	private static boolean sound;
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
		ONE_MIN(1, "1:00"),
		TWO_MIN(2, "2:00"),
		FIVE_MIN(5, "5:00");
		
		private int time;
		private String timeStr;
		
		TimeLimits(int time, String timeStr) {
			this.time = time;
			this.timeStr = timeStr;
		}
		
		public int getTime() {
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
	
	public static boolean getSound() {
		return sound;
	}
	public static void setSound(boolean sound) {
		GameOptions.sound = sound;
	}
	
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
