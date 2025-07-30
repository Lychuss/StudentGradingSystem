package model;
import DatabaseMySQL.DatabaseConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ToDo {

	private String task;
	private LocalDate dueDate;
	private LocalTime dueTime;
	private String subject;
	private String type;
	private String level;
	private String day;
	private int id;
	
	public ToDo (String task, LocalDate dueDate, LocalTime dueTime, String day, String subject, String type, String level, int id) {
		this.task = task;
		this.dueDate = dueDate;
		this.dueTime = dueTime;
		this.subject = subject;
		this.type = type;
		this.level = level;
		this.day = day;
		this.id = id;
	}
	
	public String getTask() {
		return task;
	}
	
	public String getDay() {
		return day;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public LocalTime getDueTime() {
		return dueTime;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getType() {
		return type;
	}
	
	public String getLevel() {
		return level;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setDueTime(LocalTime dueTime) {
		this.dueTime = dueTime;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void addTask() {
		DatabaseConnection.addTask(task, dueDate, dueTime, subject, type, level, User.getId());
	}
	
	public static ArrayList<ToDo> getItems(int id) {
		return DatabaseConnection.storeList(id);
	}
	
	public static boolean completed(int id) {
		return DatabaseConnection.completeTask(id);
	}
	
	public static boolean completeTask(int complete, int id) {
		return DatabaseConnection.inputCompleted(complete, id);
	}
	
	public static String getComplete() {
		return DatabaseConnection.getComplete();
	}
	
	public static boolean pendingTask(int pending, int id) {
		return DatabaseConnection.inputPending(pending, id);
	}
	
	public static String getPending() {
		return DatabaseConnection.getPending();
	}
	
}

