package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Guest;
import util.Connect;

public class GuestController {
	
	private static Connect db = Connect.getInstance();
	
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
}
