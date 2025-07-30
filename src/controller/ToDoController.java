package controller;
import model.ToDo;
import model.User;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ToDoController {

	@FXML
	private VBox need;
	@FXML
	private VBox immediate;
	@FXML
	private VBox crucial;
	@FXML
	private Label pendingTask;
	@FXML
	private Label completedTask;
	@FXML
	private Label taskLabelNeed;
	@FXML
	private Label whenLabelNeed;
	@FXML
	private Label subjectLabelNeed;
	@FXML
	private Label levelLabelNeed;
	@FXML 
	private Label taskLabelImmediate;
	@FXML 
	private Label whenLabelImmediate;
	@FXML
	private Label subjectLabelImmediate;
	@FXML 
	private Label levelLabelImmediate;
	@FXML 
	private Label taskLabelCrucial;
	@FXML 
	private Label whenLabelCrucial;
	@FXML
	private Label subjectLabelCrucial;
	@FXML 
	private Label levelLabelCrucial;
	@FXML
	private Button newTaskButton;
	@FXML
	private Button refreshButton;
	@FXML
	private ProgressBar progressBar;
	@FXML 
	private ListView<ToDo> listView;
		
	private boolean showNeed = false;
	private boolean showImmediate= false;
	private boolean showCrucial = false;
	
	private int completeTask;
	
	
	@FXML
	public void initialize() {
		completeTask = Integer.parseInt(ToDo.getComplete());
		completedTask.setText(ToDo.getComplete());
		
		need.setVisible(showNeed);
		immediate.setVisible(showImmediate);
		crucial.setVisible(showCrucial);
		
		loadTable();
		pendingTask.setText(Integer.toString(listView.getItems().size()));

		listView.setCellFactory(param -> new ListCell<ToDo>() {
			private final ImageView plus = new ImageView(getClass().getResource("/view/images/completed.png").toExternalForm());
			private final Button button = new Button();
			private final Label label1 = new Label();
			private final Label label2 = new Label();
			private final Label label3 = new Label();
			private final Label label4 = new Label();
			private final VBox vbox = new VBox(10, label1, label2, label3, label4, button);
			{
				button.setOnAction(e -> {
					ToDo item = getItem();
					if(item != null && ToDo.completed(item.getId())) {
						completeTask++;
						ToDo.completeTask(completeTask, User.getId());
						getListView().getItems().remove(item);
						pendingTask.setText(Integer.toString(listView.getItems().size()));
						completedTask.setText(ToDo.getComplete());
						progressBar.setProgress(Double.parseDouble(ToDo.getComplete()) / Double.parseDouble(ToDo.getPending()));
						
						if(Double.parseDouble(ToDo.getComplete()) / Double.parseDouble(ToDo.getPending()) == 1) {
							ToDo.pendingTask(0, User.getId());
							ToDo.completeTask(0, User.getId());
							progressBar.setProgress(0.0);
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setTitle("All tasks completed!");
							alert.setContentText("You have completed all your tasks!");
							alert.showAndWait(); 
						}
						
						refresh(e);
						
						switch(item.getLevel()) {
							case "Need":
								showNeed = false;
								break;
							case "Immediate":
								showImmediate = false;
								break;
							case "Crucial":
								showCrucial = false;
								break;
							default:
						}
					}
				});
				

				
		        button.setPrefSize(24, 24);
		        button.setMinSize(24, 24);
		        button.setMaxSize(24, 24);

		        plus.setPreserveRatio(true);
		        plus.setFitWidth(16); 
		        plus.setFitHeight(16);

		        button.setGraphic(plus);

		        button.setText(null);

			}
			
			@Override
			protected void updateItem(ToDo item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || item == null) {
					setGraphic(null);
				} else {
					label1.setText("Task: ".toUpperCase() + item.getTask());
					label1.setStyle("-fx-font-weight: bold;");
					label2.setText("Due Date: ".toUpperCase() + item.getDueDate() + " || " + item.getDueTime() + item.getDay());
					label2.setStyle("-fx-font-weight: bold;");
					label3.setText("Type: ".toUpperCase() + item.getType() + " || " + item.getLevel());
					label3.setStyle("-fx-font-weight: bold;");
					label4.setText("Subject: ".toUpperCase() + item.getSubject());
					label4.setStyle("-fx-font-weight: bold;");
					button.setStyle("-fx-background-color: transparent;");
					vbox.setStyle("	-fx-background-color: rgba(228, 251, 130, 0.8);\r\n"
							+ "	-fx-border-radius: 20;\r\n"
							+ "	-fx-background-radius: 20;\r\n"
							+ "	-fx-border-width: 2;"
							+ " -fx-padding: 15;");
					setGraphic(vbox);
				}
			}
		});
	}	
	
	public void loadTable() {
		ObservableList<ToDo> list = FXCollections.observableArrayList(ToDo.getItems(User.getId()));
		listView.setItems(list);
		
		for(ToDo i : list) {
			switch(i.getLevel()){
				case "Need":
					taskLabelNeed.setText("TASK: " + i.getType());
					whenLabelNeed.setText("DUE: " + i.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					subjectLabelNeed.setText("SUBJECT: " + i.getSubject());
					levelLabelNeed.setText("LEVEL: " + i.getLevel());
					need.setVisible(true);
					break;
				case "Immediate":
						taskLabelImmediate.setText("TASK: " + i.getType());
						whenLabelImmediate.setText("DUE: " + i.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						subjectLabelImmediate.setText("SUBJECT: " + i.getSubject());
						levelLabelImmediate.setText("LEVEL: " + i.getLevel());
						immediate.setVisible(true);
					break;
				case "Crucial":
						taskLabelCrucial.setText("TASK: " + i.getType());
						whenLabelCrucial.setText("DUE: " + i.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						subjectLabelCrucial.setText("SUBJECT: " + i.getSubject());
						levelLabelCrucial.setText("LEVEL: " + i.getLevel());
						crucial.setVisible(true);
					break;
				default:
			}
		}
	}
	
	public void addTask(ActionEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showNewTask(e);
	}
	
	public void refresh(ActionEvent e) {
		initialize();
	}
	
	public void resetBar() {
		progressBar.setProgress(0.0);
	}
}
