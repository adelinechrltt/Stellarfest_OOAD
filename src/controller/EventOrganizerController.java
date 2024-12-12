package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Event;
import model.EventOrganizer;
import util.Connect;
import view.ViewEventsList;

public class EventOrganizerController {
		
	public EventOrganizerController() {
		super();
	}
	
	public static ArrayList<Event> viewOrganizedEvents(String email){
		ArrayList<Event> events = EventOrganizer.viewOrganizedEvents(email);
		return events;
	}
	
	public static Event viewOrganizedEventDetails(String eventID){
		model.Event ev = null;
		try {
			ev = EventController.viewEventDetails(eventID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
	
	public static boolean checkCreateEventInput(String name, LocalDate date, String location, String description, String organizerID, Label errorLbl) {
		if(name.isEmpty() || date == null || location.isEmpty() || description.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return false;
		}
		
		LocalDate today = LocalDate.now();
		if(!date.isAfter(today)) {
			errorLbl.setText("ERROR: Event must be scheduled for the future!");
			errorLbl.setVisible(true);
			return false;
		}
		
		if(location.length()<5) {
			errorLbl.setText("ERROR: Event location must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return false;
		}
		
		if(description.length()>200) {
			errorLbl.setText("ERROR: Event location must not be over 200 characters long!");
			errorLbl.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	public static void createEvent(String name, LocalDate date, String location, String description, String organizerID, Label errorLbl) {
		// validation
		boolean isValid = checkCreateEventInput(name, date, location, description, organizerID, errorLbl);
		if(!isValid) return;
		EventController.createEvent(name, date, location, description, organizerID);
	}
	
	public static void editEventName(String eventID, String name, Label errorLbl) {
		if(name.isEmpty()) {
			errorLbl.setText("ERROR: Name cannot be empty!");
			errorLbl.setVisible(true);
			return;
		}
		
		EventController.updateEventName(eventID, name);
		for(Event event : Main.currentUser.getEventsCreated()) {
			if(event.getEventID().equals(eventID)) event.setName(name);
		}
		
		Main.displayAlert("Update success!", "Event name succesfully updated.");
		Main.switchScene(ViewEventsList.getScene());
	}
	
	public static void deleteEvent(String eventID) {
		if(!eventID.isEmpty()) {
			EventController.deleteEvent(eventID);
			
			for(Event event : Main.currentUser.getEventsCreated()) {
				if(event.getEventID().equals(eventID)) event = null;
				break;
			}
			
			Main.displayAlert("Delete success!", "Event succesfully deleted.");
			Main.switchScene(ViewEventsList.getScene());
		}
	}
	
	public static void inviteToEvent(String eventID, String email) {
		InvitationController.sendInvitation(eventID, email);
	}
	
//	karena 2 method ini tidak ada di activity diagram maka saya asumsikan
//	bahwa 2 method ini berupa method get
	public static void getGuests() {};
	public static void getVendors() {};
	
}
