package model;

public class Level {

	private String task, type, level;
	
	public Level(String task, String type, String level) {
		this.task = task;
		this.type = type;
		this.level = level;
	}
	
	public String getTask() {
		return task;
	}
	
	public String getType() {
		return type;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
}
