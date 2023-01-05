package com.example.lab4secondtry.Utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    /**
     * Print message
     * @param owner stage to be printed
     * @param type type of message
     * @param header header of message
     * @param text message text
     */
    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    /**
     * Print error message
     * @param owner stage to be printed
     * @param text error message
     */
    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}

