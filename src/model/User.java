package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import util.Connect;
import view.MyProfile;

public abstract class User {
	
	private static Connect db = Connect.getInstance();
	
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
	
	public void updateEvents(ArrayList<Event> events) {};
	public ArrayList<Event> getEventsCreated(){return null;};
	public void updateInvitations(ArrayList<Event> events) {};

	
	public static ArrayList<String> getAllEmails() {
		String query = "SELECT email FROM users";
		ResultSet rs = null;
		ArrayList<String> emails = new ArrayList<>();
		
        try {
			PreparedStatement ps = db.getConnection().prepareCall(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				emails.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return emails;
	}
	
	public static ArrayList<String> getAllNames() {
		String query = "SELECT name FROM users";
		ResultSet rs = null;
		ArrayList<String> names = new ArrayList<>();
		
        try {
			PreparedStatement ps = db.getConnection().prepareCall(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				names.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return names;
	}
	
	public static String getPasswordByEmail(String email) {
		String query = "SELECT password FROM Users WHERE email = ?";
		ResultSet rs = null;
		String password = null;
		
		try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
        	ps = db.getConnection().prepareStatement(query);
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
            	password = rs.getString("password");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}
	
	public static boolean register(String UID, String email, String username, String password, String role) {
		boolean registerSuccess = false;
		String query = "INSERT INTO Users\n"
				+ "(userID, email, name, password, role) VALUES (" + 
				"?, ?, ?, ?, ?)";
		
		PreparedStatement ps;

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, UID);
			ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.execute();
            registerSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			registerSuccess = false;
		}
		return registerSuccess;
	}
	
	public static void login(String email, String password) {
		String query = "SELECT * FROM users\n"
        		+ "WHERE email = ? AND password = ?";
        try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
        	ps = db.getConnection().prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
            	if(rs.getString("role").equals("Event Organizer")) {
                    Main.currentUser = new EventOrganizer(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Vendor")) {
                    Main.currentUser = new Vendor(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Admin")) {
                    Main.currentUser = new Admin(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Guest")) {
                    Main.currentUser = new Guest(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                }
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
	}
	
	public static void updateEmail(String oldEmail, String newEmail) {
		String query = "UPDATE users\n"
				+ " SET email = ? WHERE email = ?";
        try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, newEmail);
            ps.setString(2, oldEmail);
            ps.executeUpdate();
            
            Main.currentUser.setEmail(newEmail);
            Main.switchScene(MyProfile.getScene());
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	public static void updateUsn(String oldUsn, String newUsn) {
		String query = "UPDATE users\n"
				+ " SET name = ? WHERE name = ?";
        try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, newUsn);
            ps.setString(2, oldUsn);
            ps.executeUpdate();
            
            Main.currentUser.setName(newUsn);
            Main.switchScene(MyProfile.getScene());
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	public static void changePassword(String email, String userPassword, String oldPassword, String newPassword) {
		String query = "UPDATE users\n"
				+ " SET password = ? WHERE email = ?";
        try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();
            
            Main.currentUser.setPassword(newPassword);
            Main.switchScene(MyProfile.getScene());
        } catch (SQLException e) {
        	e.printStackTrace();
        }   
	}
	
}
