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
import util.RoutingHelper;
import view.Home;
import view.LoginPage;
import view.MyProfile;

public class UserController {

	public static LoginSession login = LoginSession.getInstance();

	public UserController() {
		super();
	}
	
	public static String generateUserID() {
		// method untuk generate user ID tanpa regex
		// format: U123456
		String UID = "U";
		for(int i=0; i<7; i++) {
			UID = UID + String.valueOf((int)(Math.random()*10));
		}
		return UID;
	}
	
	public static boolean validateUniqueEmail(String email, Label errorLbl) {
		// method untuk validasi apakah email yang diinput unik
		// flow adalah sbg berikut:
		
		// 1. mendapatkan semua email dalam database
        ArrayList<String> emails = User.getAllEmails();
        
        // 2. cek apakah terdapat kesamaan antara email input dengan salah satu email dalam database
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
		// method untuk validasi apakah username yang diinput unik
		// flow adalah sbg berikut:
		
		// 1. mendapatkan semua username dalam database
        ArrayList<String> names = User.getAllNames();
        
        // 2. cek apakah terdapat kesamaan antara username input dengan salah satu username dalam database
        for(String name : names) {
        	if(name.equals(username)){
		    	errorLbl.setText("ERROR: Username must be unique!");
		    	errorLbl.setVisible(true);
		    	return false;
		    }
        }
        
        return true;
	}
	
