package mijnlieff.CompanionClasses.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NicknameCompanion {

    public TextField nicknameTextfield;
    public Button nickNameButton;
    public VBox vBox;

    public void initialize(){
        nicknameTextfield.textProperty().addListener(o -> checkValue());
        nickNameButton.setDisable(true);
    }

    public void checkValue(){
        if (!nicknameTextfield.getText().equals("")){
            vBox.getStyleClass().removeAll("invalid");
        }else {
            vBox.getStyleClass().add("invalid");
        }
    }

    //Onaction button
    public void checkPressed(){

    }

}
