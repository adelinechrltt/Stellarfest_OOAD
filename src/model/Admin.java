package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.EventController;
import javafx.scene.control.Label;
import util.Connect;

public class Admin extends User {

	private static Connect db = Connect.getInstance();

	public Admin(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		// TODO Auto-generated constructor stub
		this.role = "Admin";
	}

	public static ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<>();
		
		// asumsi admin tidak bisa mengakses admin lain sehingga tidak bisa saling delete akun admin
		String query = "SELECT * FROM Users\n "
				+ "WHERE ROLE != 'Admin'";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String ID = rs.getString("UserID");
            	String email = rs.getString("Email");
            	String name = rs.getString("Name");
            	String password = rs.getString("Password");
            	String role = rs.getString("Role");
            	
            	if(role.equals("Event Organizer")) users.add(new EventOrganizer(ID, email, name, password));
            	else if (role.equals("Vendor")) users.add(new Vendor(ID, email, name, password));
            	else if (role.equals("Guest")) users.add(new Guest(ID, email, name, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static void deleteUser(String userID) {
		String query = "DELETE FROM Users\n" +
						"WHERE UserID = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, userID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Event viewEventDetails(String eventID){
		model.Event ev = null;
		try {
			ev = EventController.viewEventDetails(eventID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
}
