package model;

import java.lang.Math;
import java.util.ArrayList;

public abstract class User {
	
	protected String UserID;
	protected String email;
	protected String name;
	protected String password;
	protected String role;
	
	public User(String userID, String email, String name, String password) {
		super();
		UserID = userID;
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
//	TODO: IS THIS LEGAL??
	public void updateEvents(ArrayList<Event> events) {};
	public ArrayList<Event> getEventsCreated(){return null;};
	public void updateInvitations(ArrayList<Event> events) {};

}
