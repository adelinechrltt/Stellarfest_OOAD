package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Guest extends User {
	
	private static Connect db = Connect.getInstance();

	private ArrayList<String> acceptedInvites;

	public Guest(String userID, String email, String name, String password) {
		super(userID, email, name, password);
		this.role = "Guest";
		this.acceptedInvites = new ArrayList<>();
	}
	
	public static ArrayList<Guest> getAllGuests(){
		ArrayList<Guest> guests = new ArrayList<>();
		String query = "SELECT * FROM users "
        		+ "WHERE role = 'Guest'";
        PreparedStatement ps;

        try {
        	ps = db.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String GuestID = rs.getString("UserID");
            	String GuestEmail = rs.getString("Email");
            	String GuestName = rs.getString("Name");
            	String GuestPw = rs.getString("Password");
            	guests.add(new Guest(GuestID, GuestEmail, GuestName, GuestPw));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return guests;
	}
	
	public static Guest getGuestByEmail(String email) {
		Guest guest = null;
		String query = "SELECT * FROM Users\n"
				+ "WHERE email = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String guestID = rs.getString("userID");
				String name = rs.getString("name");
				String password = rs.getString("password");
				guest = new Guest(guestID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return guest;
	}
}
