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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Scores;
import model.ScoresTableView;

public class TestController {
	
	@FXML
	private TextField score;
	@FXML
	private TextField total;
	@FXML
	private TableView<ScoresTableView> scoreTableView;
	@FXML
	private TableColumn<ScoresTableView, String> scoreColumn;
	@FXML
	private TableColumn<ScoresTableView, String> totalColumn;
	@FXML
	private TableColumn<ScoresTableView, Void> removeColumn;
	@FXML
	private Button addButton;
	
	private int id = 0;
	
	public void initialize() {
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		removeColumn.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Remove");
		{	
			btn.setOnAction(event -> {
				ScoresTableView data = getTableView().getItems().get(getIndex());
				if(DatabaseConnection.removeData(getIndex())) {
					 getTableView().getItems().remove(data);
				}
				
				
			});
		}
		
		@Override
		protected void updateItem(Void item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setGraphic(null);
			} else {
				setGraphic(btn); 
			}
		}
	});
}
	
	public void add(ActionEvent e) {
	try {
		id++;
		Scores values = new Scores(Integer.parseInt(score.getText()), Integer.parseInt(total.getText()), id);
		values.add();
		loadTable();
	} catch (NumberFormatException error){
		 Alert alert = new Alert(Alert.AlertType.ERROR);
		 alert.setTitle("INVALID INPUT");
		 alert.setHeaderText(null);
		 alert.setContentText("Must be a number!");
		 alert.showAndWait();
	 }
	}
	
	public void loadTable() {
		if(Scores.scores.isEmpty()) {
			System.out.println("No value");
		}
		
		Scores.scores.clear();
		DatabaseConnection.getScores();
		
		ObservableList<ScoresTableView> scores = FXCollections.observableArrayList();

		for(Scores i : Scores.scores) {
			ScoresTableView list = new ScoresTableView(Integer.toString(i.getScore()), Integer.toString(i.getTotal()));
			scores.add(list);
		}
		scoreTableView.setItems(scores);
	}
}
