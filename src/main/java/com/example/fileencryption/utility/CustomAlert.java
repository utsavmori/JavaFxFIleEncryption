package com.example.fileencryption.utility;

import javafx.scene.control.Alert;

public class CustomAlert {
    private Alert alert;

    public CustomAlert createAlert(Alert.AlertType type) {
        alert = new Alert(type);
        return this;
    }

    public Alert getAlert() {
        return alert;
    }

    public CustomAlert setTitle(String title) {
        alert.setTitle(title);
        return this;
    }

    public CustomAlert setHeaderText(String text) {
        alert.setHeaderText(text);
        return this;
    }

    public CustomAlert setContentText(String text) {
        alert.setContentText(text);
        return this;
    }
}
