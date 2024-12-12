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
		String invitationID = generateInvID();
		try {			
			User user = VendorController.getVendorByEmail(email);
			if(user==null) user = GuestController.getGuestByEmail(email);
			if(user==null) System.out.println("a");
			
			boolean sentFlag = Invitation.sendInvitation(invitationID, eventID, email, user.getRole(), user.getUserID());
			if(sentFlag) {
				Main.displayAlert("INFO", "All invitations sent! Returning to Events List page.");
				Main.switchScene(ViewEventsList.getScene());
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Invitation getInvitationByInvID(String invID) {
		Invitation inv = Invitation.getInvitationByInvId(invID);
		return inv;
	}
	
	public static ArrayList<Invitation> getInvitationsByEmail(String email){
		ArrayList<Invitation> invites = Invitation.getInvitationsByEmail(email);
		return invites;
	};
	
	public static ArrayList<Invitation> getPendingInvsByEmail(String email){
		ArrayList<Invitation> invitations = getInvitationsByEmail(email);
	    invitations.removeIf(invitation -> invitation.getStatus().equals("Accepted"));

		return invitations;
	};
	
	public static ArrayList<String> getInvitedUsersByEventID(String eventID){
		ArrayList<String> emails = Invitation.getInvitedUsersByEventID(eventID);
		return emails;
	}
	
	public static ArrayList<String> getAttendingVendorsByEventID(String eventID){
		ArrayList<String> emails = Invitation.getAttendingVendorsByEventID(eventID);
		return emails;
	}
	
	public static ArrayList<String> getAttendingGuestsByEventID(String eventID){
		ArrayList<String> emails = Invitation.getAttendingGuestsByEventID(eventID);
		return emails;
	}
	
	public static void acceptInvitation(String invID, Label errorLbl) {
		Invitation.acceptInvitation(invID, errorLbl);
	}
}
