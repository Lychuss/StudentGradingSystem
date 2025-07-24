package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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
	private BorderPane mainBorderPane;
	
	public void showTest(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showTest(mainBorderPane);
	}
	
	public void showUser(MouseEvent e) throws IOException {
		ShowScene scene = new ShowScene();
		scene.showUser(e);
	}

}
