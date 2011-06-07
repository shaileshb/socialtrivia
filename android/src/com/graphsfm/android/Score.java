package com.graphsfm.android;

public class Score {

	private  int score = 0;

	public  void setScore(int score) {
		score = score;
	}

	public  int getScore() {
		return score;
	}
	
	public void addScore(int time) {
		score = score + 100;
	}
	
	
	public String toString() {
	  return Integer.toString(score);
	}
}
