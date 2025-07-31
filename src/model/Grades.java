package model;

import java.util.ArrayList;

import DatabaseMySQL.DatabaseConnection;

public class Grades {

	private double grades, units;
	private int id, userId;
	
	public Grades(double grades, double units, int id, int userId) {
		this.grades = grades;
		this.units = units;
		this.id = id;
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}
	
	public int getUser() {
		return userId;
	}
	
	public double getGrade() {
		return grades;
	}
	
	public double getUnits() {
		return units;
	}
	
	public static boolean inputGrades(double grades, double units, int id) {
		return DatabaseConnection.inputGrades(grades, units, id);
	}
	
	public static ArrayList<Grades> getGrades(){
		return DatabaseConnection.grades();
	}
	
	public static boolean removeGrade(int id) {
		return DatabaseConnection.removeGrades(id);
	}
	
	public static double getTotalGrade() {
		return DatabaseConnection.getTotalGrades();
	}
	
	public static double getTotalUnit() {
		return DatabaseConnection.getTotalUnits();
	}
	
	public static boolean clearGrades(int id) {
		return DatabaseConnection.clearGrades(id);
	}
}
