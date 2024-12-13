package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import model.Event;
import model.Guest;
import model.Invitation;
import util.Connect;

public class GuestController {
	
	public static ArrayList<Guest> getAllGuests(){
		ArrayList<Guest> Guests = Guest.getAllGuests();
        return Guests;
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		ArrayList<Event> events = new ArrayList<>();
		ArrayList<Invitation> invites = InvitationController.getInvitations(email);
		for(Invitation inv : invites) {
			 if(inv.getStatus().equals("Accepted")) {
				 Event ev = EventController.viewEventDetails(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
	
	// method acceptInvtation ini tidak dimasukkan ke model Vendor / Guest karena berhubungan dengan DB table invitation
	// sehingga kami prefer agar controller vendor / guest langsung chaining saja ke controller invitation
	public static void acceptInvitation(String invID, Label errorLbl) {
		Invitation.acceptInvitation(invID, errorLbl);
	}
}
