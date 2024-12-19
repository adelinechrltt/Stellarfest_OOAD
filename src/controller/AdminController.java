package controller;

import java.util.ArrayList;

import main.Main;
import model.Admin;
import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;

public class AdminController {
	
	// method getAllEvents() tidak dibuat lagi karena sudah ada method viewAllEvents
	public static ArrayList<Event> viewAllEvents(){
		// method untuk return semua events yang ada dalam database
		ArrayList<Event> events = Admin.viewAllEvents();
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
			Admin.deleteEvent(eventID);
			Main.displayAlert("Info", "Successfully deleted event: " + eventID + "!");
		} catch (Exception e) {
			Main.displayAlert("ERROR", "Failed to delete event: " + eventID);
		}
	}
	
	
//	asumsi bahwa transaction merupakan typo untuk ubiquitous language event
// 	sehingga: 
//	getVendorsByTransactionId --> getVendorsByEventId
//	getGuestsByTransactionId --> getGuestsByEventId
	
	public static ArrayList<Vendor> getVendorsByEventId(String eventID){
		// method untuk mendapatkan vendor-vendor yang akan menghadiri suatu event
		// berdasarkan apakah vendor tersebut sudah menerima invitation untuk hadir
		
		ArrayList<String> vendorEmails = Admin.getVendorsByEventId(eventID);
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<Vendor> vendors = new ArrayList<>();
		for (String email : vendorEmails) {
			vendors.add((Vendor) UserController.getUserByEmail(email));
		}
		return vendors;
	}
	
	public static ArrayList<Guest> getGuestsByEventId(String eventID){
		// method untuk mendapatkan guest-guest yang akan menghadiri suatu event
		// berdasarkan apakah vendor tersebut sudah menerima invitation untuk hadir
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<String> guestEmails = Admin.getGuestsByEventId(eventID);
		ArrayList<Guest> guests = new ArrayList<>();
		for (String email : guestEmails) {
			guests.add((Guest) UserController.getUserByEmail(email));
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
