package view.VendorAndGuestViews;

import controller.EventController;
import controller.InvitationController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.Invitation;
import util.LoginSession;
import view.NavBar;

public class InvitationDetailsPage {
	
	public static LoginSession login = LoginSession.getInstance();
	
	public static Invitation inv;
	public static model.Event ev;
	
	public static Scene getScene(String invID) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());
		
		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		
		Label titleLbl = new Label();
		titleLbl.setText("Invitation Details");
		titleLbl.setFont(titleFont);
		
		layout.getChildren().addAll(navbar, titleLbl);
		
		Label errorLbl = new Label();
		errorLbl.setText("");
	    errorLbl.setVisible(false);
		errorLbl.setStyle("-fx-text-fill: red;");
		
		inv = InvitationController.getInvitationByInvID(invID);
		ev = EventController.viewEventDetails(inv.getEventID());

		GridPane invDetails = new GridPane();
		invDetails.setAlignment(Pos.CENTER);
		
		Label invIdLbl = new Label();
		invIdLbl.setText("Invitation ID: ");
		Label invIdVal = new Label();
		invIdVal.setText(invID);
		
		invDetails.add(invIdLbl, 0, 0);
		invDetails.add(invIdVal, 1, 0);
		
		Label invStatusLbl = new Label();
		invStatusLbl.setText("Invitation Status: ");
		Label invStatusVal = new Label();
		invStatusVal.setText(inv.getStatus());
		
		invDetails.add(invStatusLbl, 0, 1);
		invDetails.add(invStatusVal, 1, 1);
		
		Label invRoleLbl = new Label();
		invRoleLbl.setText("My Role: ");
		Label invRoleVal = new Label();
		invRoleVal.setText(inv.getUserRole());
		
		invDetails.add(invRoleLbl, 0, 2);
		invDetails.add(invRoleVal, 1, 2);
		
		Label evIdLbl = new Label();
		evIdLbl.setText("Event ID: ");
		Label evIdVal = new Label();
		evIdVal.setText(inv.getEventID());
		
		invDetails.add(evIdLbl, 0, 3);
		invDetails.add(evIdVal, 1, 3);
		
		Label evNameLbl = new Label();
		evNameLbl.setText("Event Name: ");
		Label evNameVal = new Label();
		evNameVal.setText(ev.getName());
		
		invDetails.add(evNameLbl, 0, 4);
		invDetails.add(evNameVal, 1, 4);
		
		Label evDateLbl = new Label();
		evDateLbl.setText("Event Date: ");
		Label evDateVal = new Label();
		evDateVal.setText(ev.getDate().toString());
		
		invDetails.add(evDateLbl, 0, 5);
		invDetails.add(evDateVal, 1, 5);
		
		Label evDescLbl = new Label();
		evDescLbl.setText("Event Description: ");
		Label evDescVal = new Label();
		evDescVal.setText(ev.getDescription());
		
		invDetails.add(evDescLbl, 0, 6);
		invDetails.add(evDescVal, 1, 6);
		
		layout.getChildren().add(invDetails);
		
		Label actionLbl = new Label();
		actionLbl.setText("Actions: ");
		layout.getChildren().add(actionLbl);
		
		if(inv.getStatus().equals("Pending")) {			
			Button acceptBtn = new Button();
			acceptBtn.setText("Accept Invitation");
			acceptBtn.setOnAction(e -> {
				InvitationController.acceptInvitation(invID, errorLbl);
			});
			layout.getChildren().add(acceptBtn);
		}
		
		return new Scene(layout, 1600, 900);
	}
}
