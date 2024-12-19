package view;

import controller.UserController;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.Main;
import util.LoginSession;
import util.RoutingHelper;
import view.AdminViews.ViewAllEventsPage;
import view.AdminViews.ViewAllUsersPage;
import view.VendorAndGuestViews.ViewInvitationsPage;
import view.VendorViews.MyProductsPage;

public class NavBar {	
	public static LoginSession login = LoginSession.getInstance();

	public static HBox getNavbar(String role) {
        HBox navbar = new HBox(10);
        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #778899");

        Button HomeBtn = new Button();
        Button EventsBtn = new Button();
        Button CreateEventBtn = new Button();
        Button InvitationsBtn = new Button();
        Button UsersBtn = new Button();
        Button ProfileBtn = new Button();
        Button ProductsBtn = new Button();
        Button AllEventsBtn = new Button();
        Button LogoutBtn = new Button();
        
        HomeBtn.setText("Home");
        HomeBtn.setOnAction(e -> {
        	RoutingHelper.showHomePage();
        });
        
        EventsBtn.setText("Events");
        EventsBtn.setOnAction(e -> {
        	if(login.getLoggedInUser().getRole().equals("Event Organizer")) Main.switchScene(ViewEventsList.getScene());
        	else RoutingHelper.showEventsListPage();
        });
        
        CreateEventBtn.setText("Create Events");
        CreateEventBtn.setOnAction(e -> {
        	RoutingHelper.showCreateEventPage();
        });
        
        InvitationsBtn.setText("Invitations");
        InvitationsBtn.setOnAction(e -> {
        	RoutingHelper.showInvitationsListPage();
        });
        
        UsersBtn.setText("Users");
        UsersBtn.setOnAction(e -> {
        	RoutingHelper.showAllUsersPage();
        });
        
        ProfileBtn.setText("My Profile");
        ProfileBtn.setOnAction(e -> {
        	RoutingHelper.showProfilePage();
        });
        
        ProductsBtn.setText("Products");
        ProductsBtn.setOnAction(e -> {
        	RoutingHelper.showProductsPage(login.getLoggedInUser().getUserID());
        });
        
        AllEventsBtn.setText("All Events");
        AllEventsBtn.setOnAction(e ->{
        	RoutingHelper.showAllEventsListPage();
        });
        
        LogoutBtn.setText("Logout");
        LogoutBtn.setOnAction(e -> {
        	UserController.logout();
        	RoutingHelper.showLoginPage();
        });
        
        // berupa conditional rendering untuk navbar berdasarkan role user yang sedang login
        if(role.equals("Event Organizer")) {
            navbar.getChildren().addAll(HomeBtn, EventsBtn, CreateEventBtn, ProfileBtn, LogoutBtn);
        } else if (role.equals("Admin")) {
            navbar.getChildren().addAll(HomeBtn, AllEventsBtn, UsersBtn, ProfileBtn, LogoutBtn);
        } else if (role.equals("Vendor")) {
            navbar.getChildren().addAll(HomeBtn, InvitationsBtn, ProductsBtn, EventsBtn, ProfileBtn, LogoutBtn);
        }else if (role.equals("Guest")) {
            navbar.getChildren().addAll(HomeBtn, InvitationsBtn, EventsBtn, ProfileBtn, LogoutBtn);
        }
        
        navbar.getChildren().addAll();
        return navbar;
    }

}
