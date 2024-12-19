package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import util.Connect;

public class EventOrganizer extends User {

	private static Connect db = Connect.getInstance();
	
	private ArrayList<Event> eventsCreated;

	public EventOrganizer(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Event Organizer";
		this.eventsCreated = new ArrayList<>();
	}
	
	public void updateEvents(ArrayList<Event> events) {
		this.eventsCreated = events;
	}
	
	public ArrayList<Event> getEventsCreated(){
		return eventsCreated;
	}
	
	// query methods
	public static ArrayList<Event> viewOrganizedEvents(String email){
		// method untuk mendapatkan daftar event yang telah dibuat oleh seorang event organizer
		ArrayList<Event> events = new ArrayList<>();
	    String query = "SELECT * FROM events e JOIN users u ON e.OrganizerID = u.UserID\n"
	    		+ "WHERE u.Email = ?";
	    
	    try (Connection conn = db.getConnection();
	    		
	         PreparedStatement ps = conn.prepareStatement(query)) {
	         ps.setString(1, email);
	         ResultSet rs = ps.executeQuery();

	         while (rs.next()) {
	        	 Event event = new Event(null, null, null, null, null, null);
	             event.setEventID(rs.getString("eventId"));
	             event.setName(rs.getString("name"));
	             event.setDate(rs.getDate("date"));
	             event.setLocation(rs.getString("location"));
	             event.setDescription(rs.getString("description"));
	             events.add(event);
	         }
	         
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }

		return events;
	}

	public static ArrayList<String> getVendorsByEventId(String eventID) {
		// method untuk mendapatkan vendor yang menghadiri suatu event 
		// berdasarkan data yang tersimpan dalam tabel invitations
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT Email FROM Users u JOIN Invitations i ON i.UserID = u.UserID "
				+ "WHERE i.EventID = ? AND i.InvStatus = 'Accepted' AND i.InvRole = 'Vendor'";
		PreparedStatement ps;
		
	    try {
	    	
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, eventID);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
                emails.add(rs.getString("email"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		return emails;
	}

	public static ArrayList<String> getGuestsByEventId(String eventID) {
		// method untuk mendapatkan guest yang menghadiri suatu event 
		// berdasarkan data yang tersimpan dalam tabel invitations
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT Email FROM Users u JOIN Invitations i ON i.UserID = u.UserID\n"
				+ "WHERE i.EventID = ? AND i.InvStatus = 'Accepted' AND i.InvRole = 'Guest'";
		PreparedStatement ps;
		
	    try {
	    	
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, eventID);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            emails.add(rs.getString("email"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		return emails;
	}
}
