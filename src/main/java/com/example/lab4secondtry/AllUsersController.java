package com.example.lab4secondtry;

import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AllUsersController implements Initializable {
    UserService userService = new UserService();
    ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, String> tableColumnFirstName;
    @FXML
    TableColumn<User, String> tableColumnLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnFirstName.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        tableColumnLastName.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        initModel();

        tableView.setItems(model);
    }

//    public void setAllUserService(UserService service) {
//        this.service = service;
//        initModel();
//    }

    private void initModel() {
        Iterable<User> messages = userService.getAll();
        List<User> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

}
