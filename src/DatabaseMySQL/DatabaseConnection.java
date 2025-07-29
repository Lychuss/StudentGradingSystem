package DatabaseMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import javafx.scene.control.Alert;
import model.Scores;
import model.ScoresTableView;
import model.ToDo;
import model.User;

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
				String sqlUsername = rs.getString(1);
				String sqlPassword = rs.getString(2);
				String sqlId = rs.getString(3);
				
				if(sqlUsername.equals(username) && sqlPassword.equals(password)) {
					System.out.println("Login Successfully!");
					User.setId(Integer.parseInt(sqlId));
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
		} catch (SQLException m) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error, can't add");
			alert.setContentText("Refresh First");
			alert.showAndWait();
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
	
	public static void refreshData(String str) {
		try {
			PreparedStatement stmt = con.prepareStatement(str);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static void addTask(String task, LocalDate dueDate, LocalTime dueTime, String subject, String type, String level, int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO tasks (`task`, `date due`, `time due`, `subject`, `type`, `level`, `id`) VALUE (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, task);
			stmt.setString(2, dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			stmt.setString(3, dueTime.format(DateTimeFormatter.ofPattern("hh:mm a")));
			stmt.setString(4, subject);
			stmt.setString(5, type);
			stmt.setString(6, level);
			stmt.setString(7, Integer.toString(id));
			stmt.execute();
			System.out.println("added successfully");
			stmt.close();
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public static ArrayList<ToDo> storeList(int id) {
		ArrayList<ToDo> list = new ArrayList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM tasks");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String tasks = rs.getString(1);
				String dueDate = rs.getString(2);
				String dueTime = rs.getString(3);
				String[] parts = dueTime.split(" ");
				String subject = rs.getString(4);
				String type = rs.getString(5);
				String level = rs.getString(6);
				int id2 = Integer.parseInt(rs.getString(7));
				int id1 = Integer.parseInt(rs.getString(8));
				
				if(id2 == id) {
				ToDo task = new ToDo(tasks, LocalDate.parse(dueDate), LocalTime.parse(parts[0]), parts[1], subject, type, level, id1);
				list.add(task);
			   }
			}
			return list;
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return null;
		} 
	}
	
	public static boolean completeTask(int id) {
			System.out.println(id);
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM tasks WHERE taskid = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}
}
