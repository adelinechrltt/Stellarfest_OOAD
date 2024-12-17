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
		// method untuk mendapatkan semua user dalam DB
		// method ini hanya akan dipanggil oleh seorang admin
		
		ArrayList<User> users = new ArrayList<>();
		
		// asumsi bahwa admin tidak bisa mengakses admin lain
		// sehingga admin tidak bisa delete akunnya sendiri + saling delete akun admin lain
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
		// method untuk delete user berdasarkan userID nya
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
		// method untuk mendapatkan detail-detail dari suatu event berdasarkan eventID nya
		model.Event ev = null;
		try {
			ev = EventController.viewEventDetails(eventID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
}
