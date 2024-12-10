package view.EventOrganizerViews;

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
import model.Vendor;
import view.NavBar;

public class InviteVendor {
	
	public static Scene getScene(String eventID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Invite Vendor to Event");
		titleLbl.setFont(titleFont);
	    
        ObservableList<Vendor> vendors = FXCollections.observableArrayList(VendorController.getUninvitedVendors(eventID));        
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
        	ArrayList<Vendor> selectedVendors = new ArrayList<>();
            for (CheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    selectedVendors.add((Vendor) checkBox.getUserData());
                }
            }

            if (selectedVendors.isEmpty()) {
                Main.displayAlert("Error", "ERROR: You must select at least one vendor to invite!");
            } else {
                for (Vendor vendor : selectedVendors) {
                	EventOrganizerController.inviteToEvent(eventID, vendor.getUserID());
                }
            }

        });
        
	    layout.getChildren().addAll(navbar, titleLbl, vendorListView, inviteBtn);
		return new Scene(layout, 300, 200);
	}
}