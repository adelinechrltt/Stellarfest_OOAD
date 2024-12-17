package view;

import controller.UserController;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import main.Main;
import util.RoutingHelper;

public class LoginPage {
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 Label titleLbl = new Label();
		 Label emailLbl = new Label();
		 Label passwordLbl = new Label();
		 
		 Label errorLbl = new Label();
		 
		 TextField emailField = new TextField();
		 
		 PasswordField pwField = new PasswordField();
		 
		 Button loginBtn = new Button();
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Login");
		 titleLbl.setFont(titleFont);
		 
		 emailLbl.setText("Email: ");
		 emailLbl.setFont(inputFont);
		 
		 passwordLbl.setText("Password: ");
		 passwordLbl.setFont(inputFont);
		 
		 errorLbl.setText("");
	     errorLbl.setVisible(false);
		 errorLbl.setStyle("-fx-text-fill: red;");
		 
		 loginBtn.setText("Login");
		 loginBtn.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 21));
		 loginBtn.setOnAction(e -> {
			 errorLbl.setText("");
			 UserController.login(emailField.getText(), pwField.getText(), errorLbl);
		 });
		 		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(emailLbl, 0, 0);
		 input.add(emailField, 1, 0);
		 input.add(passwordLbl, 0, 1);
		 input.add(pwField, 1, 1);
		 
		 Label registerLbl = new Label();
		 Button registerBtn = new Button();
		 
		 registerLbl.setText("No account yet?");
		 registerBtn.setText("Register");
		 registerBtn.setOnAction(e -> {
			 RoutingHelper.showRegisterPage();
		 });
		 
		 HBox register = new HBox();
		 register.setAlignment(Pos.CENTER);
		 register.getChildren().addAll(registerLbl, registerBtn);
		 
		 layout.getChildren().addAll(titleLbl, input, errorLbl, loginBtn, register);

		 return new Scene(layout, 800, 500);	
	}
}
