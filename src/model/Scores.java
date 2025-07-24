package model;
import java.util.ArrayList;

import DatabaseMySQL.DatabaseConnection;

public class Scores {

	public static ArrayList<Scores> scores = new ArrayList<>();
	
	private int score;
	private int total;
	private int id;
	
	public Scores(int score, int total, int id1){
		this.score = score;
		this.total = total;
		this.id = id1;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setId(int id1) {
		id = id1;
	}
	
	public void add() {
		DatabaseConnection.addScoresTotal(score, total, id);
	}
}
