package view.AdminViews;

import java.sql.Date;
import java.util.ArrayList;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import util.LoginSession;
import view.NavBar;
import view.ViewEventDetails;

public class ViewAllUsersPage {
	public static LoginSession login = LoginSession.getInstance();

	public static String tempID;
	private static int clickCount = 0;

	public static Scene getScene() {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

		HBox navbar = NavBar.getNavbar(login.getLoggedInUser().getRole());

		Font titleFont = Font.font("Microsoft Sans Serif", FontWeight.BOLD, 26);
		Font inputFont = Font.font("Microsoft Sans Serif", FontWeight.MEDIUM, 17);

		Label titleLbl = new Label();
		titleLbl.setText("All Users");
		titleLbl.setFont(titleFont);

		layout.getChildren().addAll(navbar, titleLbl);

		ArrayList<User> users = new ArrayList<>();
		users = AdminController.getAllUsers();

		if (users == null || users.isEmpty()) {
			Label nullDisplay = new Label();
			nullDisplay.setText("No users!");
			layout.getChildren().add(nullDisplay);
		} else {
			Label subtitleLbl = new Label();
			subtitleLbl.setText("Ctrl+click to select multiple rows.");
			titleLbl.setFont(inputFont);

			ObservableList<User> usersList = FXCollections.observableArrayList(users);
			ListView<CheckBox> usersView = new ListView<>();
			ObservableList<CheckBox> checkBoxList = FXCollections.observableArrayList();

			for (User user : usersList) {
				CheckBox checkBox = new CheckBox(
						"ID: " + user.getUserID() + " | " + user.getName() + " (" + user.getRole() + ")");
				checkBox.setUserData(user);
				checkBoxList.add(checkBox);
			}

			usersView.setItems(checkBoxList);

			Button deleteBtn = new Button();
			deleteBtn.setText("Delete Events");
			deleteBtn.setOnAction(event -> {
				ArrayList<User> selectedUsers = new ArrayList<>();
				for (CheckBox checkBox : checkBoxList) {
					if (checkBox.isSelected()) {
						selectedUsers.add((User) checkBox.getUserData());
					}
				}

				if (selectedUsers.isEmpty()) {
					Main.displayAlert("Error", "ERROR: You must select at least one event to delete!");
				} else {
					for (User user : selectedUsers) {
						selectedUsers.remove(user);
						AdminController.deleteEvent(user.getUserID());
					}
				}
			});

			layout.getChildren().addAll(subtitleLbl, usersView, deleteBtn);
		}

		return new Scene(layout, 1200, 600);
	}
}
