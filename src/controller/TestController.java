package controller;

import DatabaseMySQL.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML
	private Button refreshButton;
	@FXML
	private TextArea totalScore;
	@FXML
	private TextArea computedPercentage;
	@FXML
	private TextArea grade;
	@FXML
	private TextField percentage;
	@FXML
	private Button computeButton;
	@FXML
	private PieChart myPieChart;
	@FXML
	private RadioButton writtenWorks;
	@FXML
	private RadioButton performanceTask;
	@FXML
	private RadioButton exam;
	@FXML
	private ToggleGroup percentGroup;
	
	private int id = 0;
	
	private ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList();
	
	public void initialize() {
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		removeColumn.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Remove");
		{	
			btn.setOnAction(event -> {
				ScoresTableView data = getTableView().getItems().get(getIndex());
				if(DatabaseConnection.removeData(getIndex())) {
					id--;
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
	
	public void initialize1() {
		
	}
	
	public void refresh(ActionEvent e) {
		DatabaseConnection.refreshData("TRUNCATE TABLE test");
		loadTable();
		id = 0;
		totalScore.setText(null);
		computedPercentage.setText(null);
		percentage.setText(null);
		grade.setText(null);
		Scores.percents.clear();
		Scores.str.clear();
		pieChart.clear();
	}
	
	public void add(ActionEvent e) {
	try {
		id++;
		Scores values = new Scores(Integer.parseInt(score.getText()), Integer.parseInt(total.getText()), id);
		values.add();
		loadTable();
		totalScore.setText("  "+Integer.toString(Scores.totalScore()) + '/' + Integer.toString(Scores.inTotal()));
	} catch (NumberFormatException n){
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
	
	public void compute(ActionEvent e) {
		computedPercentage.setText("   "+ Double.toString(Scores.computedPercent(Integer.parseInt(percentage.getText()))) + "%");
		RadioButton button = (RadioButton) percentGroup.getSelectedToggle();
		String selectedValue = button.getText();
		
		Scores.str.add(selectedValue);
		Scores.percents.add((double)(Scores.computedPercent(Integer.parseInt(percentage.getText()))));
		
		PieChart.Data pie = new PieChart.Data(selectedValue, (int)(Scores.computedPercent(Integer.parseInt(percentage.getText()))));
		pieChart.add(pie);
		System.out.println((int)(Scores.computedPercent(Integer.parseInt(percentage.getText()))));
		
		if(Scores.str.size() == 3) {
			PieChart.Data pie1 = new PieChart.Data("Percent Lacking", (int) Scores.lackPercent());
			pieChart.add(pie1);
			myPieChart.setData(pieChart);
			grade.appendText(Double.toString(Scores.overallPercent()) + "%");
			grade.setEditable(false);
		}
	}
}
