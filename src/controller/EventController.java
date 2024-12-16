package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.Main;
import model.Event;
import model.Guest;
import model.User;
import model.Vendor;
import util.Connect;
import util.RoutingHelper;
import view.ViewEventsList;

public class EventController {
	
	public EventController() {
		super();
	}
	
	public static String generateEventID() {
		String EvID = "E";
		for(int i=0; i<7; i++) {
			EvID = EvID + String.valueOf((int)(Math.random()*10));
		}
		return EvID;
	}
	
	public static void createEvent(String name, LocalDate date, String location, String description, String organizerID) {		
		String eventID = generateEventID();
		boolean successFlag = Event.createEvent(eventID, name, date, location, description, organizerID);
		if(successFlag) {
			RoutingHelper.showEventsListPage();
	        Main.displayAlert("Event creation succesful!", "Succesfully created a new event!");
		} else Main.displayAlert("ERROR", "Failed to create new event.");
	}
	
	public static ArrayList<model.Event> viewAllEvents(){
		ArrayList<model.Event> events = Event.viewAllEvents();
		return events;
	}
	
	public static model.Event viewEventDetails(String eventID){
		model.Event ev = Event.viewEventDetails(eventID);

	    return ev;
	}
	
	public static void updateEventName(String eventID, String name){
		Event.updateEventName(eventID, name);
	}
	
	public static void deleteEvent(String eventID) {
		Event.deleteEvent(eventID);
	}
	
//	asumsi bahwa transaction merupakan typo untuk ubiquitous language event
// 	sehingga: 
//	getVendorsByTransactionId --> getVendorsByEventId
//	getGuestsByTransactionId --> getGuestsByEventId
	
	public static ArrayList<Vendor> getVendorsByEventId(String eventID){
		ArrayList<String> vendorEmails = InvitationController.getAttendingVendorsByEventID(eventID);
		ArrayList<Vendor> vendors = new ArrayList<>();
		for (String email : vendorEmails) {
			vendors.add((Vendor) UserController.getUserByEmail(email));
		}
		return vendors;
	}
	
	public static ArrayList<Guest> getGuestsByEventId(String eventID){
		ArrayList<String> guestEmails = InvitationController.getAttendingGuestsByEventID(eventID);
		ArrayList<Guest> guests = new ArrayList<>();
		for (String email : guestEmails) {
			guests.add((Guest) UserController.getUserByEmail(email));
		}
		return guests;
	}
}
