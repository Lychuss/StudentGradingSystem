package model;

import javafx.beans.property.SimpleStringProperty;

public class GeneralAverage {

	private SimpleStringProperty grades;
	private SimpleStringProperty units;
	private SimpleStringProperty id;
	
	public GeneralAverage (String grades, String units, String id) {
		this.grades = new SimpleStringProperty(grades);
		this.units = new SimpleStringProperty(units);
		this.id = new SimpleStringProperty(id);
	}
	
	public String getGrades() {
		return grades.get();
	}
	
	public String getUnits() {
		return units.get();
	}
	
	public String getId() {
		return id.get();
	}
}
