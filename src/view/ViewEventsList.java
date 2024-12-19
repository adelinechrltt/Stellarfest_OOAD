package view;

import java.sql.Date;
import java.util.ArrayList;

import controller.EventController;
import controller.EventOrganizerController;
import controller.GuestController;
import controller.InvitationController;
import controller.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Event;
import model.Invitation;
import util.LoginSession;
import util.RoutingHelper;

public class ViewEventsList {
	
	public static String tempID; 
	private static int clickCount = 0;
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label titleLbl = new Label();
		 titleLbl.setText("My Events");
		 titleLbl.setFont(titleFont);
		 		 
		 layout.getChildren().addAll(navbar, titleLbl);

		 ArrayList<model.Event> events = new ArrayList<>();
		 if(login.getLoggedInUser().getRole().equals("Event Organizer")) {
			 events = EventOrganizerController.viewOrganizedEvents(login.getLoggedInUser().getEmail());
		 } else {
			 if(login.getLoggedInUser().getRole().equals("Vendor")) events = VendorController.viewAcceptedEvents(login.getLoggedInUser().getEmail());
			 else if(login.getLoggedInUser().getRole().equals("Guest")) events = GuestController.viewAcceptedEvents(login.getLoggedInUser().getEmail());
		 }
		 
		 if(events==null || events.isEmpty()) {
			 Label nullDisplay1 = new Label();
			 nullDisplay1.setText("No events!");
			 layout.getChildren().add(nullDisplay1);
			 
			 Label nullDisplay2 = new Label();
			 nullDisplay2.setText("Create an event now.");
			 if(login.getLoggedInUser().getRole().equals("Event Organizer")) layout.getChildren().add(nullDisplay2);
		 } else {			 
			 Label subtitleLbl = new Label();
			 subtitleLbl.setText("Double-click on an entry for more actions.");
			 titleLbl.setFont(inputFont);

			 ObservableList<model.Event> evs = FXCollections.observableArrayList(events);
			 
			 TableView<model.Event> viewMyEvents = new TableView<>();
		     
			 TableColumn<model.Event, String> idCol = new TableColumn<>("Event ID");
		     idCol.setCellValueFactory(new PropertyValueFactory<>("eventID"));
		     
		     TableColumn<model.Event, String> nameCol = new TableColumn<>("Name");
		     nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		     
		     TableColumn<model.Event, Date> dateCol = new TableColumn<>("Date (YYYY-MM-DD)");
		     dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		     
		     TableColumn<model.Event, String> locCol = new TableColumn<>("Location");
		     locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
			 
		     TableColumn<model.Event, String> descCol = new TableColumn<>("Description");
		     descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		     
		     viewMyEvents.getColumns().addAll(idCol, nameCol, dateCol, locCol, descCol);
		     viewMyEvents.setItems(evs);
		     viewMyEvents.setOnMouseClicked(e -> {
		    	 try {
			    	 TableSelectionModel<model.Event> tsModel = viewMyEvents.getSelectionModel();
			    	 tsModel.setSelectionMode(SelectionMode.SINGLE);
			    	 model.Event ev = tsModel.getSelectedItem();
			    	 tempID = ev.getEventID();
			    	 
			    	 clickCount++;
			    	 if(clickCount == 2) {
			    		 RoutingHelper.showEventDetailsPage(tempID);
			    		 clickCount = 0;
			    	 } 
		    	 } catch (Exception error) {
		    		 error.printStackTrace();
		    	 }
		     });
		     
		     layout.getChildren().addAll(subtitleLbl, viewMyEvents);
		 }
	     
		 return new Scene(layout, 1200, 600);	
	}
}