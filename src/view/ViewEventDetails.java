package view;

import java.util.ArrayList;

import controller.EventOrganizerController;
import controller.GuestController;
import controller.VendorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Guest;
import model.Vendor;
import view.EventOrganizerViews.InviteGuest;
import view.EventOrganizerViews.InviteVendor;
import view.EventOrganizerViews.UpdateEventNamePage;

public class ViewEventDetails {
	public static Scene getScene(String eventID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label manipTitle = new Label();
		 Label errorLbl = new Label();
		 
		 manipTitle.setText("Event Details");
		 manipTitle.setFont(titleFont);
		 
		 errorLbl.setStyle("-fx-text-fill: red;");
	     errorLbl.setText("");
	     errorLbl.setVisible(false);
		 
//	     event details
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
	     
	     Label evIDVal = new Label();
	     evIDVal.setText(eventID);
	     Label evNameVal = new Label();
	     Label evDateVal = new Label();
	     Label evLocVal = new Label();
	     Label evDescVal = new Label();
	     EventOrganizerController.viewOrganizedEventDetails(eventID, evNameVal, evDateVal, evLocVal, evDescVal);
	     
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
	     
	     
//		 buttons field
	     if(Main.currentUser.getRole().equals("Event Organizer")) {
	    	 Button updateBtn = new Button(), deleteBtn = new Button(), inviteGuests = new Button(), inviteVendors = new Button();
			 GridPane buttons = new GridPane();
			 
			 updateBtn.setText("Update Event Name");
			 updateBtn.setOnAction(e -> {
				 Main.switchScene(UpdateEventNamePage.getScene(eventID, evNameVal.getText()));
			 });
			 buttons.add(updateBtn, 0, 0);
			 
			 deleteBtn.setText("Delete Event");
			 deleteBtn.setOnAction(e -> {
				 EventOrganizerController.deleteEvent(eventID);
			 });
			 buttons.add(deleteBtn, 1, 0);
			 
			 inviteGuests.setText("Invite Guests");
			 inviteGuests.setOnAction(e -> {
				 ArrayList<Guest> guests = GuestController.getUninvitedGuests(eventID);
				 
				 if(guests == null) Main.displayAlert("ERROR", "No guests left to invite for this event!");
				 else Main.switchScene(InviteGuest.getScene(eventID));
			 });
			 buttons.add(inviteGuests, 2, 0);
			 
			 inviteVendors.setText("Invite Vendors");
			 inviteVendors.setOnAction(e -> {
				 ArrayList<Vendor> vendors = VendorController.getUninvitedVendors(eventID);
				 
				 if (vendors == null) Main.displayAlert("ERROR", "No vendors left to invite for this event!");
				 else Main.switchScene(InviteVendor.getScene(eventID));
			 });
			 
			 buttons.add(inviteVendors, 3, 0);
			 
			 buttons.setAlignment(Pos.CENTER);
			 layout.getChildren().add(buttons);
	     }
		 		 
		 return new Scene(layout, 300, 200);
	}
}
