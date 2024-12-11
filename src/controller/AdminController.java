package controller;

import java.util.ArrayList;

import main.Main;
import model.Event;
import model.User;
import util.Connect;

public class AdminController {
	private static Connect db = Connect.getInstance();
	
	public static ArrayList<Event> viewAllEvents(){
		ArrayList<Event> events = EventController.viewAllEvents();
		return events;
	}
	
	public static ArrayList<User> viewAllUsers(){
		ArrayList<User> users = UserController.viewAllUsers();
		return users;
	}

	public static void deleteUser(String userID) {
		try {
			UserController.deleteUser(userID);
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
}
