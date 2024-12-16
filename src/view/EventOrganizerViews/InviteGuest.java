package view.EventOrganizerViews;

import java.util.ArrayList;

import controller.EventOrganizerController;
import controller.GuestController;
import controller.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Guest;
import util.LoginSession;
import view.NavBar;

public class InviteGuest {
	
	public static LoginSession login = LoginSession.getInstance();

	public static Scene getScene(String eventID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Invite Guest to Event");
		titleLbl.setFont(titleFont);
	    
        ObservableList<Guest> guests = FXCollections.observableArrayList(EventOrganizerController.checkAddGuestInput(eventID));        
        ListView<CheckBox> GuestListView = new ListView<>();
        ObservableList<CheckBox> checkBoxList = FXCollections.observableArrayList();
        
        for (Guest guest : guests) {
            CheckBox checkBox = new CheckBox(guest.getName());
            checkBox.setUserData(guest);
            checkBoxList.add(checkBox);
        }
        
        GuestListView.setItems(checkBoxList);
	    
        Button inviteBtn = new Button();
        inviteBtn.setText("Invite Guests");
        inviteBtn.setOnAction(e -> {
        	ArrayList<Guest> selectedGuests = new ArrayList<>();
            for (CheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    selectedGuests.add((Guest) checkBox.getUserData());
                }
            }

            if (selectedGuests.isEmpty()) {
                Main.displayAlert("Error", "ERROR: You must select at least one guest to invite!");
            } else {
                for (Guest Guest : selectedGuests) {
                	EventOrganizerController.inviteToEvent(eventID, Guest.getEmail());
                }
            }

        });
        
	    layout.getChildren().addAll(navbar, titleLbl, GuestListView, inviteBtn);
		return new Scene(layout, 1200, 600);
	}
}
