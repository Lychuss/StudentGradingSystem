package model;
import java.util.ArrayList;
import DatabaseMySQL.DatabaseConnection;
import javafx.scene.control.Label;

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
		double totalPercent = Math.round((double) totalScore() / inTotal() * 100.0) / 100.0;
		return totalPercent;
	}
	
	public static double computedPercent(int percent) {
		double compute = Math.round(totalPercent() * percent * 100.0) / 100.0;
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
	
	public static double transmutation() {
		double initial = Math.round(overallPercent() * 100) / 100;
		for(Transmutation i : Transmutation.transmutations) {
			if(initial >= i.getMin() && initial <= i.getMax()) {
				return i.getGrades();
			} 
		}
		
		return -1;
	}
	
	public static double gwa() {
		double gwa = transmutation();
		System.out.println(gwa);
		if(gwa >= 98 && gwa == 100) {
			return 1.00;
		} else if(gwa >= 95 && gwa <= 97) {
			return 1.25;
		} else if(gwa >= 92 && gwa <= 94) {
			return 1.50;
		} else if(gwa >= 89 && gwa <= 91) {
			return 1.75;
		} else if(gwa >= 86 && gwa <= 88) {
			return 2.00;
		} else if(gwa >= 83 && gwa <= 85) {
			return 2.25;
		} else if(gwa >= 80 && gwa <= 82) {
			return 2.50;
		} else if(gwa >= 77 && gwa <= 79) {
			return 2.75;
		} else if(gwa >= 75 && gwa <= 76) {
			return 3.00;
		} else if(gwa >= 70 && gwa <= 74){
			return 4.00;
		} else {
			return 5.00;
		}
	}
	
	public static void failedPass(Label label) {
		double value = gwa();
		if(value < 4.0) {
			label.setText("PASSED");
			label.setStyle("-fx-text-fill: green;");
		} else if (value == 4.0) {
			label.setText("INC");
			label.setStyle("-fx-text-fill: blue;");
		} else {
			label.setText("FAILED");
			label.setStyle("-fx-text-fill: red;");
		}
	}
}
