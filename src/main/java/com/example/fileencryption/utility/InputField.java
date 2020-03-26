package com.example.fileencryption.utility;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InputField {
    private TextField field;

    public InputField setType(InputType inp) {
        if (inp == InputType.PASSWORD) {
            field = new PasswordField();
        } else if (inp == InputType.TEXT) {
            field = new TextField();
        }
        return this;
    }

    public TextField getField() {
        return field;
    }

    public InputField setCoordinates(double x, double y) {
        field.setLayoutX(x);
        field.setLayoutY(y);
        return this;
    }

    public InputField setText(String txt) {
        field.setText(txt);
        return this;
    }

    public InputField setPrefWidth(double value) {
        field.setPrefWidth(value);
        return this;
    }

    public InputField setFocusTraversable(boolean flag) {
        field.setFocusTraversable(flag);
        return this;

    }

    public InputField setDisable() {
        field.setDisable(true);
        return this;
    }
}
