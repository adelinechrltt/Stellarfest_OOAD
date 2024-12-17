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
		// method untuk mendapatkan semua vendor dalam database
		ArrayList<Vendor> vendors = Vendor.getAllVendors();
        return vendors;
	}
	
	public static boolean checkManageVendorInput(String name, String description, Label errorLbl) {
		// method untuk memvalidasi input baru untuk suatu produk
		
		// 1. validasi bahwa tidak ada field yang kosong
		if(name.isEmpty() || description.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return false;
		} else if (name.length() > 200) { // 2. validasi bahwa panjang nama tidak lebih dari 200 karakter
			errorLbl.setText("ERROR: Name cannot be longer than 200 characters!");
			errorLbl.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	// asumsi bahwa manageVendor berupa kumpulan operasi2 CRUD
	// dan kumpulan operasinya dinamakan createProduct, updateProduct, deleteProduct, etc.
	public static void createProduct(String userID, String name, String description, Label errorLbl) {
		// method untuk membuat produk baru
		boolean isValid = checkManageVendorInput(name, description, errorLbl); // flag untuk cek apakah input lolos validasi
		if(isValid) {			
			ProductController.createProduct(userID, name, description);
			RoutingHelper.showProductsPage(userID);
			Main.displayAlert("Info", "Succesfully created new product!");
		}
	}
	
	public static void updateProduct(String userID, String productID, String name, String description, Label errorLbl) {
		// method untuk mengubah detail produk yang sudah ada
		boolean isValid = checkManageVendorInput(name, description, errorLbl); // flag untuk cek apakah input lolos validasi
		if(isValid) {
			ProductController.updateProduct(productID, name, description);
			RoutingHelper.showProductsPage(userID);
			Main.displayAlert("Info", "Succesfully updated product!");
		}
	}
	
	public static void deleteProduct(String productID, String userID) {
		// method untuk menghapus suatu produk
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
		// method untuk menerima undangan menghadiri event dari seorang eventorganizer
		Invitation.acceptInvitation(invID, errorLbl);
	}
	
	public static ArrayList<Event> viewAcceptedEvents (String email){
		// method bagi seorang guest untuk mendapatkan daftar event yang akan dihadirinya
		// flow adalah sbg berikut:
		
		// 1. mendapatkan semua invitation yang ditujukan kepada email guest tertentu
		ArrayList<Invitation> invites = InvitationController.getInvitations(email);
		ArrayList<Event> events = new ArrayList<>();
		
		// 2. mengecek semua invitation, apakah vendor sudah accept invitationnya atau belum
		for(Invitation inv : invites) {
			 // 3. apabila vendor sudah accept invitationnya, maka event ybs akan dimasukkan ke dalam
			 // arraylist of events yang akan ditampilkan di view
			 if(inv.getStatus().equals("Accepted")) {
				 Event ev = EventController.viewEventDetails(inv.getEventID());
				 events.add(ev);
			 }
		 }
		return events;
	}
}
