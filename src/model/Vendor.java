package model;

import java.util.*;

public class Vendor extends User{
	
	private ArrayList<String> acceptedInvites;

	public Vendor(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Vendor";
		this.acceptedInvites = new ArrayList<>();
	}

}
