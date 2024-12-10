package view.EventOrganizerViews;

import controller.EventOrganizerController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import view.NavBar;
import view.ViewEventDetails;

public class UpdateEventNamePage {
	public static Scene getScene(String eventID, String name) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Update Event Name");
		titleLbl.setFont(titleFont);
		
		Label errorLbl = new Label();
		errorLbl.setStyle("-fx-text-fill: red;");
	    errorLbl.setText("");
	    errorLbl.setVisible(false);
		
		Label nameLbl = new Label();
		nameLbl.setText("Event Name: ");
		
		TextField nameField = new TextField();
		nameField.setText(name);
		
		Button updateName = new Button();
		updateName.setText("Update Event Name");
		updateName.setOnAction(e -> {
			EventOrganizerController.editEventName(eventID, nameField.getText(), errorLbl);
		});
		
		Button backBtn = new Button();
		backBtn.setText("Back");
		backBtn.setOnAction(e -> {
			Main.switchScene(ViewEventDetails.getScene(eventID));
		});
		
		GridPane input = new GridPane();
		input.add(nameLbl, 0, 0);
		input.add(nameField, 1, 0);
		input.setAlignment(Pos.CENTER);
		
		HBox buttons = new HBox();
		buttons.getChildren().addAll(updateName, backBtn);
		buttons.setAlignment(Pos.CENTER);
		
	    layout.getChildren().addAll(navbar, titleLbl, input, buttons, errorLbl);
		return new Scene(layout, 300, 200);
	}
}
