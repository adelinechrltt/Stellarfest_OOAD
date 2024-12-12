package view.VendorAndGuestViews;

import java.sql.Date;
import java.util.ArrayList;

import controller.EventController;
import controller.InvitationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Invitation;
import view.NavBar;
import view.ViewEventDetails;

public class ViewInvitationsPage {
	
	public static String invID;
	private static int clickCount = 0;
	
	public static Scene getScene() {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("My Invitations");
		titleLbl.setFont(titleFont);
		
		layout.getChildren().addAll(navbar, titleLbl);

		ArrayList<Invitation> invites = InvitationController.getPendingInvsByEmail(Main.currentUser.getEmail());
		if(invites.isEmpty() || invites == null) {
			Label nullDisplay = new Label();
			nullDisplay.setText("No invitations found!");
			layout.getChildren().add(nullDisplay);
		} else {
			Label subtitleLbl = new Label();
			subtitleLbl.setText("Double-click on an entry for more actions.");
			titleLbl.setFont(inputFont);
			
			ObservableList<Invitation> invitations = FXCollections.observableArrayList(invites);
			TableView<Invitation> invitesView = new TableView<>();
		     
			TableColumn<Invitation, String> invIdCol = new TableColumn<>("Invitation ID");
		    invIdCol.setCellValueFactory(new PropertyValueFactory<>("InvitationID"));
		     
		    TableColumn<Invitation, String> evIdCol = new TableColumn<>("Event ID");
		    evIdCol.setCellValueFactory(new PropertyValueFactory<>("eventID"));
		    
		    TableColumn<Invitation, String> evNameCol = new TableColumn<>("Event Name");
		    evNameCol.setCellValueFactory(cellData -> {
		        String evID = cellData.getValue().getEventID();
		        String name = EventController.viewEventDetails(evID).getName();
		        return new SimpleStringProperty(name);
		    });

		    TableColumn<Invitation, String> evDateCol = new TableColumn<>("Event Date");
		    evDateCol.setCellValueFactory(cellData -> {
		        String evID = cellData.getValue().getEventID();
		        Date date = EventController.viewEventDetails(evID).getDate();
		        return new SimpleStringProperty(date.toString());
		    });
		    
		    TableColumn<Invitation, String> evDescCol = new TableColumn<>("Event Description");
		    evDescCol.setCellValueFactory(cellData -> {
		        String evID = cellData.getValue().getEventID();
		        String desc = EventController.viewEventDetails(evID).getDescription();
		        return new SimpleStringProperty(desc);
		    });
		    
		    TableColumn<Invitation, String> invStatusCol = new TableColumn<>("Invitation Status");
		    invStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		    
		    invitesView.getColumns().addAll(invIdCol, evIdCol, evNameCol, evDateCol, evDescCol, invStatusCol);
		    invitesView.setItems(invitations);
		    
		    invitesView.setOnMouseClicked(e -> {
		    	 TableSelectionModel<Invitation> invModel = invitesView.getSelectionModel();
		    	 invModel.setSelectionMode(SelectionMode.SINGLE);
		    	 Invitation inv = invModel.getSelectedItem();
		    	 invID = inv.getInvitationID();
		    	 
		    	 clickCount++;
		    	 if(clickCount == 2) {
		    		 Main.switchScene(InvitationDetailsPage.getScene(invID));
		    		 clickCount = 0;
		    	 }
		     });
		    
		    layout.getChildren().addAll(subtitleLbl, invitesView);
		}
		
		return new Scene(layout, 1600, 900);
	}
}
