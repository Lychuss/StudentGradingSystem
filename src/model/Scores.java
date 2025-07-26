package model;
import java.util.ArrayList;

import DatabaseMySQL.DatabaseConnection;

public class Scores {

	public static ArrayList<Scores> scores = new ArrayList<>();
	public static ArrayList<String> str = new ArrayList<>();
	public static ArrayList<Double> percents = new ArrayList<>();
	
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
	
	public static int totalScore() {
		int score = 0;
		for(Scores i : scores) {
			int scores = i.getScore();
			score += scores;
		}
		return score;
	}
	
	public static int inTotal() {
		int total = 0;
		for(Scores i : scores) {
			int totals = i.getTotal();
			total += totals;
		}
		return total;
	}
	
	public static double totalPercent() {
		double totalPercent = (double) totalScore() / inTotal();
		return totalPercent;
	}
	
	public static double computedPercent(int percent) {
		double compute = totalPercent() * percent;
		return compute;
	}
	
	public static double lackPercent() {
		double percent = 0;
		for(Double i : percents) {
			percent += i;
		}
		double percentage = 100 - percent;
		return percentage;
	}
	
	public static double overallPercent() {
		double percent = 0;
		for(Double i : percents) {
			percent += i;
		}
		return percent;
	}
}
