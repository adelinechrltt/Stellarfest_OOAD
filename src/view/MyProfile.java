package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.User;
import util.LoginSession;
import util.RoutingHelper;

public class MyProfile {
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		 User currentUser = login.getLoggedInUser();
		
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(currentUser.getRole());
		 
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
		 email.setText(currentUser.getEmail());
		 email.setFont(inputFont);
		 
		 updateEmailBtn.setText("\u270F");
		 updateEmailBtn.setOnAction(e -> {
			 RoutingHelper.showUpdateEmailPage();
		 });
		 
		 usnLbl.setText("Username: ");
		 usnLbl.setFont(inputFont);
		 usn.setText(currentUser.getName());
		 usn.setFont(inputFont);
		 
		 updateUsnBtn.setText("\u270F");
		 updateUsnBtn.setOnAction(e -> {
			 RoutingHelper.showUpdateUsernamePage();
		 });
		 
		 changePwBtn.setText("Change Password");
		 changePwBtn.setOnAction(e -> {
			 RoutingHelper.showUpdatePasswordPage();
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
