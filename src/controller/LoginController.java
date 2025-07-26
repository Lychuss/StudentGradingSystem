package controller;
import model.User;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	
	@FXML 
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button exitButton;
	@FXML
	private Button loginButton;
	@FXML
	private Label invalid;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void login(ActionEvent e) throws IOException {
		String userName = usernameField.getText();
		String passWord = passwordField.getText();
		
		if(User.getUsers(userName, passWord)) {
			System.out.print("success");
			ShowScene scene = new ShowScene();
			scene.showUser(e);
		} else {
			invalid.setText("Invalid account, try again.");
		}
	}
	
	public void exit(ActionEvent e) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
	
}
