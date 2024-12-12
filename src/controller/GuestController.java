package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Event;
import model.Guest;
import model.Invitation;
import util.Connect;

public class GuestController {
	
	public static Guest getGuestByEmail(String email) {
		Guest guest = Guest.getGuestByEmail(email);
		return guest;
	}
	
	public static ArrayList<Guest> getAllGuests(){
		ArrayList<Guest> Guests = Guest.getAllGuests();
        return Guests;
	}
	
	public static ArrayList<Guest> getUninvitedGuests(String eventID){
		ArrayList<Guest> Guests = getAllGuests();
		ArrayList<String> invitedGuestIDs = InvitationController.getInvitedUsersByEventID(eventID);
		
	    Guests.removeIf(Guest -> invitedGuestIDs.contains(Guest.getUserID()));
		
		if(Guests.isEmpty()) Guests = null;
		return Guests;
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		ArrayList<Event> events = new ArrayList<>();
		ArrayList<Invitation> invites = InvitationController.getInvitationsByEmail(email);
		for(Invitation inv : invites) {
			 if(inv.getStatus().equals("Accepted")) {
				 Event ev = EventController.viewEventDetails(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
}
