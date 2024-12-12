package view.AdminViews;

import java.sql.Date;
import java.util.ArrayList;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.Event;
import model.User;
import view.NavBar;
import view.ViewEventDetails;

public class ViewAllEventsPage {
	public static String tempID; 
	private static int clickCount = 0;
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label titleLbl = new Label();
		 titleLbl.setText("All Events");
		 titleLbl.setFont(titleFont);
		 		 
		 layout.getChildren().addAll(navbar, titleLbl);

		 ArrayList<model.Event> events = new ArrayList<>();
		 events = AdminController.viewAllEvents();
		 
		 if(events==null || events.isEmpty()) {
			 Label nullDisplay = new Label();
			 nullDisplay.setText("No events!");
			 layout.getChildren().add(nullDisplay);
		 } else {			 
			 Label subtitleLbl = new Label();
			 subtitleLbl.setText("Double-click on an entry for more actions.\nCtrl+Click on rows to select multiple entries.");
			 titleLbl.setFont(inputFont);

			 ObservableList<model.Event> evs = FXCollections.observableArrayList(events);
			 
			 TableView<model.Event> viewMyEvents = new TableView<>();
		     
			 TableColumn<model.Event, String> idCol = new TableColumn<>("Event ID");
		     idCol.setCellValueFactory(new PropertyValueFactory<>("eventID"));
		     
		     TableColumn<model.Event, String> nameCol = new TableColumn<>("Name");
		     nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		     
		     TableColumn<model.Event, Date> dateCol = new TableColumn<>("Date (YYYY-MM-DD)");
		     dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		     
		     TableColumn<model.Event, String> locCol = new TableColumn<>("Location");
		     locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
			 
		     TableColumn<model.Event, String> descCol = new TableColumn<>("Description");
		     descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		     
		     viewMyEvents.getColumns().addAll(idCol, nameCol, dateCol, locCol, descCol);
		     viewMyEvents.setItems(evs);
		     viewMyEvents.setOnMouseClicked(e -> {
		    	 TableSelectionModel<model.Event> tsModel = viewMyEvents.getSelectionModel();
		    	 tsModel.setSelectionMode(SelectionMode.SINGLE);
		    	 model.Event ev = tsModel.getSelectedItem();
		    	 tempID = ev.getEventID();
		    	 
		    	 clickCount++;
		    	 if(clickCount == 2) {
		    		 Main.switchScene(ViewEventDetails.getScene(tempID));
		    		 clickCount = 0;
		    	 }
		     });
		     
		     ArrayList<Event> toDelete = new ArrayList<>();
		     viewMyEvents.setOnMouseClicked(event -> {
		    	    if (event.getClickCount() == 1 && event.isControlDown()) {
		    	        TableSelectionModel<model.Event> evModel = viewMyEvents.getSelectionModel();
		    	        model.Event selected = evModel.getSelectedItem();
		    	        toDelete.add(selected);
		    	    }
		    	});
		     
            Button deleteBtn = new Button();
            deleteBtn.setText("Delete Users");
            deleteBtn.setOnAction(event -> {
                for (Event ev : toDelete) {
                    evs.remove(ev);
                    AdminController.deleteEvent(ev.getEventID());
                }
            });
		     
		     layout.getChildren().addAll(subtitleLbl, viewMyEvents, deleteBtn);
		 }
	     
		 return new Scene(layout, 1600, 900);	
	}
}
