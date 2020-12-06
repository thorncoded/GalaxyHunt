package model;

public class Score {
	int currentScore;
	int scoreX;
	int scoreY;
	public Score(int scoreX, int scoreY, int currentScore) {
		this.scoreX = scoreX;
		this.scoreY = scoreY;
		this.currentScore = currentScore;
	}
	
	public void increaseScore(int points) {
		this.currentScore = currentScore + points;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getScoreX() {
		return scoreX;
	}

	public void setScoreX(int scoreX) {
		this.scoreX = scoreX;
	}

	public int getScoreY() {
		return scoreY;
	}

	public void setScoreY(int scoreY) {
		this.scoreY = scoreY;
	}
}
