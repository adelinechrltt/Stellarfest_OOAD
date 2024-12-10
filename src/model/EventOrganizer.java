package model;
import java.util.*;

public class EventOrganizer extends User {

	private ArrayList<Event> eventsCreated;

	public EventOrganizer(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Event Organizer";
		this.eventsCreated = new ArrayList<>();
	}
	
	public void updateEvents(ArrayList<Event> events) {
		this.eventsCreated = events;
	}
	
	public ArrayList<Event> getEventsCreated(){
		return eventsCreated;
	}
}
