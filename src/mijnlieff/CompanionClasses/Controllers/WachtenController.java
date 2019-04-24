package mijnlieff.CompanionClasses.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WachtenController {

    @FXML
    private Label textLabel;

    private String text;

    public WachtenController(String text){
        this.text = text;
    }

    public void initialize(){
        textLabel.setText(text);
    }

}
