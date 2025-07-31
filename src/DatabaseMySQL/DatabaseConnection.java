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
import model.GeneralAverage;
import model.Grades;
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
			PreparedStatement stmt = con.prepareStatement("INSER INTO users VALUES (?, ?)");
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
			PreparedStatement stmt = con.prepareStatement("INSERT INTO test VALUES (?, ?, ?)");
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
			PreparedStatement stmt = con.prepareStatement("INSERT INTO tasks (`task`, `date due`, `time due`, `subject`, `type`, `level`, `id`) VALUES (?, ?, ?, ?, ?, ?, ?)");
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
	
	public static boolean inputCompleted(int complete, int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE completed SET completed = ? WHERE id = ?");
			stmt.setInt(1, complete);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			return true;
		} catch(SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}
	
	public static String getComplete() {
		String complete = null;
		try {
			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM completed");
			rs = stmnt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(2);
				if(id.equalsIgnoreCase(Integer.toString(User.getId()))) {
					complete = rs.getString(1);
				}
			}
			
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}
		
		return complete;
	}
	
	public static boolean inputPending(int pending, int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE completed SET pending = ? WHERE id = ?");
			stmt.setInt(1, pending);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			return true;
		} catch(SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}
	
	public static String getPending() {
		String pending = null;
		try {
			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM completed");
			rs = stmnt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(2);
				if(id.equalsIgnoreCase(Integer.toString(User.getId()))) {
					pending = rs.getString(3);
				}
			}
			
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}
		
		return pending;
	}
	
	public static boolean inputGrades(double grades, double units, int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO grades (`grades`, `units`, `user`) VALUES (?, ?, ?)");
			stmt.setDouble(1, grades);
			stmt.setDouble(2, units);
			stmt.setInt(3, id);
			stmt.execute();
			stmt.close();
			return true;
		} catch(SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}
	
	public static ArrayList<Grades> grades(){
		ArrayList<Grades> grades = new ArrayList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM grades;");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String grade = rs.getString(1);
				String unit = rs.getString(2);
				String id = rs.getString(3);
				String user = rs.getString(4);
				Grades avg = new Grades(Double.parseDouble(grade), Double.parseDouble(unit), Integer.parseInt(id), Integer.parseInt(user));
				grades.add(avg);
			}
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null ,e);
		}
		return grades;
	}
	
	public static boolean removeGrades(int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM grades WHERE id = ?;");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}
	
	public static double getTotalGrades() {
		double grade = 0;
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM grades;");
			rs = stmt.executeQuery();
			while(rs.next()) {
			 if(rs.getInt(4) == User.getId()) {
				double grades = rs.getDouble(1);
				double units = rs.getDouble(2);
				grade += grades * units;
				System.out.print(grade);
			 }
			}
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		return grade;
	}
	
	public static double getTotalUnits() {
		double unit = 0;
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM grades;");
			rs = stmt.executeQuery();
			while(rs.next()) {
			 if(rs.getInt(4) == User.getId()) {
				double units = rs.getDouble(2);
				unit += units;
			 }
			}
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		return unit;
	}
	
	public static boolean clearGrades(int id) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM grades WHERE user = ?");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}
}
