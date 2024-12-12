package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import util.Connect;

public class Event {

	private static Connect db = Connect.getInstance();

	private String EventID;
	private String name;
	private Date date;
	private String location;
	private String description;
	private String OrganizerID;

	public Event(String eventID, String name, Date date, String location, String description, String organizerID) {
		super();
		EventID = eventID;
		this.name = name;
		this.date = date;
		this.location = location;
		this.description = description;
		OrganizerID = organizerID;
	}

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganizerID() {
		return OrganizerID;
	}

	public void setOrganizerID(String organizerID) {
		OrganizerID = organizerID;
	}

	
	// query methods
	public static boolean createEvent(String eventID, String name, LocalDate date, String location, String description, String organizerID) {
		boolean succesfulCreate = false;
		
		String query = "INSERT INTO Events\n"
				+ "(eventid, name, date, location, description, organizerID) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps;

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, eventID);
			ps.setString(2, name);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, location);
            ps.setString(5, description);
            ps.setString(6, organizerID);
            ps.execute();
            succesfulCreate = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return succesfulCreate;
	}
	
	public static ArrayList<Event> viewAllEvents(){
		ArrayList<model.Event> events = new ArrayList<>();
		String query = "SELECT * FROM Events";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Event ev = null;
				String eventID = rs.getString("eventID");
				String name = rs.getString("name");
	        	Date date = rs.getDate("date");
	        	String location = rs.getString("location");
	        	String description = rs.getString("description");
	        	String id = rs.getString("organizerID");
	        		        	  
	        	ev = new model.Event(eventID, name, date, location, description, id);
	        	events.add(ev);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return events;
	}
	
	public static Event viewEventDetails(String eventID) {
		Event ev = null;
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
	        	  String id = rs.getString("organizerID");
	        	  	        	  
	        	  ev = new model.Event(eventID, name, date, location, description, id);
	        }
	       
	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return ev;
	}
	
	public static void updateEventName(String eventID, String name) {
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
