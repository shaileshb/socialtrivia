package com.graphsfm.android;

public class Score {

	private static int score = 0;

	public static void setScore(int score) {
		Score.score = score;
	}

	public static int getScore() {
		return score;
	}
	
	public void addScore(int time) {
		score = score + 100;
	}
	
	
	
}
