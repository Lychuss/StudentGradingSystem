package model;
import DatabaseMySQL.DatabaseConnection;

public class User {

	private String username;
	private String password;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static boolean getUsers(String username, String password) {
		return DatabaseConnection.loginUser(username, password);
	}
}
