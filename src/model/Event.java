package model;

import java.sql.Date;

public class Event {

	private String EventID;
	private String name;
	private Date date;
	private String location;
	private String description;
	private String OrganizerID;

	public Event(String eventID, String name, Date date, String location, String description, String organizerID) {
		super();
		EventID = eventID;
		this.name = name;
		this.date = date;
		this.location = location;
		this.description = description;
		OrganizerID = organizerID;
	}

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganizerID() {
		return OrganizerID;
	}

	public void setOrganizerID(String organizerID) {
		OrganizerID = organizerID;
	}

}
