package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Event;
import model.Invitation;
import model.Vendor;
import util.Connect;
import view.VendorViews.MyProductsPage;

public class VendorController {

	private static Connect db = Connect.getInstance();
	
	public static Vendor getVendorByEmail(String email) {
		Vendor vendor = null;
		String query = "SELECT * FROM Users\n"
				+ "WHERE email = ?";
		PreparedStatement ps;
		
		try {
			ps = db.getConnection().prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String userID = rs.getString("userID");
				String name = rs.getString("name");
				String password = rs.getString("password");
				vendor = new Vendor(userID, email, name, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return vendor;
	}
	
	public static ArrayList<Vendor> getAllVendors(){
		ArrayList<Vendor> vendors = new ArrayList<>();
		
        String query = "SELECT * FROM users "
        		+ "WHERE role = 'Vendor'";
        PreparedStatement ps;

        try {
        	ps = db.getConnection().prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String vendorID = rs.getString("UserID");
            	String vendorEmail = rs.getString("Email");
            	String vendorName = rs.getString("Name");
            	String vendorPw = rs.getString("Password");
            	vendors.add(new Vendor(vendorID, vendorEmail, vendorName, vendorPw));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
	}
	
	public static ArrayList<Vendor> getUninvitedVendors(String eventID){
		ArrayList<Vendor> vendors = getAllVendors();
		ArrayList<String> invitedVendorIDs = InvitationController.getInvitedUsersByEventID(eventID);
		
	    vendors.removeIf(vendor -> invitedVendorIDs.contains(vendor.getUserID()));
		
		if(vendors.isEmpty()) vendors = null;
		return vendors;
	}

	public static boolean checkManageVendorInput(String name, String description, Label errorLbl) {
		if(name.isEmpty() || description.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return false;
		} else if (name.length() > 200) {
			errorLbl.setText("ERROR: Name cannot be longer than 200 characters!");
			errorLbl.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	// asumsi bahwa manageVendor berupa kumpulan operasi2 CRUD
	// dan kumpulan operasinya dinamakan createProduct, updateProduct, deleteProduct, etc.
	public static void createProduct(String userID, String name, String description, Label errorLbl) {
		boolean isValid = checkManageVendorInput(name, description, errorLbl);
		if(isValid) {			
			ProductController.createProduct(userID, name, description);
			Main.switchScene(MyProductsPage.getScene(userID));
			Main.displayAlert("Info", "Succesfully created new product!");
		}
	}
	
	public static void updateProduct(String userID, String productID, String name, String description, Label errorLbl) {
		boolean isValid = checkManageVendorInput(name, description, errorLbl);
		if(isValid) {
			ProductController.updateProduct(productID, name, description);
			Main.switchScene(MyProductsPage.getScene(userID));
			Main.displayAlert("Info", "Succesfully updated product!");
		}
	}
	
	public static void deleteProduct(String productID, String userID) {
		try {
			ProductController.deleteProduct(productID);
			Main.displayAlert("Info", "Succesfully deleted product!");
			Main.switchScene(MyProductsPage.getScene(userID));
		} catch (Exception e) {
			e.printStackTrace();
			Main.displayAlert("ERROR", "Failed to delete product!");
		}
	}
	
	public static void acceptInvitation(String eventID, Label errorLbl) {
		InvitationController.acceptInvitation(eventID, errorLbl);
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		ArrayList<Event> events = new ArrayList<>();
		ArrayList<Invitation> invites = InvitationController.getInvitationsByEmail(email);
		for(Invitation inv : invites) {
			 if(inv.getStatus().equals("Accepted")) {
				 Event ev = EventController.getEventByID(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
}
