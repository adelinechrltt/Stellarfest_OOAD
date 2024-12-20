package view;

import java.util.ArrayList;

import controller.AdminController;
import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Event;
import model.Guest;
import model.User;
import model.Vendor;
import util.LoginSession;
import util.RoutingHelper;
import view.EventOrganizerViews.InviteGuest;
import view.EventOrganizerViews.InviteVendor;

public class ViewEventDetails {
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene(String eventID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label manipTitle = new Label();
		 Label errorLbl = new Label();
		 
		 manipTitle.setText("Event Details");
		 manipTitle.setFont(titleFont);
		 
		 errorLbl.setStyle("-fx-text-fill: red;");
	     errorLbl.setText("");
	     errorLbl.setVisible(false);
		 
	     GridPane eventDetails = new GridPane();
	     
	     Label evIDLbl = new Label();
	     evIDLbl.setText("Event ID: ");
	     Label evNameLbl = new Label();
	     evNameLbl.setText("Event Name: ");
	     Label evDateLbl = new Label();
	     evDateLbl.setText("Event Date: ");
	     Label evLocLbl = new Label();
	     evLocLbl.setText("Event Location: ");
	     Label evDescLbl = new Label();
	     evDescLbl.setText("Event Description: ");
	     
	     model.Event ev = null;
	     
	     if(login.getLoggedInUser().getRole().equals("Admin")) ev = AdminController.viewEventDetails(eventID);
	     else if (login.getLoggedInUser().getRole().equals("Event Organizer")) ev = EventOrganizerController.viewOrganizedEventDetails(eventID);
	     else ev = Event.viewEventDetails(eventID);

	     try {
	    	 Label evIDVal = new Label();
	    	 evIDVal.setText(eventID);
	    	 Label evNameVal = new Label();
	    	 evNameVal.setText(ev.getName());
	    	 Label evDateVal = new Label();
	    	 evDateVal.setText(ev.getDate().toString());
	    	 Label evLocVal = new Label();
	    	 evLocVal.setText(ev.getLocation());
	    	 Label evDescVal = new Label();
	    	 evDescVal.setText(ev.getDescription());
	    	 
	    	 eventDetails.add(evIDLbl, 0, 0);
	    	 eventDetails.add(evIDVal, 1, 0);
	    	 eventDetails.add(evNameLbl, 0, 1);
	    	 eventDetails.add(evNameVal, 1, 1);
	    	 eventDetails.add(evDateLbl, 0, 2);
	    	 eventDetails.add(evDateVal, 1, 2);
	    	 eventDetails.add(evLocLbl, 0, 3);
	    	 eventDetails.add(evLocVal, 1, 3);
	    	 eventDetails.add(evDescLbl, 0, 4);
	    	 eventDetails.add(evDescVal, 1, 4);
	    	 
	    	 eventDetails.setAlignment(Pos.CENTER);
	    	 
	    	 layout.getChildren().addAll(navbar, manipTitle, eventDetails);
	    	 
	    	 if(login.getLoggedInUser().getRole().equals("Event Organizer") || login.getLoggedInUser().getRole().equals("Admin")) {
		    	 
				 Label vendorsLbl = new Label();
				 vendorsLbl.setText("Attending Vendors: ");
				 
				 layout.getChildren().add(vendorsLbl);
				 
				 try {
					 ObservableList<User> vendorsList = FXCollections.observableArrayList(AdminController.getVendorsByEventId(eventID));
					 
					 if (vendorsList.isEmpty() || vendorsList == null) {
						 Label nullVendorsDisplay = new Label();
						 nullVendorsDisplay.setText("No vendors attending this event!");
						 layout.getChildren().add(nullVendorsDisplay);
					 } else {
						 TableView<User> vendorsView = new TableView<>();
					     
						 TableColumn<User, String> vendorIdCol = new TableColumn<>("Vendor ID");
						 vendorIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

						 TableColumn<User, String> vendorNameCol = new TableColumn<>("Name");
						 vendorNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
						
						 TableColumn<User, String> vendorEmailCol = new TableColumn<>("Email");
						 vendorEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
						
						 vendorsView.getColumns().addAll(vendorIdCol, vendorNameCol, vendorEmailCol);
						 vendorsView.setItems(vendorsList);
						 vendorsView.setPrefHeight(vendorsList.size() * 24 + 28);

						 layout.getChildren().add(vendorsView); 
					 }
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
				 
				 Label guestsLbl = new Label();
				 guestsLbl.setText("Attending Guests: ");
				 
				 layout.getChildren().add(guestsLbl);
				 
				 try {
					 ObservableList<User> guestsList = FXCollections.observableArrayList(AdminController.getGuestsByEventId(eventID));
					 
					 if (guestsList.isEmpty() || guestsList == null) {
						 Label nullGuestsDisplay = new Label();
						 nullGuestsDisplay.setText("No guests attending this event!");
						 layout.getChildren().add(nullGuestsDisplay);
					 } else {
						 TableView<User> guestsView = new TableView<>();
					     
						 TableColumn<User, String> guestIdCol = new TableColumn<>("Guest ID");
						 guestIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

						 TableColumn<User, String> guestNameCol = new TableColumn<>("Name");
						 guestNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
						
						 TableColumn<User, String> guestEmailCol = new TableColumn<>("Email");
						 guestEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
						
						 guestsView.getColumns().addAll(guestIdCol, guestNameCol, guestEmailCol);
						 guestsView.setItems(guestsList);
						 guestsView.setPrefHeight(guestsList.size() * 24 + 28);

					     
						 layout.getChildren().add(guestsView); 
					 }
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
	    	 }
	    	 
	    	 if(login.getLoggedInUser().getRole().equals("Admin")) {
				 Button deleteBtn = new Button();
	    		 deleteBtn.setText("Delete Event");
				 deleteBtn.setOnAction(e -> {
					 AdminController.deleteEvent(eventID);
					 RoutingHelper.showEventsListPage();
				 });
				 layout.getChildren().add(deleteBtn);
	    	 }
	    	 
	    	 if(login.getLoggedInUser().getRole().equals("Event Organizer")) {
		    	 Button updateBtn = new Button(), deleteBtn = new Button(), inviteGuests = new Button(), inviteVendors = new Button();
				 HBox UDBtns = new HBox();
				 UDBtns.setAlignment(Pos.CENTER);
				 HBox inviteBtns = new HBox();
				 inviteBtns.setAlignment(Pos.CENTER);
				 
				 updateBtn.setText("Update Event Name");
				 updateBtn.setOnAction(e -> {
					 RoutingHelper.showUpdateEventNamePage(eventID, evNameVal.getText());
				 });
				 UDBtns.getChildren().add(updateBtn);
				 				 
				 inviteGuests.setText("Invite Guests");
				 inviteGuests.setOnAction(e -> {
					 ArrayList<Guest> guests = EventOrganizerController.checkAddGuestInput(eventID);
					 
					 if(guests == null) Main.displayAlert("ERROR", "No guests left to invite for this event!");
					 else RoutingHelper.showInviteGuestsPage(eventID);
				 });
				 inviteBtns.getChildren().add(inviteGuests);
				 
				 inviteVendors.setText("Invite Vendors");
				 inviteVendors.setOnAction(e -> {
					 ArrayList<Vendor> vendors = EventOrganizerController.checkAddVendorInput(eventID);
					 
					 if (vendors == null) Main.displayAlert("ERROR", "No vendors left to invite for this event!");
					 else RoutingHelper.showInviteVendorsPage(eventID);
				 });
				 
				 inviteBtns.getChildren().add(inviteVendors);
				 
				 layout.getChildren().addAll(UDBtns, inviteBtns); 
	    	 }
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     }
		 		 
		 return new Scene(layout, 1200, 600);
	}
}
