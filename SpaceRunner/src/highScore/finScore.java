package highScore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class finScore {
	private String date;
	private int score;
	   //DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;  
	   //LocalDateTime now = LocalDateTime.now();  
	public finScore() {
		this("01/01/1999", 0);
	}
	
	public finScore(String date, int score) {
		this.date = date;
		this.score = score;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
