package view.AdminViews;

import java.util.ArrayList;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import util.LoginSession;
import util.RoutingHelper;
import view.NavBar;
import view.ViewEventDetails;

public class ViewAllEventsPage {
	
	public static LoginSession login = LoginSession.getInstance();

	public static String tempID; 
	private static int clickCount = 0;
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label titleLbl = new Label();
		 titleLbl.setText("All Events");
		 titleLbl.setFont(titleFont);
		 		 
		 layout.getChildren().addAll(navbar, titleLbl);

		 ArrayList<model.Event> events = new ArrayList<>();
		 events = AdminController.viewAllEvents();
		 
		 if(events==null || events.isEmpty()) {
			 Label nullDisplay = new Label();
			 nullDisplay.setText("No events!");
			 layout.getChildren().add(nullDisplay);
		 } else {			 
			 Label subtitleLbl = new Label();
			 subtitleLbl.setText("Double-click on an entry for more actions.");
			 titleLbl.setFont(inputFont);

			 ObservableList<model.Event> evs = FXCollections.observableArrayList(events);
			 ListView<CheckBox> eventsView = new ListView<>();
			 ObservableList<CheckBox> checkBoxList = FXCollections.observableArrayList();

			 for (model.Event event : evs) {
			     CheckBox checkBox = new CheckBox("ID: " + event.getEventID() + " | " + event.getName());
			     checkBox.setUserData(event);
			     checkBoxList.add(checkBox);
			 }

			 eventsView.setItems(checkBoxList);

			 eventsView.setOnMouseClicked(e -> {
				    CheckBox selectedCheckBox = eventsView.getSelectionModel().getSelectedItem();
				    try {
				    	model.Event selectedEvent = (model.Event) selectedCheckBox.getUserData(); 
					    if (selectedEvent != null) {
					        if (e.getClickCount() == 2) {
					            try {
					                String tempID = selectedEvent.getEventID();
					                RoutingHelper.showEventDetailsPage(tempID);
					            } catch (Exception error) {
					                error.printStackTrace();
					            }
					        }
					    }
				    } catch (Exception error) {
				    	error.printStackTrace();
				    }
			 });
		     
			 Button deleteBtn = new Button();
			 deleteBtn.setText("Delete Events");
			 deleteBtn.setOnAction(event -> {
				 ArrayList<model.Event> selectedEvents = new ArrayList<>();
				 for (CheckBox checkBox : checkBoxList) {
					 if (checkBox.isSelected()) {
				        selectedEvents.add((model.Event) checkBox.getUserData());
					 }
				 }
				 
				 if (selectedEvents.isEmpty()) {
					 Main.displayAlert("Error", "ERROR: You must select at least one event to delete!");
				 } else {
					 for (model.Event ev : selectedEvents) {
						 evs.remove(ev);
						 AdminController.deleteEvent(ev.getEventID());
					 }
				 }
			 });
		     
		     layout.getChildren().addAll(subtitleLbl, eventsView, deleteBtn);
		 }
	     
		 return new Scene(layout, 1200, 600);	
	}
}
