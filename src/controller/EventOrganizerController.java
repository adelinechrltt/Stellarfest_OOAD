package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Label;
import main.Main;
import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.Vendor;
import util.LoginSession;
import util.RoutingHelper;

public class EventOrganizerController {
		
	public static LoginSession login = LoginSession.getInstance();
	
	public EventOrganizerController() {
		super();
	}
	
	public static ArrayList<Event> viewOrganizedEvents(String email){
		// method untuk melihat event-event apa saja yang sudah dibuat oleh seorang event organizer
		ArrayList<Event> events = EventOrganizer.viewOrganizedEvents(email);
		return events;
	}
	
	public static Event viewOrganizedEventDetails(String eventID){
		// method untuk melihat detail-detail dari suatu event yang sudah dibuat oleh seorang event organizer
		model.Event ev = null;
		try {
			ev = EventController.viewEventDetails(eventID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
	
	public static boolean checkCreateEventInput(String name, LocalDate date, String location, String description, String organizerID, Label errorLbl) {
		// method untuk validasi input user
		
		// 1. validasi bahwa tidak ada field yang kosong
		if(name.isEmpty() || date == null || location.isEmpty() || description.isEmpty()) {
			errorLbl.setText("ERROR: All fields must be filled!");
			errorLbl.setVisible(true);
			return false;
		}
		
		// 2. validasi bahwa tanggal event harus di masa depan, di mana asumsi adalah setelah hari ini
		LocalDate today = LocalDate.now();
		if(!date.isAfter(today)) {
			errorLbl.setText("ERROR: Event must be scheduled for the future!");
			errorLbl.setVisible(true);
			return false;
		}
		
		// 3. validasi bahwa nama lokasi event harus lebih dari 5 karakter
		if(location.length()<5) {
			errorLbl.setText("ERROR: Event location must be at least 5 characters long!");
			errorLbl.setVisible(true);
			return false;
		}
		
		// 4. validasi bahwa panjang deskripsi harus lebih dari 200 karakter
		if(description.length()>200) {
			errorLbl.setText("ERROR: Event location must not be over 200 characters long!");
			errorLbl.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	public static void createEvent(String name, LocalDate date, String location, String description, String organizerID, Label errorLbl) {
		// method bagi event organizer untuk membuat event baru
		
		// flag untuk menyatakan apakah input yang disediakan event organizer lolos validasi
		boolean isValid = checkCreateEventInput(name, date, location, description, organizerID, errorLbl);
		if(!isValid) return;
		
		// chain method ke eventController untuk membuat event baru
		EventController.createEvent(name, date, location, description, organizerID);
	}
	
	public static void editEventName(String eventID, String name, Label errorLbl) {
		// method untuk mengubah nama event
		
		// validasi bahwa input nama tidak boleh kosong
		if(name.isEmpty()) {
			errorLbl.setText("ERROR: Name cannot be empty!");
			errorLbl.setVisible(true);
			return;
		}
		
		// chain method ke eventController untuk mengubah nama event
		EventController.updateEventName(eventID, name);
	
		Main.displayAlert("Update success!", "Event name succesfully updated.");
		RoutingHelper.showEventsListPage();
	}
	
	public static void inviteToEvent(String eventID, String email) {
		// method untuk membuat invitation baru
		// chain method ke InvitationController
		InvitationController.sendInvitation(eventID, email);
	}
	
	// =========================================================================================================
	
	// kami asumsikan bahwa checkAddGuestInput() & checkAddVendorInput() berfungsi untuk mengecek apakah guest / vendor
	// yang ingin diundang ke event X sudah diundang ke event tersebut sebelumnya.
	
	// namun karena requirement di word menyatakan bahwa checkbox list yang ditampilkan di view layer harus berupa
	// kumpulan user yang dipastikan MEMANG BELUM diundang ke event tersebut, maka kami membuat flow checkAddUser() sedikit 
	// berbeda dari sequence diagram, di mana flow kami:
	
	// checkAddUser():
	// getAllGuests / getAllVendors --> hapus user2 yg sudah diundang sebelumnya --> display list di view layer
	
	// method2 check ini juga tidak kami masukkan ke dalam model Event, karena hanya berupa validasi. bukan query SQL ke database
	
	public static ArrayList<Guest> checkAddGuestInput(String eventID){
		// method untuk mendapatkan daftar guest yang belum diundang ke suatu event tertentu
		// flow adalah sbg berikut:
		
		// 1. mendapatkan data dari semua guest yang ada dalam database
		ArrayList<Guest> Guests = getGuests();
		
		// 2. mendapatkan data dari semua invitation untuk suatu event tertentu dalam database
		ArrayList<String> invitedGuestEmails = InvitationController.getInvitedUsersByEventID(eventID);
		
		// 3. menghapus guest dari arrayList apabila ditemukan bahwa guest tsb sudah sebelumnya diundang, 
		// dilihat dari data arrayList undangan yang didapatkan 
	    Guests.removeIf(Guest -> invitedGuestEmails.contains(Guest.getEmail()));
		
		if(Guests.isEmpty()) Guests = null;
		return Guests;
	}
	
	public static ArrayList<Vendor> checkAddVendorInput(String eventID){
		// method untuk mendapatkan daftar guest yang belum diundang ke suatu event tertentu
		// flow adalah sbg berikut:
		
		// 1. mendapatkan data dari semua vendor yang ada dalam database
		ArrayList<Vendor> vendors = getVendors();
		
		// 2. mendapatkan data dari semua invitation untuk suatu event tertentu dalam database
		ArrayList<String> invitedVendorEmails = InvitationController.getInvitedUsersByEventID(eventID);
		
		// 3. menghapus guest dari arrayList apabila ditemukan bahwa guest tsb sudah sebelumnya diundang, 
		// dilihat dari data arrayList undangan yang didapatkan 
	    vendors.removeIf(vendor -> invitedVendorEmails.contains(vendor.getEmail()));
		
		if(vendors.isEmpty()) vendors = null;
		return vendors;
	}
	
//	asumsi bahwa transaction merupakan typo untuk ubiquitous language event
// 	sehingga: 
//	getVendorsByTransactionId --> getVendorsByEventId
//	getGuestsByTransactionId --> getGuestsByEventId
	
	public static ArrayList<Vendor> getVendorsByEventId(String eventID){
		// method untuk mendapatkan vendor-vendor yang akan menghadiri suatu event
		// berdasarkan apakah vendor tersebut sudah menerima invitation untuk hadir
		
		ArrayList<String> vendorEmails = EventOrganizer.getVendorsByEventId(eventID);
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<Vendor> vendors = new ArrayList<>();
		for (String email : vendorEmails) {
			vendors.add((Vendor) UserController.getUserByEmail(email));
		}
		return vendors;
	}
	
	public static ArrayList<Guest> getGuestsByEventId(String eventID){
		// method untuk mendapatkan guest-guest yang akan menghadiri suatu event
		// berdasarkan apakah vendor tersebut sudah menerima invitation untuk hadir
		
		// karena method di atas return arrayList of emails, maka data masing2 user harus dicari lagi  
		// dengan method chain ke user controller berdasarkan email yang didapatkan
		ArrayList<String> guestEmails = EventOrganizer.getGuestsByEventId(eventID);
		ArrayList<Guest> guests = new ArrayList<>();
		for (String email : guestEmails) {
			guests.add((Guest) UserController.getUserByEmail(email));
		}
		return guests;
	}
	
	public static ArrayList<Vendor> getVendors(){
		return VendorController.getAllVendors();
	}
	
	public static ArrayList<Guest> getGuests(){
		return GuestController.getAllGuests();
	}
	
}
