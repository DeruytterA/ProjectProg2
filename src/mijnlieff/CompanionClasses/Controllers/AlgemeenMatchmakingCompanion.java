package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import mijnlieff.CompanionClasses.EigenComponenten.Login;
import mijnlieff.CompanionClasses.EigenComponenten.MatchmakingTabel;
import mijnlieff.CompanionClasses.EigenComponenten.Nickname;
import mijnlieff.Model.SpelModel;
import mijnlieff.SpelStadium;


public class AlgemeenMatchmakingCompanion extends MyController {

    public BorderPane borderPane;

    private SpelStadium stadium;
    private Login loginScreen;
    private Nickname nicknameScreen;
    private MatchmakingTabel tabelScreen;

    public void initialize(){
        stadium = SpelStadium.Login;
        loginScreen = new Login();
        borderPane.setCenter(loginScreen);
        tabelScreen = new MatchmakingTabel();
        nicknameScreen = new Nickname();
    }

    @Override
    public void invalidated(Observable var1) {
        if (stadium.equals(SpelStadium.Login)){
            checkServer();
        }else if (stadium.equals(SpelStadium.Nickname)){
            checkNickname();
        }
    }

    public void checkNickname(){
        if (model.getNickname()){
            borderPane.setCenter(tabelScreen);
            stadium = SpelStadium.Tabel;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nickname in use");
            alert.setContentText("The nickname you chose is allready in use please try another one.");
            alert.showAndWait();
        }
    }

    public void checkServer(){
        if (model.getServeraan()){
            borderPane.setCenter(nicknameScreen);
            stadium = SpelStadium.Nickname;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Server not found");
            alert.setContentText("The server you entered was not found please enter a valid adress and try again.");
            alert.showAndWait();
        }
    }

    @Override
    public void setModel(SpelModel model){
        this.model = model;
        model.addListener(this);
        loginScreen.setModel(model);
        tabelScreen.setModel(model);
    }

}
