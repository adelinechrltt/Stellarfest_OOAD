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
		// method untuk generate event ID tanpa regex
		// format: E123456
		String EvID = "E";
		for(int i=0; i<7; i++) {
			EvID = EvID + String.valueOf((int)(Math.random()*10));
		}
		return EvID;
	}
	
	public static void createEvent(String name, LocalDate date, String location, String description, String organizerID) {
		// method untuk membuat event baru
		String eventID = generateEventID();
		// successFlag digunakan untuk cek apakah berhasil create event atau tidak
		boolean successFlag = Event.createEvent(eventID, name, date, location, description, organizerID);
		if(successFlag) {
			RoutingHelper.showEventsListPage();
	        Main.displayAlert("Event creation succesful!", "Succesfully created a new event!");
		} else Main.displayAlert("ERROR", "Failed to create new event.");
	}
	
	public static ArrayList<model.Event> viewAllEvents(){
		// method untuk melihat semua event yang ada dalam database
		ArrayList<model.Event> events = Event.viewAllEvents();
		return events;
	}
	
	public static model.Event viewEventDetails(String eventID){
		// method untuk mendapatkan semua detail yang bersangkutan dari suatu event
		model.Event ev = Event.viewEventDetails(eventID);
	    return ev;
	}
	
	public static void updateEventName(String eventID, String name){
		// method untuk mengubah nama suatu event
		Event.updateEventName(eventID, name);
	}
}
