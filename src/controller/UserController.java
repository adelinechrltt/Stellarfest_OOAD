package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Label;
import main.Main;
import model.Admin;
import model.EventOrganizer;
import model.Guest;
import model.Vendor;
import util.Connect;
import view.Home;
import view.LoginPage;
import view.MyProfile;

public class UserController {

	private static Connect db = Connect.getInstance();
	
	public UserController() {
		super();
	}

//	registration methods
	public static String generateUserID() {
		String UID = "U";
		for(int i=0; i<7; i++) {
			UID = UID + String.valueOf((int)(Math.random()*10));
		}
		return UID;
	}
	
	public static boolean validateUniqueEmail(String email, Label errorLbl) {
		String query = "SELECT email FROM users";
        ResultSet rs = db.execQuery(query);
        try {
			while (rs.next()) {
			    String em = rs.getString("email");
			    if(em.equals(email)) {
			    	errorLbl.setText("ERROR: E-mail must be unique!");
			    	errorLbl.setVisible(true);
			    	return false;
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return true;
	}
	
	public static boolean validateUniqueUsn(String username, Label errorLbl) {
		String query = "SELECT name FROM Users";
        ResultSet rs = db.execQuery(query);
        try {
			while (rs.next()) {
			    String un = rs.getString("name");
			    if(un.equals(username)) {
			    	errorLbl.setText("ERROR: Username must be unique!");
			    	errorLbl.setVisible(true);
			    	return false;
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return true;
	}
	
	public static void validateRegistration(String email, String username, String password, String role, Label errorLbl) {
		
		// cek apabila ada input yg kosong
		if(email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
			errorLbl.setText("ERROR: All credential fields must be filled!");
			errorLbl.setVisible(true);
			return;
		}
		
		// cek apabila email dan/atau username tidak unik
		boolean isUnique = (validateUniqueEmail(email, errorLbl) && validateUniqueUsn(username, errorLbl));
		if(!isUnique) return;
		
		// cek apabila password kurang dri 5 karakter
		if(password.length()<5) {
			errorLbl.setText("ERROR: Password must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return;
		}
		
		register(email, username, password, role);
	}
	
	public static void register(String email, String username, String password, String role) {
		String query = "INSERT INTO Users\n"
				+ "(userid, email, name, password, role) VALUES (" + 
				"?, ?, ?, ?, ?)";
		
		PreparedStatement ps;

		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, generateUserID());
			ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.execute();
            
            Main.switchScene(LoginPage.getScene());
            Main.displayAlert("Registration succesful!", "Succesfully registered a new account! Please log-in.");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
//	login methods
	
	public static boolean validateEmailMatch(String email, Label errorLbl) {
		String query = "SELECT email FROM Users";
        ResultSet rs = db.execQuery(query);
        try {
			while (rs.next()) {
			    String em = rs.getString("email");
			    if(em.equals(email)) {
			    	return true;
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	errorLbl.setText("ERROR: E-mail not found!");
    	errorLbl.setVisible(true);        
        return false;
	}
	
	public static boolean validatePwMatch(String email, String password, Label errorLbl) {
		String query = "SELECT password FROM Users WHERE email = ?";
		
		try {
        	PreparedStatement ps = db.getConnection().prepareStatement(query);
        	ps = db.getConnection().prepareStatement(query);
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
			    String pw = rs.getString("password");
//			    System.out.println(pw);
			    if(pw.equals(password)) {
			    	return true;
			    }
			}            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
    	errorLbl.setText("ERROR: Incorrect password!");
    	errorLbl.setVisible(true);
        return false;
	}
	
	public static void login(String email, String password, Label errorLbl) {
		if(email.isEmpty() || password.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return;
		}
		
		boolean accountFound = (validateEmailMatch(email, errorLbl) && validatePwMatch(email, password, errorLbl));
		if(!accountFound) return;
		
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
                    Main.currentUser = new EventOrganizer(rs.getString("UserID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Vendor")) {
                    Main.currentUser = new Vendor(rs.getString("UserID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Admin")) {
                    Main.currentUser = new Admin(rs.getString("UserID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                } else if (rs.getString("role").equals("Guest")) {
                    Main.currentUser = new Guest(rs.getString("UserID"), rs.getString("email"), rs.getString("name"), rs.getString("password"));
                }
            }
            
            Main.switchScene(Home.getScene());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
//	update credentials
	public static void updateEmail(String oldEmail, String newEmail, Label errorLbl) {
		if(newEmail.isEmpty()) {
			errorLbl.setText("ERROR: New e-mail cannot be blank!");
			errorLbl.setVisible(true);
			return;
		}
		
		if(newEmail.equalsIgnoreCase(oldEmail)) {
			errorLbl.setText("ERROR: New e-mail must be different to old-email!");
			errorLbl.setVisible(true);
			return; 
		}
		
		boolean isUnique = validateUniqueEmail(newEmail, errorLbl);
		if(!isUnique) return;
		
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
	
	public static void updateUsn(String oldUsn, String newUsn, Label errorLbl) {
		if(newUsn.isEmpty()) {
			errorLbl.setText("ERROR: New username cannot be blank!");
			errorLbl.setVisible(true);
			return;
		}
		
		if(newUsn.equalsIgnoreCase(oldUsn)) {
			errorLbl.setText("ERROR: New username must be different to old-email!");
			errorLbl.setVisible(true);
			return; 
		}
		
		boolean isUnique = validateUniqueEmail(newUsn, errorLbl);
		if(!isUnique) return;
		
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
	
	public static void changePassword(String email, String userPassword, String oldPassword, String newPassword, Label errorLbl) {
		if(oldPassword.isEmpty() || newPassword.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return;
		}
		
		if(!oldPassword.equals(userPassword)) {
			errorLbl.setText("ERROR: Old password does not match!");
			errorLbl.setVisible(true);
			return;
		}
		
		if(oldPassword.equals(newPassword)) {
			errorLbl.setText("ERROR: New password cannot be the same as old password!");
			errorLbl.setVisible(true);
			return;
		}
		
		if(newPassword.length()<5) {
			errorLbl.setText("ERROR: New password must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return;
		}
		
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
	
	public static String getNameById(String userID) {
		String name = null;
		String query = "SELECT name FROM users\n"
				+ "WHERE userId = ?"; 
	    
		PreparedStatement ps;
		
	    try {
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, userID);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	name = rs.getString("role");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return name;
	}
	
	public static String getRoleByID(String userID) {
		String role = null;
		String query = "SELECT role FROM users\n"
				+ "WHERE userId = ?"; 
	    
		PreparedStatement ps;
		
	    try {
	    	ps = db.getConnection().prepareStatement(query);
	        ps.setString(1, userID);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	role = rs.getString("role");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return role;
	}
}
