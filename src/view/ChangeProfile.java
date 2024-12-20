package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import util.LoginSession;
import util.RoutingHelper;

public class ChangeProfile {
	
	// untuk memenuhi kriteria dalam word di mana pengubahan detail profil user dapat dilakukan secara terpisah
	// tetapi juga sambil mempertahankan logika validasi bahwa suatu data yang ingin diubah harus berbeda dengan data awalnya,
	// make approach kami adalah untuk saling memisah scene-scene untuk update masing-masing data, sehingga user memang harus 
	// melakukan pengubahan detail profil secara terpisah
	
	public static LoginSession login = LoginSession.getInstance();
	
	private static Label errorLbl = new Label();
	private static Label titleLbl = new Label();
	private static Button updateBtn = new Button();
	private static Button backBtn = new Button();
	
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
		 
		 backBtn.setText("Back to Profile");
		 backBtn.setOnAction(e -> {
			 RoutingHelper.showProfilePage();
		 });
		 
		 updateBtn.setText("Update E-mail");
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangeEmail(login.getLoggedInUser().getEmail(), emailField.getText(), errorLbl);
		 });
		 
		 HBox buttons = new HBox();
		 buttons.setAlignment(Pos.CENTER);
		 buttons.getChildren().addAll(updateBtn, backBtn);
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(emailLbl, 0, 0);
		 input.add(emailField, 1, 0);
		 
		 layout.getChildren().addAll(titleLbl, input, buttons, errorLbl);

		 return new Scene(layout, 1200, 600);	
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
		 
		 backBtn.setText("Back to Profile");
		 backBtn.setOnAction(e -> {
			 RoutingHelper.showProfilePage();
		 });
		 
		 updateBtn.setText("Update Username");
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangeUsn(login.getLoggedInUser().getName(), usnField.getText(), errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(usnLbl, 0, 0);
		 input.add(usnField, 1, 0);
		 
		 HBox buttons = new HBox();
		 buttons.setAlignment(Pos.CENTER);
		 buttons.getChildren().addAll(updateBtn, backBtn);
		 
		 layout.getChildren().addAll(titleLbl, input, buttons, errorLbl);

		 return new Scene(layout, 1200, 600);	
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
		 updateBtn.setOnAction(e -> {
			 UserController.checkChangePassword(login.getLoggedInUser().getEmail(), login.getLoggedInUser().getPassword(), oldPwField.getText(), newPwField.getText(), errorLbl);
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(oldPwLbl, 0, 0);
		 input.add(oldPwField, 1, 0);
		 input.add(newPwLbl, 0, 1);
		 input.add(newPwField, 1, 1);
		 
		 HBox buttons = new HBox();
		 buttons.setAlignment(Pos.CENTER);
		 buttons.getChildren().addAll(updateBtn, backBtn);
		 
		 layout.getChildren().addAll(titleLbl, input, buttons, errorLbl);

		 return new Scene(layout, 1200, 600);	
	}
}