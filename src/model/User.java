package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import util.Connect;
import view.MyProfile;

public class User {
	
	private static Connect db = Connect.getInstance();
	
	protected String UserID;
	protected String email;
	protected String name;
	protected String password;
	protected String role;
	
	// constructor & getter-setter 
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
	
	// berupa template method yang akan digunakan oleh child class dari User
	public void updateEvents(ArrayList<Event> events) {}; // --> digunakan oleh EventOrganizer
	public ArrayList<Event> getEventsCreated(){return null;}; // --> digunakan oleh Vendor, Guest, dan EventOrganizer
	public void updateInvitations(ArrayList<Event> events) {}; // --> digunakan oleh EventOrganizer

	
	// query methods
	public static ArrayList<String> getAllEmails() {
		// method untuk memperoleh email dari semua akun dalam database
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
		// method untuk memperoleh username dari semua akun dalam database
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
		// method untuk memperoleh password dari suatu akun dalam database yang diperoleh melalui email
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
		// method untuk melakukan registrasi user
		// return boolean untuk menandakan apakah registrasi berhasil atau tidak
		// --> true: registrasi berhasil
		// --> false: registrasi gagal

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
	
	public static User login(String email, String password) {
		// method untuk login
		// return User yang akan disimpan ke dalam singleton loginSession
		
		User user = null;
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
                    user = new EventOrganizer(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Vendor")) {
                    user = new Vendor(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Admin")) {
                    user = new Admin(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Guest")) {
                    user = new Guest(rs.getString("userID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                }
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        
        return user;
	}
	
	public static void changeProfile(String oldEmail, String newEmail, String usn, String password) {
		// method untuk mengupdate data profil suatu user
		String query = "UPDATE users\n"
				+ "SET email = ?, name = ?, password = ?\n"
				+ "WHERE email = ?;\n";
        try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, newEmail);
            ps.setString(2, usn);
            ps.setString(3, password);
            ps.setString(4, oldEmail);
            ps.executeUpdate();            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	public static User getUserByEmail(String email) {
		// method untuk mendapatkan seorang user dari database berdasarkan emailnya
		User user = null;
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
				String role = rs.getString("role");
				if(role.equals("Event Organizer")) user = new EventOrganizer(guestID, email, name, password);
				else if(role.equals("Guest")) user = new Guest(guestID, email, name, password);
				else if(role.equals("Vendor")) user = new Vendor(guestID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public static User getUserByUsername(String name) {
		// method untuk mendapatkan seorang user dari database berdasarkan usernamenya
		User user = null;
		String query = "SELECT * FROM Users\n"
				+ "WHERE name = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String guestID = rs.getString("userID");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String role = rs.getString("role");
				if(role.equals("Event Organizer")) user = new EventOrganizer(guestID, email, name, password);
				else if(role.equals("Guest")) user = new Guest(guestID, email, name, password);
				else if(role.equals("Vendor")) user = new Vendor(guestID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
