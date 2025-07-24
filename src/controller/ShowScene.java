package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShowScene {

	private Stage stage;
	private Scene scene;
	private Parent  root;
	
	public void showUser(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Student.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public void showUser(MouseEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Student.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	
	public void showTest(BorderPane mainBorder) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Test.fxml"));
		Parent centerContent = loader.load();
		mainBorder.setCenter(centerContent);
	}
	
	
}
