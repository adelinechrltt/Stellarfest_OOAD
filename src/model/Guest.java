package model;

import java.util.ArrayList;

public class Guest extends User {
	private ArrayList<String> acceptedInvites;

	public Guest(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Guest";
		this.acceptedInvites = new ArrayList<>();
	}
}
