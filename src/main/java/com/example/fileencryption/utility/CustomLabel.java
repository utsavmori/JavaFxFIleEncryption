package com.example.fileencryption.utility;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomLabel {
    private Text text;

    public CustomLabel generateLabel() {
        text = new Text();
        return this;
    }

    public CustomLabel setFont(int fontSize) {
        text.setFont(new Font(fontSize));
        return this;
    }

    public CustomLabel setCoordinates(double x, double y) {
        text.setX(x);
        text.setY(y);
        return this;
    }

    public Text getText() {
        return text;
    }

    public CustomLabel setText(String txt) {
        text.setText(txt);
        return this;
    }
}
