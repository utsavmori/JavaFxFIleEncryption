package com.example.fileencryption.utility;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CustomButton {
    private Button btn;
    public Button getButton(){
        return  btn;
    }
    public CustomButton createButton(){
        btn=new Button();
        return  this;
    }

    public CustomButton setCoordinates(double x, double y) {
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        return this;
    }
    public CustomButton setText(String text){
        btn.setText(text);
        return this;
    }
    public CustomButton setDisable() {
        btn.setDisable(true);
        return this;
    }

}
