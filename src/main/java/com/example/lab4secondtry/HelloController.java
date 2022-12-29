package com.example.lab4secondtry;

import com.example.lab4secondtry.Domain.ActiveUser;
import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Domain.ValidationException;
import com.example.lab4secondtry.Service.FriendshipService;
import com.example.lab4secondtry.Service.UserService;
import com.example.lab4secondtry.Utils.MessageAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {
    UserService userService = new UserService();
    FriendshipService friendshipService = new FriendshipService();
    @FXML
    Button showExistingUsers;
    @FXML
    Button loginButton;

    @FXML
    TextField firstNameInput;
    @FXML
    TextField lastNameInput;

//    public void setUserService(UserService service) {
//        this.service = service;
//    }

    @FXML
    void handleLoginClicked(ActionEvent actionEvent) {
        try {
            User user = new User(firstNameInput.getText(), lastNameInput.getText());
            User userInRepo = userService.find(user);

            ActiveUser.getInstance().setUser(userInRepo);
            friendshipService.getAllFriendsUser();

            Stage window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(UsersController.class.getResource("users-view.fxml"));
            AnchorPane usersLayout = fxmlLoader.load();
            Scene scene = new Scene(usersLayout);

//            UsersController usersController = fxmlLoader.getController();
//            usersController.setService(service);
//            usersController.initModel();



            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Users");
            window.setMinWidth(680);
            window.setMinHeight(450);


            //Display window and wait for it to be closed before returning
            window.setScene(scene);
            window.showAndWait();
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showExistingUsersClicked(ActionEvent event) {
        try {
            Stage window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(UsersController.class.getResource("allUsers-view.fxml"));
            AnchorPane allUsersLayout = fxmlLoader.load();
            Scene scene = new Scene(allUsersLayout);

//            AllUsersController allUsersController = fxmlLoader.getController();
//            allUsersController.setAllUserService(service);
//            allUsersController.initModel();

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Users");
            window.setMinWidth(450);
            window.setMinHeight(450);

            //Display window and wait for it to be closed before returning
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void signupClicked(ActionEvent event) {
        try {
            User user = new User(firstNameInput.getText(), lastNameInput.getText());
            userService.add(user);

            ActiveUser.getInstance().setUser(user);

            Stage window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(UsersController.class.getResource("users-view.fxml"));
            AnchorPane usersLayout = fxmlLoader.load();
            Scene scene = new Scene(usersLayout);

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Users");
            window.setMinWidth(450);
            window.setMinHeight(450);


            //Display window and wait for it to be closed before returning
            window.setScene(scene);
            window.showAndWait();
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}