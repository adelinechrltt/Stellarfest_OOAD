package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.Main;
import model.Event;
import util.Connect;
import view.ViewEventsList;

public class EventController {

	private static Connect db = Connect.getInstance();
	
	public EventController() {
		super();
	}
	
	public static String generateEventID() {
		String EvID = "E";
		for(int i=0; i<7; i++) {
			EvID = EvID + String.valueOf((int)(Math.random()*10));
		}
		return EvID;
	}
	
	public static void createEvent(String name, LocalDate date, String location, String description, String organizerID) {		
		String query = "INSERT INTO Events\n"
				+ "(eventid, name, date, location, description, organizerid) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps;

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, generateEventID());
			ps.setString(2, name);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, location);
            ps.setString(5, description);
            ps.setString(6, organizerID);
            ps.execute();
            
            Main.switchScene(ViewEventsList.getScene());
            Main.displayAlert("Event creation succesful!", "Succesfully created a new event!");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static model.Event viewEventDetails(String eventID){
		model.Event ev = null;
	    String query = "SELECT * FROM Events "
	    		+ "WHERE eventid = ?";
	    PreparedStatement ps;
	    
	    try {
	          ps = db.getConnection().prepareStatement(query);
	          ps.setString(1, eventID);
	          ResultSet rs = ps.executeQuery();
	          
	          if (rs.next()) {
	        	  String name = rs.getString("name");
	        	  Date date = rs.getDate("date");
	        	  String location = rs.getString("location");
	        	  String description = rs.getString("description");
	        	  String organizerID = rs.getString("organizerid");
	        	  	        	  
	        	  ev = new model.Event(eventID, name, date, location, description, organizerID);
	        }
	       
	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return ev;
	}
	
	public static Event getEventByiD(String evID) {
		Event ev = null;
		String query = "SELECT * FROM Events "
				+ "WHERE eventID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, evID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ev = new Event(rs.getString("eventID"), rs.getString("name"), rs.getDate("date"), rs.getString("location"), rs.getString("description"), rs.getString("organizerID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ev;
	}
	
	public static void updateEventName(String eventID, String name){
		String query = "UPDATE Events "
				+ "SET name = ? "
				+ "WHERE eventid = ?";

		PreparedStatement ps;

		try {
		    ps = db.getConnection().prepareStatement(query);
		    ps.setString(1, name); 
		    ps.setString(2, eventID);
		    ps.execute();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public static void deleteEvent(String eventID) {
		String query = "DELETE FROM Events WHERE eventid = ?";

		PreparedStatement ps;

		try {
		    ps = db.getConnection().prepareStatement(query);
		    ps.setString(1, eventID);  
		    ps.execute();
		} catch (SQLException e) {
		    e.printStackTrace();
		}

	}
}
