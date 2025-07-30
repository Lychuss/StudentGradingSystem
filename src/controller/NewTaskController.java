package controller;
import model.ToDo;
import model.User;
import controller.ToDoController;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTaskController {

	@FXML 
	private TextArea taskArea;
	@FXML
	private DatePicker dueDate;
	@FXML
	private TextField timeField;
	@FXML
	private TextField ampmField;
	@FXML
	private TextField subjectField;
	@FXML
	private ChoiceBox<String> typeChoiceBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<String> levelChoiceBox = new ChoiceBox<>();
	@FXML 
	private Button addButton;
	
	private Alert alert = new Alert(Alert.AlertType.WARNING);
	
	private int pending;
	
	public void add(ActionEvent e) throws IOException {
		pending = Integer.parseInt(ToDo.getPending());
		
		String[] parts = timeField.getText().split(":");
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);

		ToDo task = new ToDo(
				taskArea.getText(), dueDate.getValue(), 
				LocalTime.of(hours, minutes), ampmField.getText(), subjectField.getText(), 
				typeChoiceBox.getValue(), levelChoiceBox.getValue(), 0
			);
		
		if(loadTable(levelChoiceBox.getValue())) {
		pending++;
		ToDo.pendingTask(pending, User.getId());
		task.addTask();
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.close();
	  }
	}
	
	@FXML
	public void initialize() {
		typeChoiceBox.getItems().addAll("Quiz", "Midterm Exam", "Exam", "Project", "Long Quiz", "Groupings", "Assignment", "Task", "Review");
		levelChoiceBox.getItems().addAll("To Do", "Need", "Immediate", "Crucial");
	}
	
	public boolean loadTable(String level) {
		ObservableList<ToDo> list = FXCollections.observableArrayList(ToDo.getItems(User.getId()));
		
		for(ToDo i : list) {
		if(i.getLevel().equalsIgnoreCase(level)) {
			switch(i.getLevel()){
				case "Need":
					alert.setTitle("INVALID OUTPUT");
					alert.setHeaderText(null);
					alert.setContentText("You have unfinished task in Need!");
					alert.showAndWait();
					return false;
				case "Immediate":
					alert.setTitle("INVALID OUTPUT");
					alert.setHeaderText(null);
					alert.setContentText("You have unfinished task in Immediate!");
					alert.showAndWait();
					return false;
				case "Crucial":
					alert.setTitle("INVALID OUTPUT");
					alert.setHeaderText(null);
					alert.setContentText("You have unfinished task in Crucial!");
					alert.showAndWait();
					return false;
				default:
			}
		  }
		}
		
		return true;
	}
}
