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
import util.RoutingHelper;
import view.ViewEventsList;
import view.VendorAndGuestViews.ViewInvitationsPage;

public class InvitationController {

	private static Connect db = Connect.getInstance();

	public InvitationController() {
		super();
	}
	
	public static String generateInvID() {
		// method untuk generate invitation ID tanpa regex
		// format: I123456
		String InvID = "I";
		for(int i=0; i<7; i++) {
			InvID = InvID + String.valueOf((int)(Math.random()*10));
		}
		return InvID;
	}
	
	public static void sendInvitation(String eventID, String email) {
		// method untuk membuat invitation baru
		String invitationID = generateInvID();
		try {			
			User user = User.getUserByEmail(email);			
			boolean sentFlag = Invitation.sendInvitation(invitationID, eventID, email, user.getRole(), user.getUserID());
			if(sentFlag) {
				Main.displayAlert("INFO", "All invitations sent! Returning to Events List page.");
				RoutingHelper.showEventsListPage();
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void acceptInvitation(String invID, Label errorLbl) {
		// method untuk menerima suatu invitation
		
		// flag untuk cek apakah berhasil accept invitation
		boolean accepted = Invitation.acceptInvitation(invID, errorLbl);
		if(accepted) {
			Main.displayAlert("Info", "Succesfully accepted invitation!");
			RoutingHelper.showInvitationsListPage();
		} else {
			errorLbl.setText("ERROR: Failed to accept invitation!");
			errorLbl.setVisible(true);
		}
	}
	
	public static Invitation getInvitationByInvID(String invID) {
		// method untuk mendapatkan data detail dari suatu invitation berdasarkan IDnya
		Invitation inv = Invitation.getInvitationByInvId(invID);
		return inv;
	}
	
	public static ArrayList<Invitation> getInvitations(String email){
		// method untuk mendapatkan semua invitation yang ditujukan pada email tertentu
		ArrayList<Invitation> invites = Invitation.getInvitations(email);
		return invites;
	};
	
	public static ArrayList<Invitation> getPendingInvsByEmail(String email){
		// method untuk mendapatkan semua invitation yang masih pending dan ditujukan pada email tertentu
		ArrayList<Invitation> invitations = getInvitations(email);
		
		// menghapus invitation dari arrayList apabila status invitation tersebut sudah accepted
	    invitations.removeIf(invitation -> invitation.getStatus().equals("Accepted"));

		return invitations;
	};
	
	public static ArrayList<String> getInvitedUsersByEventID(String eventID){
		// method untuk mendapatkan semua user yang diundang ke dalam suatu event tertentu berdasarkan database invitation
		ArrayList<String> emails = Invitation.getInvitedUsersByEventID(eventID);
		return emails;
	}
}
