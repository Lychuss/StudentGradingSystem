package model;

import javafx.beans.property.SimpleStringProperty;

public class ScoresTableView {

	private SimpleStringProperty score;
	private SimpleStringProperty total;
	
	public ScoresTableView(String score, String total){
		this.score = new SimpleStringProperty(score);
		this.total = new SimpleStringProperty(total);
	}

	public String getScore() {
		return score.get();
	}
	
	public String getTotal() {
		return total.get();
	}

}
