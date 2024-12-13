package view;

import controller.UserController;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import main.Main;

public class RegisterPage {
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 Label titleLbl = new Label();
		 Label emailLbl = new Label();
		 Label usnLbl = new Label();
		 Label passwordLbl = new Label();
		 Label roleLbl = new Label();
		 
		 
		 TextField emailField = new TextField();
		 TextField usnField = new TextField();
		 PasswordField pwField = new PasswordField();
		 		 
		 ComboBox roleBox = new ComboBox();
		 roleBox.getItems().addAll("Event Organizer", "Vendor", "Guest");
		 
		 Button registerBtn = new Button();
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Register an Account");
		 titleLbl.setFont(titleFont);
		 
		 emailLbl.setText("Email: ");
		 emailLbl.setFont(inputFont);
		 
		 usnLbl.setText("Username: ");
		 usnLbl.setFont(inputFont);
		 
		 passwordLbl.setText("Password: ");
		 passwordLbl.setFont(inputFont);
		 
		 roleLbl.setText("Role: ");
		 roleLbl.setFont(inputFont);
	     roleBox.setPromptText("Select a role");
		 
		 Label errorLbl = new Label();
	     errorLbl.setText("");
	     errorLbl.setVisible(false);
		 errorLbl.setStyle("-fx-text-fill: red;");
	     
		 registerBtn.setText("Register Account");
		 registerBtn.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 21));
		 registerBtn.setOnAction(e -> {
			 errorLbl.setText("");
			 String role = (roleBox.getValue() == null) ? "" : roleBox.getValue().toString();
			 
			 UserController.checkRegisterInput(
					 emailField.getText(), 
					 usnField.getText(), 
					 pwField.getText(), 
					 role,
					 errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(emailLbl, 0, 0);
		 input.add(emailField, 1, 0);
		 input.add(usnLbl, 0, 1);
		 input.add(usnField, 1, 1);
		 input.add(passwordLbl, 0, 2);
		 input.add(pwField, 1, 2);
		 input.add(roleLbl, 0, 3);
		 input.add(roleBox, 1, 3);
		 
		 
//		 login section
		 Label loginLbl = new Label();
		 Button loginBtn = new Button();
		 
		 loginLbl.setText("Already have an account?");
		 loginBtn.setText("Log-In");
		 loginBtn.setOnAction(e -> {
			 Main.switchScene(LoginPage.getScene());
		 });
		 
		 HBox login = new HBox();
		 login.setAlignment(Pos.CENTER);
		 login.getChildren().addAll(loginLbl, loginBtn);
		 
		 layout.getChildren().addAll(titleLbl, input, errorLbl, registerBtn, login);

		 return new Scene(layout, 1600, 900); 
	}
}
