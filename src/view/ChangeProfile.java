package view;

import controller.UserController;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import main.Main;
import util.LoginSession;

public class ChangeProfile {
	
	// untuk memenuhi kriteria dalam word di mana pengubahan detail profil user dapat dilakukan secara terpisah
	// tetapi juga sambil mempertahankan logika validasi bahwa suatu data yang ingin diubah harus berbeda dengan data awalnya,
	// make approach kami adalah untuk saling memisah scene-scene untuk update masing-masing data, sehingga user memang harus 
	// melakukan pengubahan detail profil secara terpisah
	
	public static LoginSession login = LoginSession.getInstance();
	
	private static Label errorLbl = new Label();
	private static Label titleLbl = new Label();
	private static Button updateBtn = new Button();
	
	public static Scene getUpdateEmailScene() {
		 		 
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 Label emailLbl = new Label();
		 TextField emailField = new TextField();
		 		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Change E-mail");
		 titleLbl.setFont(titleFont);
		 
		 emailLbl.setText("New E-mail: ");
		 emailLbl.setFont(inputFont);
		 emailField.setText(login.getLoggedInUser().getEmail());
		 
		 errorLbl.setText("");
	     errorLbl.setVisible(false);
		 errorLbl.setStyle("-fx-text-fill: red;");
		 
		 updateBtn.setText("Update E-mail");
		 updateBtn.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 21));
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangeEmail(login.getLoggedInUser().getEmail(), emailField.getText(), errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(emailLbl, 0, 0);
		 input.add(emailField, 1, 0);
		 
		 layout.getChildren().addAll(titleLbl, input, updateBtn, errorLbl);

		 return new Scene(layout);	
	}
	
	public static Scene getUpdateUsnScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 Label usnLbl = new Label();
		 TextField usnField = new TextField();
		 		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Change Username");
		 titleLbl.setFont(titleFont);
		 
		 usnLbl.setText("New Username: ");
		 usnLbl.setFont(inputFont);
		 
		 usnField.setText(login.getLoggedInUser().getName());
		 
		 errorLbl.setText("");
	     errorLbl.setVisible(false);
		 errorLbl.setStyle("-fx-text-fill: red;");
		 
		 updateBtn.setText("Update Username");
		 updateBtn.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 21));
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangeUsn(login.getLoggedInUser().getName(), usnField.getText(), errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(usnLbl, 0, 0);
		 input.add(usnField, 1, 0);
		 
		 layout.getChildren().addAll(titleLbl, input, updateBtn, errorLbl);

		 return new Scene(layout);	
	}
	
	public static Scene getUpdatePwScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 Label oldPwLbl = new Label();
		 Label newPwLbl = new Label();
		 PasswordField oldPwField = new PasswordField();
		 PasswordField newPwField = new PasswordField();
		 		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Change Profile");
		 titleLbl.setFont(titleFont);

		 oldPwLbl.setText("Old Password: ");
		 oldPwLbl.setFont(inputFont);

		 newPwLbl.setText("New Password: ");
		 newPwLbl.setFont(inputFont);
		 
		 errorLbl.setText("");
	     errorLbl.setVisible(false);
		 errorLbl.setStyle("-fx-text-fill: red;");
		 
		 updateBtn.setText("Change Password");
		 updateBtn.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 21));
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangePassword(login.getLoggedInUser().getEmail(), login.getLoggedInUser().getPassword(), oldPwField.getText(), newPwField.getText(), errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(oldPwLbl, 0, 0);
		 input.add(oldPwField, 1, 0);
		 input.add(newPwLbl, 0, 1);
		 input.add(newPwField, 1, 1);
		 
		 layout.getChildren().addAll(titleLbl, input, updateBtn, errorLbl);

		 return new Scene(layout, 1200, 600);	
	}
}