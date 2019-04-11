package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginCompanion extends MyController{

    public VBox vbox;
    public TextField serverNaamTextField;
    public TextField serverPoortTextField;
    public Button loginButton;

    private Boolean valid;

    public void initialize(){
        TextField[] textFields = new TextField[] {
            serverNaamTextField, serverPoortTextField
        };

        for (TextField field : textFields) {
            field.textProperty().addListener(o -> checkValues());
        }
        loginButton.setDisable(true);
    }

    public void invalidated(Observable var1) {}

    public String getServerNaam() {
        return serverNaamTextField.getText();
    }

    public String getServerPoort() {
        return serverPoortTextField.getText();
    }

    public Boolean getValid(){
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        if (valid) {
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
            setValid(false);
            loginButton.setDisable(true);
        }else {
            setValid(true);
            loginButton.setDisable(false);
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
