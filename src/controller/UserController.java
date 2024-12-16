package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Admin;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;
import util.Connect;
import util.LoginSession;
import view.Home;
import view.LoginPage;
import view.MyProfile;

public class UserController {

	public static LoginSession login = LoginSession.getInstance();

	public UserController() {
		super();
	}
	
	public static String generateUserID() {
		String UID = "U";
		for(int i=0; i<7; i++) {
			UID = UID + String.valueOf((int)(Math.random()*10));
		}
		return UID;
	}
	
	public static boolean validateUniqueEmail(String email, Label errorLbl) {
        ArrayList<String> emails = User.getAllEmails();
        for(String em : emails) {
        	if(em.equals(email)) {
            	errorLbl.setText("ERROR: E-mail must be unique!");
            	errorLbl.setVisible(true);
            	return false;
            }
        }
        
        return true;
	}
	
	public static boolean validateUniqueUsn(String username, Label errorLbl) {
        ArrayList<String> names = User.getAllNames();
        
        for(String name : names) {
        	if(name.equals(username)){
		    	errorLbl.setText("ERROR: Username must be unique!");
		    	errorLbl.setVisible(true);
		    	return false;
		    }
        }
        
        return true;
	}
	
	public static void checkRegisterInput(String email, String username, String password, String role, Label errorLbl) {
		
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
		boolean registerSuccess = User.register(generateUserID(), email, username, password, role);
		if(registerSuccess) {
	        Main.switchScene(LoginPage.getScene());
	        Main.displayAlert("Registration succesful!", "Succesfully registered a new account! Please log-in.");		
		} else Main.displayAlert("ERROR", "Unsuccesful registration, please try again.");
		
	}
	
	
//	login methods
	
	public static boolean validateEmailMatch(String email, Label errorLbl) {
		ArrayList<String> emails = User.getAllEmails(); 
        for(String em : emails) {
        	if(em.equals(email)) return true;
        }
    	errorLbl.setText("ERROR: E-mail not found!");
    	errorLbl.setVisible(true);        
        return false;
	}
	
	public static boolean validatePwMatch(String email, String password, Label errorLbl) {
		String pw = User.getPasswordByEmail(email);
		if(pw.equals(password)) {
			return true;
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
            
		User user = User.login(email, password);
		login.setLoggedInUser(user);
        Main.switchScene(Home.getScene());
        
	}
	
	
//	update credentials
// 	method checkChangeProfile saya pecah menjadi checkChangeEmail, checkChangeUsername, checkChangePassword
// 	sesuai dengan requirement validasi yang tertera di word, di mana user harus bisa:
//	1) mengupdate hanya salah satu atribut di waktu tertentu
// 	2) dicek input, apakah credetial baru sama dengan yang lama atau tidak
	
	public static void checkChangeEmail(String oldEmail, String newEmail, Label errorLbl) {
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
		
		User user = User.getUserByEmail(oldEmail);
		
		User.changeProfile(user.getEmail(), newEmail, user.getName(), user.getPassword());       
        Main.switchScene(MyProfile.getScene());
	}
	
	public static void checkChangeUsn(String oldUsn, String newUsn, Label errorLbl) {
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
		
		User user = User.getUserByUsername(oldUsn);
		
		User.changeProfile(user.getEmail(), user.getEmail(), newUsn, user.getPassword());       
		Main.switchScene(MyProfile.getScene());
	}
	
	public static void checkChangePassword(String email, String userPassword, String oldPassword, String newPassword, Label errorLbl) {
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
		
		User user = User.getUserByEmail(email);
		User.changeProfile(email, email, user.getName(), newPassword);       
		
	}
	
	public static void changeProfile(String oldEmail, String newEmail, String usn, String password) {
		User.changeProfile(oldEmail, newEmail, usn, password);
		login.getLoggedInUser().setEmail(newEmail);
		login.getLoggedInUser().setName(usn);
		login.getLoggedInUser().setPassword(password);
		Main.switchScene(MyProfile.getScene());
	}
	
	public static User getUserByEmail(String email) {
		User user = null;
		user = User.getUserByEmail(email);
		
		return user;
	}
	
	public static User getUserByUsername(String username) {
		User user = null;
		user = User.getUserByUsername(username);
		
		return user;
	}
}
