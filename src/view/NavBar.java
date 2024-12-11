package view;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.Main;
import view.AdminViews.ViewAllEventsPage;
import view.AdminViews.ViewAllUsersPage;
import view.VendorAndGuestViews.ViewInvitationsPage;
import view.VendorViews.MyProductsPage;

public class NavBar {
	public static HBox getNavbar(String role) {
        HBox navbar = new HBox(10);
        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #2c3e50;");

        Button HomeBtn = new Button();
        Button EventsBtn = new Button();
        Button CreateEventBtn = new Button();
        Button InvitationsBtn = new Button();
        Button UsersBtn = new Button();
        Button ProfileBtn = new Button();
        Button ProductsBtn = new Button();
        
        HomeBtn.setText("Home");
        HomeBtn.setOnAction(e -> {
        	Main.switchScene(Home.getScene());
        });
        
        EventsBtn.setText("Events");
        EventsBtn.setOnAction(e -> {
        	if(Main.currentUser.getRole().equals("Event Organizer")) Main.switchScene(ViewEventsList.getScene());
        	else Main.switchScene(ViewEventsList.getScene());
        });
        
        CreateEventBtn.setText("Create Events");
        CreateEventBtn.setOnAction(e -> {
        	Main.switchScene(CreateEventPage.getScene());
        });
        
        InvitationsBtn.setText("Invitations");
        InvitationsBtn.setOnAction(e -> {
        	Main.switchScene(ViewInvitationsPage.getScene());
        });
        
        UsersBtn.setText("Users");
        UsersBtn.setOnAction(e -> {
        	Main.switchScene(ViewAllUsersPage.getScene());
        });
        
        ProfileBtn.setText("My Profile");
        ProfileBtn.setOnAction(e -> {
        	Main.switchScene(MyProfile.getScene());
        });
        
        ProductsBtn.setText("Products");
        ProductsBtn.setOnAction(e -> {
        	Main.switchScene(MyProductsPage.getScene(Main.currentUser.getUserID()));
        });
        
        Button AllEventsBtn = new Button();
        AllEventsBtn.setText("All Events");
        AllEventsBtn.setOnAction(e ->{
        	Main.switchScene(ViewAllEventsPage.getScene());
        });
        
        if(role.equals("Event Organizer")) {
            navbar.getChildren().addAll(HomeBtn, EventsBtn, CreateEventBtn, ProfileBtn);
        } else if (role.equals("Admin")) {
            navbar.getChildren().addAll(HomeBtn, AllEventsBtn, UsersBtn, ProfileBtn);
        } else if (role.equals("Vendor")) {
            navbar.getChildren().addAll(HomeBtn, InvitationsBtn, ProductsBtn, EventsBtn, ProfileBtn);
        }else if (role.equals("Guest")) {
            navbar.getChildren().addAll(HomeBtn, InvitationsBtn, EventsBtn, ProfileBtn);
        }
        
        navbar.getChildren().addAll();
        return navbar;
    }

}
