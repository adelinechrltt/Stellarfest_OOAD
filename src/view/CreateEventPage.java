package view;

import controller.EventOrganizerController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import main.Main;
import model.User;
import util.LoginSession;

public class CreateEventPage {
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		 
		 Label titleLbl = new Label();
		 Label evNameLbl = new Label();
		 Label evDateLbl = new Label();
		 Label evLocLbl = new Label();
		 Label evDescLbl = new Label();
		 
		 TextField evNameField = new TextField();
		 DatePicker evDatePicker = new DatePicker();
		 TextField evLocField = new TextField();
		 TextField evDescField = new TextField();
		 
		 Button createEventBtn = new Button();
		 
		 Label errorLbl = new Label();
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 titleLbl.setText("Create New Event");
		 titleLbl.setFont(titleFont);
		 
		 evNameLbl.setText("Event Name: ");
		 evNameLbl.setFont(inputFont);
		 evDateLbl.setText("Event Date: ");
		 evDateLbl.setFont(inputFont);
		 evLocLbl.setText("Event Location: ");
		 evLocLbl.setFont(inputFont);
		 evDescLbl.setText("Event Description: ");
		 evDescLbl.setFont(inputFont);
		 
		 errorLbl.setText("");
		 errorLbl.setStyle("-fx-text-fill: red;");
		 errorLbl.setVisible(false);
		 
		 createEventBtn.setOnAction(e -> {
			 EventOrganizerController.createEvent(evNameField.getText(), evDatePicker.getValue(), evLocField.getText(), evDescField.getText(), login.getLoggedInUser().getUserID(), errorLbl);
		 });
		 
		 createEventBtn.setText("Create Event");
		 
		 GridPane input = new GridPane();
		 input.setAlignment(Pos.CENTER);
		 input.add(evNameLbl, 0, 0);
		 input.add(evNameField, 1, 0);
		 input.add(evDateLbl, 0, 1);
		 input.add(evDatePicker, 1, 1);
		 input.add(evLocLbl, 0, 2);
		 input.add(evLocField, 1, 2);
		 input.add(evDescLbl, 0, 3);
		 input.add(evDescField, 1, 3);
		 
		 layout.getChildren().addAll(navbar, titleLbl, input, createEventBtn, errorLbl);
		 
		 return new Scene(layout, 1600, 900); 
	}
}
