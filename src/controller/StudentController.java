package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudentController {
	

	@FXML
	private ImageView userIcon;
	@FXML
	private ImageView testIcon;
	@FXML
	private ImageView targetIcon;
	@FXML
	private ImageView calculatorIcon;
	@FXML
	private ImageView logoutIcon;
	@FXML
	private Button exitButton;
	@FXML
	private BorderPane mainBorderPane;
	
	public void showTest(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showTest(mainBorderPane);
	}
	
	public void showUser(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showUser(e);
	}

	public void showToDo(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showToDo(mainBorderPane);
	}
	
	public void showGrade(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showGrade(mainBorderPane);
	}
	
	public void exit(ActionEvent e) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
}
