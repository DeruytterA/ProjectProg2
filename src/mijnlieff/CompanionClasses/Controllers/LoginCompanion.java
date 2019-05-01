package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginCompanion extends MyController{

    public VBox vbox;
    public TextField serverNaamTextField;
    public TextField serverPoortTextField;
    public Button loginButton;

    private BooleanProperty buttonDisabled;

    public void initialize(){
        loginButton.setDefaultButton(true);
        buttonDisabled = new SimpleBooleanProperty();
        buttonDisabled.setValue(true);
        TextField[] textFields = new TextField[] {
            serverNaamTextField, serverPoortTextField
        };

        for (TextField field : textFields) {
            field.textProperty().addListener(o -> checkValues());
        }
        loginButton.disableProperty().bind(buttonDisabled);
    }

    public void invalidated(Observable var1) {

    }

    public String getServerNaam() {
        return serverNaamTextField.getText();
    }

    public String getServerPoort() {
        return serverPoortTextField.getText();
    }


    public void setValid() {
        if (!buttonDisabled.get()) {
            vbox.getStyleClass().removeAll("invalid");
        } else {
            vbox.getStyleClass().add("invalid");
        }
    }

    public void loginPressed() {
        model.startServer(serverNaamTextField.getText(), serverPoortTextField.getText());
    }

    public void checkValues(){
        if (!isNumeric(serverPoortTextField.getText()) || serverNaamTextField.getText().equals("")){
            buttonDisabled.setValue(true);
            setValid();
        }else {
            buttonDisabled.setValue(false);
            setValid();
        }
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
