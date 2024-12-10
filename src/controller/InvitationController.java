package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Invitation;
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
	
	public static void createInvitation(String eventID, String userID) {
		String query = "INSERT INTO Invitations\n"
				+ "(invid, eventid, userid, invstatus, invrole) VALUES (" + 
				"?, ?, ?, ?, ?)";
		
		PreparedStatement ps;

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, generateInvID());
			ps.setString(2, eventID);
            ps.setString(3, userID);
            ps.setString(4, "Pending");
            ps.setString(5, UserController.getRoleByID(userID));
            ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Main.displayAlert("ERROR", "Failed to create invitation for user: " + UserController.getNameById(userID));
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
	
	public static ArrayList<Invitation> getInvitationsByUserID(String userID){
		ArrayList<Invitation> invitations = new ArrayList<>();
		String query = "SELECT * FROM invitations\n"
				+ "WHERE UserID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, userID);
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
	
	public static ArrayList<Invitation> getPendingInvsByUserID(String userID){
		ArrayList<Invitation> invitations = getInvitationsByUserID(userID);
	    invitations.removeIf(invitation -> invitation.getStatus().equals("Accepted"));

		return invitations;
	};
	
	public static ArrayList<String> getInvitedUsersByEventID(String eventID){
		ArrayList<String> vendorIDs = new ArrayList<>();
		String query = "SELECT UserID FROM invitations "
				+ "WHERE EventID = ?";
		PreparedStatement ps;
		
	    try {
	    	
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, eventID);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            vendorIDs.add(rs.getString("userID"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

		return vendorIDs;
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
