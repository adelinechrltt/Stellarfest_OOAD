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
	
	private static Connect db = Connect.getInstance();
	
	public static Guest getGuestByEmail(String email) {
		Guest guest = null;
		String query = "SELECT * FROM Users\n"
				+ "WHERE email = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String guestID = rs.getString("userID");
				String name = rs.getString("name");
				String password = rs.getString("password");
				guest = new Guest(guestID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return guest;
	}
	
	public static ArrayList<Guest> getAllGuests(){
		ArrayList<Guest> Guests = new ArrayList<>();
		
        String query = "SELECT * FROM users "
        		+ "WHERE role = 'Guest'";
        PreparedStatement ps;

        try {
        	ps = db.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String GuestID = rs.getString("UserID");
            	String GuestEmail = rs.getString("Email");
            	String GuestName = rs.getString("Name");
            	String GuestPw = rs.getString("Password");
            	Guests.add(new Guest(GuestID, GuestEmail, GuestName, GuestPw));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
				 Event ev = EventController.getEventByID(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
}
