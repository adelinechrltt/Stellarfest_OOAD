package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Invitation;
import model.User;
import util.Connect;
import view.ViewEventsList;
import view.VendorAndGuestViews.ViewInvitationsPage;

public class InvitationController {

	private static Connect db = Connect.getInstance();

	public InvitationController() {
		super();
	}
	
	public static String generateInvID() {
		String InvID = "I";
		for(int i=0; i<7; i++) {
			InvID = InvID + String.valueOf((int)(Math.random()*10));
		}
		return InvID;
	}
	
	public static void sendInvitation(String eventID, String email) {
		String query = "INSERT INTO Invitations"
				+ "(invid, eventid, invstatus, invrole, userId) VALUES (" + 
				"?, ?, ?, ?, ?)";
		
		PreparedStatement ps;
		
		User user = VendorController.getVendorByEmail(email);
		if(user==null) user = GuestController.getGuestByEmail(email);

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, generateInvID());
			ps.setString(2, eventID);
            ps.setString(3, "Pending");
            ps.setString(4, user.getRole());
            ps.setString(5, user.getUserID());
            ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Main.displayAlert("ERROR", "Failed to create invitation for user: " + user.getName());
			return;
		}
		
        Main.displayAlert("Info", "All invitations sent! Returning to My Events page.");
        Main.switchScene(ViewEventsList.getScene());
	}
	
	public static Invitation getInvitationByInvID(String invID) {
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
	};
	
	public static ArrayList<Invitation> getPendingInvsByEmail(String email){
		ArrayList<Invitation> invitations = getInvitationsByEmail(email);
	    invitations.removeIf(invitation -> invitation.getStatus().equals("Accepted"));

		return invitations;
	};
	
	public static ArrayList<String> getInvitedUsersByEventID(String eventID){
		ArrayList<String> emails = new ArrayList<>();
		String query = "SELECT UserID FROM invitations "
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
			
			if(getInvitationByInvID(invID).getStatus().equals("Accepted")) {
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
