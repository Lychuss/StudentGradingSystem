package DatabaseMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import model.Scores;
import model.ScoresTableView;

public class DatabaseConnection {
	
	static Connection con;
	static ResultSet rs;
	
	public static void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentgradingsystem", "root", "Tokitoclaude0907@");
			System.out.println("hello");
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static void storeUsers(String username, String password) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSER INTO users VALUE (?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static boolean loginUser(String username, String password) {
		boolean check = false;
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM users");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String sqlUsername = rs.getString("usernames");
				String sqlPassword = rs.getString("passwords");
				
				if(sqlUsername.equals(username) && sqlPassword.equals(password)) {
					System.out.println("Login Successfully!");
					check = true;
				}
			}
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		System.out.println(check);
		return check;
	}
	
	public static void getScores() {
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM test");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String scores = rs.getString(1);
				String totals = rs.getString(2);
				String index = rs.getString(3);
				Scores score = new Scores(Integer.parseInt(scores), Integer.parseInt(totals), Integer.parseInt(index));
				Scores.scores.add(score);
			}
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static void addScoresTotal(int score, int total, int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO test VALUE (?, ?, ?)");
			stmt.setInt(1, score);
			stmt.setInt(2, total);
			stmt.setInt(3, id);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static boolean removeData(int id) {
		 int remove = id + 1;
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM test WHERE id = ?");
			stmt.setInt(1, remove);
			stmt.executeUpdate();
	        Statement resetIdStmt = con.createStatement();
	        resetIdStmt.execute("SET @new_id = 0");
	        resetIdStmt.execute("UPDATE test SET id = (@new_id := @new_id + 1)");
			return true;
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}
}
