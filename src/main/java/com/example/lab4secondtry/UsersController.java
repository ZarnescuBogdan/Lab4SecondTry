package com.example.lab4secondtry;

import com.example.lab4secondtry.Domain.ActiveUser;
import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Domain.ValidationException;
import com.example.lab4secondtry.Service.FriendshipService;
import com.example.lab4secondtry.Service.UserService;
import com.example.lab4secondtry.Utils.MessageAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.StreamSupport;

public class UsersController implements Initializable {
    UserService userService = new UserService();
    FriendshipService friendshipService = new FriendshipService();
    ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    TextField firstNameInputAddFr;
    @FXML
    TextField lastNameInputAddFr;
    @FXML
    Button friendRequestButton;
    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, String> tableColumnFirstName;
    @FXML
    TableColumn<User, String> tableColumnLastName;

    /**
     * Initialize override of users-view scene
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnFirstName.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        tableColumnLastName.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        initModel();

        tableView.setItems(model);
    }

    /**
     * Model initializer
     */
    private void initModel() {
        Iterable<Friendship> messages = friendshipService.getAll();
        List<Friendship> friendships = StreamSupport.stream(messages.spliterator(), false)
                .toList();
        List<User> friends = new ArrayList<>();

        User activeUser = ActiveUser.getInstance().getUser();

        for (Friendship f : friendships) {

            if (f.getFirstUserId() == activeUser.getId() && f.getStatus().equals("accepted") ) {
                Optional<User> optionalUser = userService.findById(f.getSecondUserId());
                optionalUser.ifPresent(friends::add);
            }
            else if (f.getSecondUserId() == activeUser.getId() && f.getStatus().equals("accepted") ) {
                Optional<User> optionalUser = userService.findById(f.getFirstUserId());
                optionalUser.ifPresent(friends::add);
            }
        }
        model.setAll(friends);
    }

    /**
     * Friend request button clicked
     * @param event event
     */
    public void friendRequestClicked(ActionEvent event) {
        try {
            User user = new User(firstNameInputAddFr.getText(), lastNameInputAddFr.getText());
            User userInRepo = userService.find(user);

            userService.sendFriendRequest(ActiveUser.getInstance().getUser(), userInRepo);

        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Friend request succesffully sent!");
    }

    /**
     * Delete button clicked
     * @param event event
     */
    public void deleteClicked(ActionEvent event) {
        try {
            ObservableList<User> selectedUser = tableView.getSelectionModel().getSelectedItems();
            if (!selectedUser.isEmpty()) {
                User user = selectedUser.get(0);
                User userInRepo = userService.find(user);

                Friendship friendship = new Friendship(ActiveUser.getInstance().getUser().getId(), userInRepo.getId(), "accepted", new Date(System.currentTimeMillis()));
                Friendship friendshipInRepo = friendshipService.find(friendship);

                friendshipService.delete(friendshipInRepo.getId());
                initModel();
            } else {
                MessageAlert.showErrorMessage(null, "There was no user selected!");
                return;
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Friend successfully removed!");
    }

    /**
     * Pending request button clicked
     * @param event event
     */
    public void pendingRequestsClicked(ActionEvent event) {
        try {

            Stage window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(UsersController.class.getResource("requests-view.fxml"));
            AnchorPane usersLayout = fxmlLoader.load();
            Scene scene = new Scene(usersLayout);

            window.setOnCloseRequest(e -> initModel());

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Friend requests");
            window.setMinWidth(680);
            window.setMinHeight(450);


            //Display window and wait for it to be closed before returning
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
