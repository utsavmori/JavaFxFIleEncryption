package com.example.fileencryption;

import com.example.fileencryption.utility.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainClass extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        AtomicReference<List<File>> listOfFiles = new AtomicReference<>();

        Text hashGenerationLabel = new CustomLabel().generateLabel().setCoordinates(20, 20).setFont(22).setText("Hash Generation").getText();
        Text enterPasswordLabel = new CustomLabel().generateLabel().setCoordinates(50, 60).setFont(15).setText("Enter Password").getText();
        TextField passwordField = new InputField().setType(InputType.TEXT).setCoordinates(180, 42).setPrefWidth(280).setFocusTraversable(true).getField();
        TextField hashField = new InputField().setType(InputType.TEXT).setDisable().setCoordinates(180, 70).setPrefWidth(280).getField();
        Text hashLabel = new CustomLabel().generateLabel().setCoordinates(50, 90).setFont(15).setText("Hash").getText();
        Text encryptionLabel = new CustomLabel().generateLabel().setCoordinates(20, 150).setFont(22).setText("Encryption").getText();
        Text encryptionNoteLabel = new CustomLabel().generateLabel().setCoordinates(140, 150).setFont(12).setText("Below Button Encrypts all selected files").getText();
        Text decryptionLabel = new CustomLabel().generateLabel().setCoordinates(20, 230).setFont(22).setText("Decryption").getText();
        Text decryptionNoteLabel = new CustomLabel().generateLabel().setCoordinates(140, 230).setFont(12).setText("Below Button Decrypts all selected files").getText();
        Button encryptButton = new CustomButton().createButton().setCoordinates(300, 170).setText("Encrypt Now").setDisable().getButton();
        Button decryptButton = new CustomButton().createButton().setCoordinates(300, 250).setText("Decrypt Now").setDisable().getButton();
        Button hashGenBtn = new CustomButton().createButton().setCoordinates(480, 42).setText("Generate Hash").getButton();
        Button browseBtn = new CustomButton().createButton().setCoordinates(300, 105).setText("Browse Files").getButton();

        browseBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();
                        listOfFiles.set(fileChooser.showOpenMultipleDialog(primaryStage));

                        if(listOfFiles.get().stream().map(x->x.getName()).allMatch(x->x.substring(x.length()-3).equals("enc"))){
                            decryptButton.setDisable(false);
                            encryptButton.setDisable(true);
                        }else{
                            decryptButton.setDisable(true);
                            encryptButton.setDisable(false);
                        }
                    }
                });


        hashGenBtn.setOnAction(value -> {
            try {
                hashField.setText(Hashing.GenerateSHA256(passwordField.getText()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });


        encryptButton.setOnAction(value -> {
            try {
                String hsh = Hashing.GenerateSHA256(passwordField.getText());
                hashField.setText(hsh);
                if (listOfFiles.get() == null) {
                    new CustomAlert().createAlert(Alert.AlertType.ERROR).setTitle("Encryption Error")
                            .setHeaderText("Files Not Selected").getAlert().showAndWait();
                }
                boolean x = Aes.Encrypt(hsh, listOfFiles.get());
                if (x == true) {
                    new CustomAlert().createAlert(Alert.AlertType.INFORMATION).setTitle("Encryption")
                            .setHeaderText("Encryption Completed").getAlert().showAndWait();

                } else {

                    new CustomAlert().createAlert(Alert.AlertType.ERROR).setTitle("Encryption")
                            .setHeaderText("Encryption not Completed").getAlert().showAndWait();

                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        });


        decryptButton.setOnAction(value -> {
            try {
                String hsh = Hashing.GenerateSHA256(passwordField.getText());
                hashField.setText(hsh);
                try {
                    if (listOfFiles.get() == null) {
                        new CustomAlert().createAlert(Alert.AlertType.ERROR).setTitle("Decryption Error")
                                .setHeaderText("Files Not Selected").getAlert().showAndWait();
                    }
                    boolean x = Aes.Decrypt(hsh, listOfFiles.get());
                    if (x == true) {
                        new CustomAlert().createAlert(Alert.AlertType.INFORMATION).setTitle("Decryption")
                                .setHeaderText("Decryption Completed").getAlert().showAndWait();

                    } else {
                        new CustomAlert().createAlert(Alert.AlertType.ERROR).setTitle("Decryption")
                                .setHeaderText("Decryption not Completed").getAlert().showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        });


        //creating a Group object
        Group root = new Group();

        ObservableList list = root.getChildren();
        list.addAll(hashGenerationLabel, enterPasswordLabel, passwordField, hashGenBtn, hashLabel, encryptionLabel, encryptionNoteLabel, decryptionLabel, decryptionNoteLabel, hashField, encryptButton, decryptButton, browseBtn);


        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(root, 700, 400);

        //setting color to the scene
        scene.setFill(Color.LIGHTGRAY);

        //Setting the title to Stage.
        primaryStage.setTitle("File Encrypter");

        //Adding the scene to Stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }
}
