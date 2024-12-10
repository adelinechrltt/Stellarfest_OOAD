package model;

public class Admin extends User {

	public Admin(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		// TODO Auto-generated constructor stub
		this.role = "Admin";
	}

}
