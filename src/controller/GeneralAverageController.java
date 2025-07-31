package controller;

import DatabaseMySQL.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GeneralAverage;
import model.Grades;
import model.User;

public class GeneralAverageController {
	
	@FXML
	private TextField gradesField, unitsField;
	@FXML
	private TextArea gradesArea, unitsArea, gwaArea;
	@FXML
	private Button addButton, computeButton;
	@FXML
	private TableColumn<GeneralAverage, String> gradeColumn, unitColumn;
	@FXML
	private TableColumn<GeneralAverage, Void> removeColumn;
	@FXML
	private TableView<GeneralAverage> generalCalculator;
	
	private Alert alert = new Alert(Alert.AlertType.WARNING);
	@FXML
	public void initialize() {
		loadTable();
		
		gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grades"));
		unitColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
		removeColumn.setCellFactory(col -> new TableCell<>() {
			private Button remove = new Button("Remove");
			{
				remove.setOnAction(e -> {
					GeneralAverage grades = getTableView().getItems().get(getIndex());
					if(Grades.removeGrade(Integer.parseInt(grades.getId()))) {
						getTableView().getItems().remove(getIndex());
						loadTable();
					}
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				
				if(empty) {
					setGraphic(null);
				} else {
					setGraphic(remove);
				}
			}
		});
	}
	
	public void add(ActionEvent e) {
		if(Grades.inputGrades(Double.parseDouble(gradesField.getText()), Double.parseDouble (unitsField.getText()), User.getId())) {
			System.out.println("Added Successfully");
		} else {
			alert.setHeaderText(null);
			alert.setTitle("Invalid Input");
			alert.setContentText("Cannot be added, check your input and retry again");
			alert.showAndWait();
		}
		
		loadTable();
		gwaArea.setText(null);
		gradesArea.setText(null);
		unitsArea.setText(null);
	}
	
	public void loadTable() {
		ObservableList<GeneralAverage> list = FXCollections.observableArrayList();
		
		for(Grades i : Grades.getGrades()) {
			if(i.getUser() == User.getId()) {
				GeneralAverage avg = new GeneralAverage(Double.toString(i.getGrade()), Double.toString(i.getUnits()), Integer.toString(i.getId()));
				list.add(avg);
			}
		}
		generalCalculator.setItems(list);
	}
	
	public void compute(ActionEvent e) {
		gradesArea.appendText(Double.toString(Math.round(Grades.getTotalGrade() * (double) 100) / (double) 100));
		gradesArea.setEditable(false);
		unitsArea.appendText(Double.toString(Math.round(Grades.getTotalUnit() * (double) 100) / (double) 100));
		gradesArea.setEditable(false);
		double average = Grades.getTotalGrade() / Grades.getTotalUnit();
		gwaArea.appendText(Double.toString(Math.round(average * 100.0) / 100.0));
		generalCalculator.getItems().clear();
		Grades.clearGrades(User.getId());
		loadTable();
	}
}
