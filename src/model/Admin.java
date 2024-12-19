package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.EventController;
import javafx.scene.control.Label;
import util.Connect;

public class Admin extends User {

	private static Connect db = Connect.getInstance();

	public Admin(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		// TODO Auto-generated constructor stub
		this.role = "Admin";
	}

	public static ArrayList<User> getAllUsers(){
		// method untuk mendapatkan semua user dalam DB
		// method ini hanya akan dipanggil oleh seorang admin
		
		ArrayList<User> users = new ArrayList<>();
		
		// asumsi bahwa admin tidak bisa mengakses admin lain
		// sehingga admin tidak bisa delete akunnya sendiri + saling delete akun admin lain
		String query = "SELECT * FROM Users\n "
				+ "WHERE ROLE != 'Admin'";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String ID = rs.getString("UserID");
            	String email = rs.getString("Email");
            	String name = rs.getString("Name");
            	String password = rs.getString("Password");
            	String role = rs.getString("Role");
            	
            	if(role.equals("Event Organizer")) users.add(new EventOrganizer(ID, email, name, password));
            	else if (role.equals("Vendor")) users.add(new Vendor(ID, email, name, password));
            	else if (role.equals("Guest")) users.add(new Guest(ID, email, name, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static void deleteUser(String userID) {
		// method untuk delete user berdasarkan userID nya
		String query = "DELETE FROM Users\n" +
						"WHERE UserID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, userID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Event viewEventDetails(String eventID){
		// method untuk mendapatkan detail-detail dari suatu event berdasarkan eventID nya
		model.Event ev = null;
		try {
			ev = EventController.viewEventDetails(eventID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
	
	public static ArrayList<Event> viewAllEvents(){
		// method untuk return semua events yang ada dalam database
		ArrayList<Event> events = EventController.viewAllEvents();
		return events;
	}
	
	public static void deleteEvent(String eventID) {
		// method untuk menghapus suatu event berdasarkan eventID
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
