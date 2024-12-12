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
	public static boolean  sendInvitation(String invitationID, String eventID, String email, String role, String userID) {
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
			Main.displayAlert("ERROR", "Failed to create invitation for user: " + userID);
		}
		
		return sentInvite;
	}
	
	public static Invitation getInvitationByInvId(String invID) {
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
	
	public static ArrayList<Invitation> getInvitationsByEmail(String email){
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
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT UserID FROM invitations "
				+ "WHERE EventID = ? AND InvStatus = 'Accepted' AND InvRole = 'Guest'";
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
	
	public static void acceptInvitation(String invID, Label errorLbl) {
		String query = "UPDATE invitations\n"
				+ "SET invStatus = 'Accepted'\n"
				+ "WHERE invID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, invID);
			ps.execute();
			
			if(getInvitationByInvId(invID).getStatus().equals("Accepted")) {
				Main.displayAlert("Info", "Succesfully accepted invitation!");
				Main.switchScene(ViewInvitationsPage.getScene());
			} else {
				errorLbl.setText("ERROR: Failed to accept invitation!");
				errorLbl.setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
