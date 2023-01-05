package com.example.lab4secondtry;

import com.example.lab4secondtry.Domain.*;
import com.example.lab4secondtry.Service.FriendshipService;
import com.example.lab4secondtry.Service.UserService;
import com.example.lab4secondtry.Utils.MessageAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.util.stream.StreamSupport;

public class RequestsController implements Initializable {
    UserService userService = new UserService();
    FriendshipService friendshipService = new FriendshipService();
    ObservableList<Wrapper> modelFinal = FXCollections.observableArrayList();

    @FXML
    TableView<Wrapper> tableView;
    @FXML
    TableColumn<Wrapper, String> tableColumnFirstName;
    @FXML
    TableColumn<Wrapper, String> tableColumnLastName;
    @FXML
    TableColumn<Wrapper, Date> tableColumnDate;

    /**
     * Initialize override of request-view scene
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

        tableColumnFirstName.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnLastName.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnDate.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        initModel();

        tableView.setItems(modelFinal);
    }

    /**
     * Model initializer
     */
    private void initModel() {
        Iterable<Friendship> messages = friendshipService.getAll();
        List<Friendship> friendships = StreamSupport.stream(messages.spliterator(), false)
                .toList();
        List<Wrapper> pending = new ArrayList<>();

        User activeUser = ActiveUser.getInstance().getUser();

        for (Friendship f : friendships) {

            if (f.getSecondUserId() == activeUser.getId() && f.getStatus().equals("unaccepted") ) {
                Optional<User> optionalUser = userService.findById(f.getFirstUserId());
                if (optionalUser.isPresent()) {
                    Wrapper wrapper = new Wrapper(optionalUser.get().getFirstName(),
                            optionalUser.get().getLastName(), f.getDate());
                    pending.add(wrapper);
                }
            }
        }
        modelFinal.setAll(pending);
    }

    /**
     * Accept button clicked
     * @param event event
     */
    public void acceptClicked(ActionEvent event) {
        try {
            ObservableList<Wrapper> selectedUser = tableView.getSelectionModel().getSelectedItems();
            if (!selectedUser.isEmpty()) {
                Wrapper wrapper = selectedUser.get(0);
                User user = new User(wrapper.getFirstName(), wrapper.getLastName());
                User userInRepo = userService.find(user);

                Friendship friendship = new Friendship(userInRepo.getId(),
                        ActiveUser.getInstance().getUser().getId(),
                        "unaccepted", new Date(System.currentTimeMillis()));

                Friendship friendshipInRepo = friendshipService.find(friendship);
                friendshipInRepo.setStatus("accepted");

                friendshipService.update(friendshipInRepo);
                initModel();
            } else {
                MessageAlert.showErrorMessage(null, "There was no friend request selected!");
                return;
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Accept", "Friend request successfully added!");
    }

    /**
     * Delete button clicked
     * @param event event
     */
    public void deleteClicked(ActionEvent event) {
        try {
            ObservableList<Wrapper> selectedUser = tableView.getSelectionModel().getSelectedItems();
            if (!selectedUser.isEmpty()) {
                Wrapper wrapper = selectedUser.get(0);
                User user = new User(wrapper.getFirstName(), wrapper.getLastName());
                User userInRepo = userService.find(user);

                Friendship friendship = new Friendship(userInRepo.getId(),
                        ActiveUser.getInstance().getUser().getId(),
                        "unaccepted", new Date(System.currentTimeMillis()));
                Friendship friendshipInRepo = friendshipService.find(friendship);

                friendshipService.delete(friendshipInRepo.getId());
                initModel();
            } else {
                MessageAlert.showErrorMessage(null, "There was no friend request selected!");
                return;
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Friend request successfully deleted!");
    }
}
