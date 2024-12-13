package controller;

import java.util.ArrayList;

import main.Main;
import model.Admin;
import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;
import util.Connect;

public class AdminController {
	
	// method getAllEvents() tidak dibuat lagi karena sudah ada method viewAllEvents
	public static ArrayList<Event> viewAllEvents(){
		ArrayList<Event> events = EventController.viewAllEvents();
		return events;
	}
	
	public static ArrayList<User> getAllUsers(){
		ArrayList<User> users = Admin.getAllUsers();
		return users;
	}

	public static void deleteUser(String userID) {
		try {
			Admin.deleteUser(userID);
			Main.displayAlert("Info", "Successfully deleted user: " + userID + "!");
		} catch (Exception e) {
			Main.displayAlert("ERROR", "Failed to delete user: " + userID);
		}
	}
	
	public static void deleteEvent(String eventID) {
		try {
			EventController.deleteEvent(eventID);
			Main.displayAlert("Info", "Successfully deleted event: " + eventID + "!");
		} catch (Exception e) {
			Main.displayAlert("ERROR", "Failed to delete event: " + eventID);
		}
	}
	
	
//	asumsi bahwa transaction merupakan typo untuk ubiquitous language event
// 	sehingga: 
//	getVendorsByTransactionId --> getVendorsByEventId
//	getGuestsByTransactionId --> getGuestsByEventId
	
	public static ArrayList<User> getVendorsByEventId(String eventID){
		ArrayList<String> vendorEmails = InvitationController.getAttendingVendorsByEventID(eventID);
		ArrayList<User> vendors = new ArrayList<>();
		for (String email : vendorEmails) {
			vendors.add(UserController.getUserByEmail(email));
		}
		return vendors;
	}
	
	public static ArrayList<User> getGuestsByEventId(String eventID){
		ArrayList<String> guestEmails = InvitationController.getAttendingGuestsByEventID(eventID);
		ArrayList<User> guests = new ArrayList<>();
		for (String email : guestEmails) {
			guests.add(UserController.getUserByEmail(email));
		}
		return guests;
	}
	
	public static Event viewEventDetails(String eventID){
		Event event = Admin.viewEventDetails(eventID);
		return event;
	}
}