	public static boolean checkRegisterInput(String email, String username, String password, String role, Label errorLbl) {
		// method untuk melakukan validasi semua input untuk registrasi
		
		// 1. cek apabila ada input yg kosong
		if(email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
			errorLbl.setText("ERROR: All credential fields must be filled!");
			errorLbl.setVisible(true);
			return false;
		}
		
		// 2. cek apabila email dan/atau username tidak unik
		boolean isUnique = (validateUniqueEmail(email, errorLbl) && validateUniqueUsn(username, errorLbl));
		if(!isUnique) return false;
		
		// 3. cek apabila password kurang dri 5 karakter
		if(password.length()<5) {
			errorLbl.setText("ERROR: Password must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	public static void register(String email, String username, String password, String role, Label errorLbl) {
		// method untuk mendaftarkan user baru
		
		// cek apabila semua input yang diberikan valid atau tidak
		boolean isValidInput = checkRegisterInput(email, username, password, role, errorLbl);
		if(!isValidInput) return;
		
		// cek apabila berhasil register
		boolean registerSuccess = User.register(generateUserID(), email, username, password, role);
		if(registerSuccess) {
			RoutingHelper.showLoginPage();
			Main.displayAlert("Registration succesful!", "Succesfully registered a new account! Please log-in.");		
		} else Main.displayAlert("ERROR", "Unsuccesful registration, please try again.");
		
	}
	
	
//	login methods
	public static boolean validateEmailMatch(String email, Label errorLbl) {
		// method untuk validasi bahwa email yang diinput merupakan email dari suatu akun yang sudah terdata dalam database
		ArrayList<String> emails = User.getAllEmails(); 
        for(String em : emails) {
        	if(em.equals(email)) return true;
        }
    	errorLbl.setText("ERROR: E-mail not found!");
    	errorLbl.setVisible(true);        
        return false;
	}
	
	public static boolean validatePwMatch(String email, String password, Label errorLbl) {
		// method untuk validasi bahwa password yang diinput merupakan password dari akun dengan email tertentu
		String pw = User.getPasswordByEmail(email);
		if(pw.equals(password)) {
			return true;
		}
		
    	errorLbl.setText("ERROR: Incorrect password!");
    	errorLbl.setVisible(true);
        return false;
	}
	
	public static void login(String email, String password, Label errorLbl) {
		// method untuk login user
		
		// 1. validasi email dan password tidak boleh kosong
		if(email.isEmpty() || password.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 2. validasi bahwa email yang diinput merupakan milik suatu akun yang sudah terdaftar ke dalam database
		// serta password yang diinput benar-benar milik akun tersebut
		boolean accountFound = (validateEmailMatch(email, errorLbl) && validatePwMatch(email, password, errorLbl));
		if(!accountFound) return;
        
		// apabila lolos validasi, maka user akan di-log in melalui singleton
		User user = User.login(email, password);
		login.setLoggedInUser(user);
		RoutingHelper.showHomePage();
	}
	
	
//	update credentials
// 	method checkChangeProfile kami pecah menjadi checkChangeEmail, checkChangeUsername, checkChangePassword
// 	sesuai dengan requirement validasi yang tertera di word, di mana user harus bisa:
//	1) mengupdate hanya salah satu atribut di waktu tertentu
// 	2) dicek input, apakah credetial baru sama dengan yang lama atau tidak
	
	public static void checkChangeEmail(String oldEmail, String newEmail, Label errorLbl) {
		// method untuk validasi email saat update email
		
		// 1. validasi bahwa email tidak kosong
		if(newEmail.isEmpty()) {
			errorLbl.setText("ERROR: New e-mail cannot be blank!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 2. validasi bahwa email baru yang diinput berbeda dengan email lama dari akun ybs
		if(newEmail.equalsIgnoreCase(oldEmail)) {
			errorLbl.setText("ERROR: New e-mail must be different to old-email!");
			errorLbl.setVisible(true);
			return; 
		}
		
		// 3. validasi bahwa email baru berupa email unik dan tidak sudah dimiliki oleh akun lain
		boolean isUnique = validateUniqueEmail(newEmail, errorLbl);
		if(!isUnique) return;
		
		// mengganti email dengan memasukkan value email lama dan baru ke dalam method changeProfile 
		User user = login.getLoggedInUser();
		System.out.println(user);
		User.changeProfile(user.getEmail(), newEmail, user.getName(), user.getPassword());
		
		login.getLoggedInUser().setEmail(newEmail);
		System.out.println(login.getLoggedInUser().getEmail());
		
		RoutingHelper.showProfilePage();
	}
	
	public static void checkChangeUsn(String oldUsn, String newUsn, Label errorLbl) {
		// method untuk validasi username saat update username
		
		// 1. validasi bahwa username baru tidak kosong
		if(newUsn.isEmpty()) {
			errorLbl.setText("ERROR: New username cannot be blank!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 2. validasi bahwa usernmae baru yang diinput berbeda dengan username lama dari akun ybs
		if(newUsn.equalsIgnoreCase(oldUsn)) {
			errorLbl.setText("ERROR: New username must be different to old-email!");
			errorLbl.setVisible(true);
			return; 
		}
		
		// 3. validasi bahwa username baru yang diinput unik dan tidak sudah dimiliki oleh akun lain
		boolean isUnique = validateUniqueUsn(newUsn, errorLbl);
		if(!isUnique) return;
		
		// mengganti username dengan memasukkan value username baru ke dalam method changeProfile 
		User user = login.getLoggedInUser();
		User.changeProfile(user.getEmail(), user.getEmail(), newUsn, user.getPassword());     
		
		login.getLoggedInUser().setName(newUsn);
		System.out.println(login.getLoggedInUser().getName());

		RoutingHelper.showProfilePage();
	}
	
	public static void checkChangePassword(String email, String userPassword, String oldPassword, String newPassword, Label errorLbl) {
		// method untuk validasi password input
		
		// 1. validasi bahwa old password dan new password tidak kosong
		if(oldPassword.isEmpty() || newPassword.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 2. validasi bahwa password lama sesuai atau tidak
		if(!oldPassword.equals(userPassword)) {
			errorLbl.setText("ERROR: Old password does not match!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 3. validasi bahwa password lama dan password baru tidak sama
		if(oldPassword.equals(newPassword)) {
			errorLbl.setText("ERROR: New password cannot be the same as old password!");
			errorLbl.setVisible(true);
			return;
		}
		
		// 4. validasi bahwa panjang password baru tidak kurang dari 5 karakter
		if(newPassword.length()<5) {
			errorLbl.setText("ERROR: New password must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return;
		}
		
		// mengganti password dengan memasukkan value password baru ke dalam method changeProfile 
		User user = login.getLoggedInUser();
		User.changeProfile(email, email, user.getName(), newPassword);       
		
		login.getLoggedInUser().setPassword(newPassword);
		System.out.println(login.getLoggedInUser().getPassword());
		
		RoutingHelper.showProfilePage();
	}
	
	public static void changeProfile(String oldEmail, String newEmail, String usn, String password) {
		// method untuk memanggil query mengganti detail profil di model
//		try {
			User.changeProfile(oldEmail, newEmail, usn, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static User getUserByEmail(String email) {
		// method untuk mendapatkan seorang user berdasarkan email
		User user = null;
		user = User.getUserByEmail(email);
		
		return user;
	}
	
	public static User getUserByUsername(String username) {
		// method untuk mendapatkan seorang user berdasarkan username
		User user = null;
		user = User.getUserByUsername(username);
		
		return user;
	}
	
	public static void logout() {
		login.setLoggedInUser(null);
	}
}
