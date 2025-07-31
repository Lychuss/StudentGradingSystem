package controller;
import model.User;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	
	public void login(ActionEvent e) throws IOException {
		String userName = usernameField.getText();
		String passWord = passwordField.getText();
		
		if(User.getUsers(userName, passWord)) {
			Alert success = new Alert(Alert.AlertType.INFORMATION);
			success.setHeaderText(null);
			success.setTitle("Login");
			success.setContentText("Login Successfully!");
			success.show();
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
