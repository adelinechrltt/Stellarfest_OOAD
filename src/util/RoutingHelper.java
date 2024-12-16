package util;

import main.Main;
import model.Product;
import view.ChangeProfile;
import view.CreateEventPage;
import view.Home;
import view.LoginPage;
import view.MyProfile;
import view.RegisterPage;
import view.ViewEventDetails;
import view.ViewEventsList;
import view.AdminViews.ViewAllEventsPage;
import view.AdminViews.ViewAllUsersPage;
import view.EventOrganizerViews.InviteGuest;
import view.EventOrganizerViews.InviteVendor;
import view.EventOrganizerViews.UpdateEventNamePage;
import view.VendorAndGuestViews.InvitationDetailsPage;
import view.VendorAndGuestViews.ViewInvitationsPage;
import view.VendorViews.MyProductsPage;
import view.VendorViews.UpdateProductPage;
import view.VendorViews.ViewProductDetails;

public class RoutingHelper {
	
	// account authentication & home pages
	public static void showRegisterPage() {
		 Main.switchScene(RegisterPage.getScene());
	}
	
	public static void showLoginPage() {
		 Main.switchScene(LoginPage.getScene());
	}
	
	public static void showHomePage() {
		Main.switchScene(Home.getScene());
	}
	
	
//	events pages
	public static void showEventsListPage() {
		Main.switchScene(ViewEventsList.getScene());
	}
	
	public static void showAllEventsListPage() {
    	Main.switchScene(ViewAllEventsPage.getScene());
	}
	
	public static void showEventDetailsPage(String eventID) {
		 Main.switchScene(ViewEventDetails.getScene(eventID));
	}
	
	public static void showCreateEventPage() {
    	Main.switchScene(CreateEventPage.getScene());
	}
	
	public static void showUpdateEventNamePage(String eventID, String name) {
		 Main.switchScene(UpdateEventNamePage.getScene(eventID, name));
	}
	
	
// invitation pages
	public static void showInvitationsListPage() {
    	Main.switchScene(ViewInvitationsPage.getScene());
	}
	
	public static void showInvitationDetailsPage(String invID) {
		Main.switchScene(InvitationDetailsPage.getScene(invID));
	}
	
	
//	product pages
	public static void showProductsPage(String userID) {
    	Main.switchScene(MyProductsPage.getScene(userID));
	}
	
	public static void showProductDetailsPage(String productID) {
		 Main.switchScene(ViewProductDetails.getScene(productID));
	}
	
	public static void showUpdateProductPage(Product product) {
		Main.switchScene(UpdateProductPage.getScene(product));
	}
	
	
//	users, vendors, & guests page
	public static void showAllUsersPage() {
    	Main.switchScene(ViewAllUsersPage.getScene());
	}
	
	public static void showInviteVendorsPage(String eventID) {
		Main.switchScene(InviteVendor.getScene(eventID));
	}
	
	public static void showInviteGuestsPage(String eventID) {
		Main.switchScene(InviteGuest.getScene(eventID));
	}
	
	
//	profile page + update profile functions
	public static void showProfilePage() {
    	Main.switchScene(MyProfile.getScene());
	}
	
	public static void showUpdateEmailPage() {
		 Main.switchScene(ChangeProfile.getUpdateEmailScene());
	}
	
	public static void showUpdateUsernamePage() {
		 Main.switchScene(ChangeProfile.getUpdateUsnScene());
	}
	
	public static void showUpdatePasswordPage() {
		 Main.switchScene(ChangeProfile.getUpdatePwScene());
	}
	
}

