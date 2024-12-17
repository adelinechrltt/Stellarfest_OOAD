package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import model.Event;
import model.Guest;
import model.Invitation;
import util.Connect;

public class GuestController {
	
	public static ArrayList<Guest> getAllGuests(){
		// method untuk mendapatkan semua guest dalam database
		ArrayList<Guest> Guests = Guest.getAllGuests();
        return Guests;
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		// method bagi seorang guest untuk mendapatkan daftar event yang akan dihadirinya
		// flow adalah sbg berikut:
		
		// 1. mendapatkan semua invitation yang ditujukan kepada email guest tertentu
		ArrayList<Event> events = new ArrayList<>();
		ArrayList<Invitation> invites = InvitationController.getInvitations(email);
		
		// 2. mengecek semua invitation, apakah guest sudah accept invitationnya atau belum
		for(Invitation inv : invites) {
			 if(inv.getStatus().equals("Accepted")) {
				 // 3. apabila guest sudah accept invitationnya, maka event ybs akan dimasukkan ke dalam
				 // arraylist of events yang akan ditampilkan di view
				 Event ev = EventController.viewEventDetails(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
	
	// method acceptInvtation ini tidak dimasukkan ke model Vendor / Guest karena berhubungan dengan DB table invitation
	// sehingga kami prefer agar controller vendor / guest langsung chaining saja ke controller invitation
	public static void acceptInvitation(String invID, Label errorLbl) {
		Invitation.acceptInvitation(invID, errorLbl);
	}
}
