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
		// method untuk return semua events yang ada dalam database
		ArrayList<Event> events = EventController.viewAllEvents();
		return events;
	}
	
	public static ArrayList<User> getAllUsers(){
		// method untuk return semua user yang ada dalam database
		ArrayList<User> users = Admin.getAllUsers();
		return users;
	}

	public static void deleteUser(String userID) {
		// method untuk delete suatu user
		try {
			Admin.deleteUser(userID);
			Main.displayAlert("Info", "Successfully deleted user: " + userID + "!");
		} catch (Exception e) {
			Main.displayAlert("ERROR", "Failed to delete user: " + userID);
		}
	}
	
	public static void deleteEvent(String eventID) {
		// method untuk delete suaut event
		try {
			// chain method ke eventController
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
		// method untuk mendapatkan semua vendor yang menghadiri suatu event
		ArrayList<String> vendorEmails = InvitationController.getAttendingVendorsByEventID(eventID);
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<User> vendors = new ArrayList<>();
		for (String email : vendorEmails) {
			vendors.add(UserController.getUserByEmail(email));
		}
		return vendors;
	}
	
	public static ArrayList<User> getGuestsByEventId(String eventID){
		// method untuk mendapatkan semua guest yang menghadiri suatu event
		ArrayList<String> guestEmails = InvitationController.getAttendingGuestsByEventID(eventID);
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<User> guests = new ArrayList<>();
		for (String email : guestEmails) {
			guests.add(UserController.getUserByEmail(email));
		}
		return guests;
	}
	
	public static Event viewEventDetails(String eventID){
		// method untuk mendapatkan data detail dari suatu event
		// chain method ke model admin, karena berdasarkan class diagram admin juga memiliki method ini
		Event event = Admin.viewEventDetails(eventID);
		return event;
	}
}
