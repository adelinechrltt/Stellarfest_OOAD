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
import util.RoutingHelper;
import view.VendorViews.MyProductsPage;

public class VendorController {

	private static Connect db = Connect.getInstance();
	
	public static ArrayList<Vendor> getAllVendors(){
		ArrayList<Vendor> vendors = Vendor.getAllVendors();
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
			RoutingHelper.showProductsPage(userID);
			Main.displayAlert("Info", "Succesfully created new product!");
		}
	}
	
	public static void updateProduct(String userID, String productID, String name, String description, Label errorLbl) {
		boolean isValid = checkManageVendorInput(name, description, errorLbl);
		if(isValid) {
			ProductController.updateProduct(productID, name, description);
			RoutingHelper.showProductsPage(userID);
			Main.displayAlert("Info", "Succesfully updated product!");
		}
	}
	
	public static void deleteProduct(String productID, String userID) {
		try {
			ProductController.deleteProduct(productID);
			Main.displayAlert("Info", "Succesfully deleted product!");
			RoutingHelper.showProductsPage(userID);
		} catch (Exception e) {
			e.printStackTrace();
			Main.displayAlert("ERROR", "Failed to delete product!");
		}
	}
	
	// method acceptInvtation ini tidak dimasukkan ke model Vendor / Guest karena berhubungan dengan DB table invitation
	// sehingga kami prefer agar controller vendor / guest langsung chaining saja ke controller invitation
	public static void acceptInvitation(String invID, Label errorLbl) {
		Invitation.acceptInvitation(invID, errorLbl);
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		ArrayList<Event> events = new ArrayList<>();
		ArrayList<Invitation> invites = InvitationController.getInvitations(email);
		for(Invitation inv : invites) {
			 if(inv.getStatus().equals("Accepted")) {
				 Event ev = EventController.viewEventDetails(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
}
