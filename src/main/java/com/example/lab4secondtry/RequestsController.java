package com.example.lab4secondtry;

import com.example.lab4secondtry.Domain.ActiveUser;
import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Domain.ValidationException;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoFriendship;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestsController implements Initializable {
    UserService userService = new UserService();
    FriendshipService friendshipService = new FriendshipService();
    ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, String> tableColumnFirstName;
    @FXML
    TableColumn<User, String> tableColumnLastName;
    @FXML
    TableColumn<Friendship, String> tableColumnDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnFirstName.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnLastName.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnDate.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Friendship, String>("date"));

        initModel();

        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Friendship> messages = friendshipService.getAll();
        List<Friendship> friendships = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        List<User> pendingFriends = new ArrayList<>();
        List<Date> pendingDates = new ArrayList<>();

        User activeUser = ActiveUser.getInstance().getUser();

        for (Friendship f : friendships) {

            Iterable<User> messages1 = userService.getAll();
            List<User> users = StreamSupport.stream(messages1.spliterator(), false)
                    .collect(Collectors.toList());

            if (f.getSecondUserId() == activeUser.getId() && f.getStatus().equals("unaccepted") ) {
                Optional<User> optionalUser = userService.findById(f.getFirstUserId());
                pendingFriends.add(optionalUser.get());
                pendingDates.add(f.getDate());
            }
        }
        model.setAll(pendingFriends);
    }

    public void acceptClicked(ActionEvent event) {
        try {
            ObservableList<User> selectedUser = tableView.getSelectionModel().getSelectedItems();
            if (!selectedUser.isEmpty()) {
                User user = selectedUser.get(0);
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

    public void deleteClicked(ActionEvent event) {
        try {
            ObservableList<User> selectedUser = tableView.getSelectionModel().getSelectedItems();
            if (!selectedUser.isEmpty()) {
                User user = selectedUser.get(0);
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
