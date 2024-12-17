package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.GuestController;
import controller.VendorController;
import javafx.scene.control.Label;
import main.Main;
import util.Connect;
import view.VendorAndGuestViews.ViewInvitationsPage;

public class Invitation {
	private static Connect db = Connect.getInstance();

	private String InvitationID;
	private String UserID;
	private String EventID;
	private String status;
	private String UserRole;

	public Invitation(String invitationID, String userID, String eventID, String status, String userRole) {
		super();
		InvitationID = invitationID;
		UserID = userID;
		EventID = eventID;
		this.status = status;
		UserRole = userRole;
	}

	public String getInvitationID() {
		return InvitationID;
	}

	public void setInvitationID(String invitationID) {
		InvitationID = invitationID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	
	// query methods
	public static boolean sendInvitation(String invitationID, String eventID, String email, String role, String userID) {
		// method untuk membuat invitation baru berdasarkan input dari user
		boolean sentInvite = false;
		
		String query = "INSERT INTO Invitations"
				+ "(invid, eventid, invstatus, invrole, userId) VALUES (" + 
				"?, ?, ?, ?, ?)";
		
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, invitationID);
			ps.setString(2, eventID);
            ps.setString(3, "Pending");
            ps.setString(4, role);
            ps.setString(5, userID);
            ps.execute();
            sentInvite = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sentInvite;
	}
	
	public static Invitation getInvitationByInvId(String invID) {
		// method untuk mendapatkan suatu invitation berdasarkan IDnya
		Invitation inv = null;
		String query = "Select * FROM invitations\n"
				+ "WHERE InvID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, invID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("InvID");
				String eventID = rs.getString("eventID");
				String uID = rs.getString("userID");
				String status = rs.getString("invStatus");
				String role = rs.getString("invRole");
				
				inv = new Invitation(invID, uID, eventID, status, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inv;
	}
	
	public static ArrayList<Invitation> getInvitations(String email){
		// method untuk memperoleh daftar invitation yang ditujukan pada email tertentu
		ArrayList<Invitation> invitations = new ArrayList<>();
		String query = "SELECT * FROM invitations i JOIN users u ON i.UserID = u.UserID\n"
				+ "WHERE u.Email = ?";
		PreparedStatement ps;
				
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String invID = rs.getString("InvID");
				String eventID = rs.getString("eventID");
				String uID = rs.getString("userID");
				String status = rs.getString("invStatus");
				String role = rs.getString("invRole");
				
				Invitation inv = new Invitation(invID, uID, eventID, status, role);
				invitations.add(inv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitations;
	}

	public static ArrayList<String> getInvitedUsersByEventID(String eventID) {
		// method untuk memperoleh daftar user yang diundang untuk menghadiri suatu event
		// berdasarkan data yang tersimpan dalam tabel invitations
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT Email FROM Users u JOIN Invitations i on u.UserID = i.UserID\n"
				+ "WHERE EventID = ?";
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
	
	public static ArrayList<String> getAttendingVendorsByEventID(String eventID){
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
	
	public static ArrayList<String> getAttendingGuestsByEventID(String eventID){
		// method untuk mendapatkan guest yang menghadiri suatu event 
		// berdasarkan data yang tersimpan dalam tabel invitations
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT Email FROM Users u JOIN Invitations i ON i.UserID = u.UserID\n"
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
	
	public static boolean acceptInvitation(String invID, Label errorLbl) {
		// method bagi guest dan vendor untuk menerima suatu undangan kehadiran di event tertentu
		boolean isAccepted = false;
		String query = "UPDATE invitations\n"
				+ "SET invStatus = 'Accepted'\n"
				+ "WHERE invID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, invID);
			ps.execute();
			
			isAccepted = true;
		} catch (SQLException e) {
			e.printStackTrace();
			isAccepted = false;
		}
		
		return isAccepted;
	}
}
