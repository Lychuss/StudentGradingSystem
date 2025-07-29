package controller;
import model.ToDo;
import model.User;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	private Label taskLabelNeed;
	@FXML
	private Label whenLabelNeed;
	@FXML
	private Label levelLabelNeed;
	@FXML 
	private Label taskLabelImmediate;
	@FXML 
	private Label whenLabelImmediate;
	@FXML 
	private Label levelLabelImmediate;
	@FXML 
	private Label taskLabelCrucial;
	@FXML 
	private Label whenLabelCrucial;
	@FXML 
	private Label levelLabelCrucial;
	@FXML
	private Button newTaskButton;
	@FXML
	private Button refreshButton;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private TextArea pendingToDo;
	@FXML
	private TextArea completedToDo;
	@FXML 
	private ListView<ToDo> listView;
	
	@FXML
	public void initialize() {
		loadTable();
		
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
						getListView().getItems().remove(item);
						System.out.println(item.getId());
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
					label3.setText("Type: ".toUpperCase() + item.getType());
					label3.setStyle("-fx-font-weight: bold;");
					label4.setText("Subject: ".toUpperCase() + item.getSubject());
					label4.setStyle("-fx-font-weight: bold;");
					button.setStyle("-fx-background-color: transparent;");
					vbox.setStyle("-fx-background-color: rgba(228, 251, 130, 0.8);");
					setGraphic(vbox);
				}
			}
		});
	}	
	
	public void loadTable() {
		ObservableList<ToDo> list = FXCollections.observableArrayList(ToDo.getItems(User.getId()));
		listView.setItems(list);
	}
	
	public void addTask(ActionEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showNewTask(e);
	}
	
	public void refresh(ActionEvent e) {
		System.out.print("HELLO");
		loadTable();
		initialize();
	}
}
