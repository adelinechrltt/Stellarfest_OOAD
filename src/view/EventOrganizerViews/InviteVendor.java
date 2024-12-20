package view.EventOrganizerViews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.EventOrganizerController;
import controller.VendorController;
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
import model.Invitation;
import model.Vendor;
import util.LoginSession;
import view.NavBar;

public class InviteVendor {
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static Scene getScene(String eventID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Invite Vendor to Event");
		titleLbl.setFont(titleFont);
	    
        ObservableList<Vendor> vendors = FXCollections.observableArrayList(EventOrganizerController.checkAddVendorInput(eventID));        
        ListView<CheckBox> vendorListView = new ListView<>();
        ObservableList<CheckBox> checkBoxList = FXCollections.observableArrayList();
        
        for (Vendor vendor : vendors) {
            CheckBox checkBox = new CheckBox(vendor.getName());
            checkBox.setUserData(vendor);
            checkBoxList.add(checkBox);
        }
        
        vendorListView.setItems(checkBoxList);
	    
        Button inviteBtn = new Button();
        inviteBtn.setText("Invite Vendors");
        inviteBtn.setOnAction(e -> {
			 // mendapatkan daftar vendor yang sudah di select berdasarkan checkbox
        	ArrayList<Vendor> selectedVendors = new ArrayList<>();
            for (CheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    selectedVendors.add((Vendor) checkBox.getUserData());
                }
            }

			 // cek apabila ada vendor yang diselect berdasarkan checkbox
			 // apabila ada, maka vendor  tersebut akan di-delete
            if (selectedVendors.isEmpty()) {
                Main.displayAlert("Error", "ERROR: You must select at least one vendor to invite!");
            } else {
                for (Vendor vendor : selectedVendors) {
                	EventOrganizerController.inviteToEvent(eventID, vendor.getEmail());
                }
            }

        });
        
	    layout.getChildren().addAll(navbar, titleLbl, vendorListView, inviteBtn);
		return new Scene(layout, 1200, 600);
	}
}
