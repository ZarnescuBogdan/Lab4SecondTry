package com.example.lab4secondtry;

import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoFriendship;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoUser;
import com.example.lab4secondtry.Repository.DbRepo.FriendshipDbRepository;
import com.example.lab4secondtry.Repository.DbRepo.UserDbRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    /**
     * Start application
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException error
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        System.out.println("Reading data from file");
        String username="postgres";
        String pasword="220602";
        String url="jdbc:postgresql://localhost:5432/socialnetwork";
        UserDbRepository userRepo = new UserDbRepository(url, username, pasword);
        FriendshipDbRepository friendshipRepo = new FriendshipDbRepository(url, username, pasword);

        userRepo.findAll().forEach(x -> System.out.println(x));
        friendshipRepo.findAll().forEach(x -> System.out.println(x));

        ActiveRepoUser.getInstance().setRepository(userRepo);
        ActiveRepoFriendship.getInstance().setRepository(friendshipRepo);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setMinWidth(276);
        primaryStage.setMinHeight(135);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}