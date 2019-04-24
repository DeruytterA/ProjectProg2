package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NicknameCompanion extends MyController {

    public TextField nicknameTextfield;
    public Button nickNameButton;
    public VBox vBox;

    public void initialize(){
        nicknameTextfield.textProperty().addListener(o -> checkValue());
        nickNameButton.setDisable(true);
    }

    public void invalidated(Observable observable){

    }

    public void checkValue(){
        if (!nicknameTextfield.getText().equals("")){
            vBox.getStyleClass().removeAll("invalid");
        }else {
            vBox.getStyleClass().add("invalid");
        }
        nickNameButton.setDisable(nicknameTextfield.getText().equals(""));
    }

    //Onaction button
    public void checkPressed(){
        model.serverNickname(nicknameTextfield.getText());
    }

    public String getTextField(){
        return nicknameTextfield.getText();
    }
}
