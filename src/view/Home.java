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
import main.Main;
import util.LoginSession;

public class Home {
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());

		 Label greetingLbl = new Label();
		 greetingLbl.setText("Hello, " + login.getLoggedInUser().getName() + "!");
		 
		 layout.getChildren().addAll(navbar, greetingLbl);
		 return new Scene(layout, 1200, 600);	
	}
}
