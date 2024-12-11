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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import model.User;
import view.NavBar;

public class ViewAllUsersPage {
	public static String tempID; 
	private static int clickCount = 0;
	
	public static Scene getScene() {
		 VBox layout = new VBox(10);
		 layout.setAlignment(Pos.CENTER);
		 
		 HBox navbar = NavBar.getNavbar(Main.currentUser.getRole());
		 
		 Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		 Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);
		 
		 Label titleLbl = new Label();
		 titleLbl.setText("All Users");
		 titleLbl.setFont(titleFont);
		 		 
		 layout.getChildren().addAll(navbar, titleLbl);

		 ArrayList<User> users = new ArrayList<>();
		 users = AdminController.viewAllUsers();
		 
		 if(users==null || users.isEmpty()) {
			 Label nullDisplay = new Label();
			 nullDisplay.setText("No users!");
			 layout.getChildren().add(nullDisplay);
		 } else {			 
			 Label subtitleLbl = new Label();
			 subtitleLbl.setText("Ctrl+click to select multiple rows.");
			 titleLbl.setFont(inputFont);

			 ObservableList<User> usersList = FXCollections.observableArrayList(users);
			 
			 TableView<User> usersView = new TableView<>();
		     
			 TableColumn<User, String> idCol = new TableColumn<>("User ID");
		     idCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
		     
		     TableColumn<User, String> nameCol = new TableColumn<>("Name");
		     nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		     
		     TableColumn<User, String> emailCol = new TableColumn<>("Email");
		     emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			 
		     TableColumn<User, String> roleCol = new TableColumn<>("Role");
		     roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
		     
		     usersView.getColumns().addAll(idCol, nameCol, emailCol, roleCol);
		     usersView.setItems(usersList);
		     usersView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		     
		     ArrayList<User> toDelete = new ArrayList<>();
	         usersView.setOnMouseClicked(event -> {
	                if (event.getClickCount() % 2 == 1) {
	                    toDelete.add((User)usersView.getSelectionModel().getSelectedItem());
	                }
	            });
		     
            Button deleteBtn = new Button();
            deleteBtn.setText("Delete Users");
            deleteBtn.setOnAction(event -> {
                for (User user : toDelete) {
                    usersList.remove(user);
                    AdminController.deleteUser(user.getUserID());
                }
            });
	            
		     layout.getChildren().addAll(subtitleLbl, usersView, deleteBtn);
		 }
	     
		 return new Scene(layout, 300, 200);	
	}
}
