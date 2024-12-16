package view;

import controller.UserController;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import main.Main;
import util.LoginSession;

public class MyProfile {
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		 
		 Label titleLbl = new Label();
		 Label emailLbl = new Label();
		 Label usnLbl = new Label();
		 
		 Label email = new Label();
		 Label usn = new Label();
		 
		 Button updateEmailBtn = new Button();
		 Button updateUsnBtn = new Button();
		 Button changePwBtn = new Button();
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("My Profile");
		 titleLbl.setFont(titleFont);
		 
		 emailLbl.setText("Email: ");
		 emailLbl.setFont(inputFont);
		 email.setText(login.getLoggedInUser().getEmail());
		 email.setFont(inputFont);
		 
		 updateEmailBtn.setText("\u270F");
		 updateEmailBtn.setOnAction(e -> {
			 Main.switchScene(ChangeProfile.getUpdateEmailScene());
		 });
		 
		 usnLbl.setText("Username: ");
		 usnLbl.setFont(inputFont);
		 usn.setText(login.getLoggedInUser().getName());
		 usn.setFont(inputFont);
		 
		 updateUsnBtn.setText("\u270F");
		 updateUsnBtn.setOnAction(e -> {
			 Main.switchScene(ChangeProfile.getUpdateUsnScene());
		 });
		 
		 changePwBtn.setText("Change Password");
		 changePwBtn.setOnAction(e -> {
			 Main.switchScene(ChangeProfile.getUpdatePwScene());
		 });
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(emailLbl, 0, 0);
		 input.add(email, 1, 0);
		 input.add(updateEmailBtn, 2, 0);
		 input.add(usnLbl, 0, 1);
		 input.add(usn, 1, 1);
		 input.add(updateUsnBtn, 2, 1);
		 
		 layout.getChildren().addAll(navbar, titleLbl, input, changePwBtn);

		 return new Scene(layout, 1200, 600);	
	}
}
