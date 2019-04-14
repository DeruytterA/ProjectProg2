package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import mijnlieff.CompanionClasses.EigenComponenten.Login;
import mijnlieff.CompanionClasses.EigenComponenten.MatchmakingTabel;
import mijnlieff.CompanionClasses.EigenComponenten.Nickname;
import mijnlieff.Model.SpelModel;


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
    }

    @Override
    public void invalidated(Observable var1) {
        if (stadium.equals(SpelStadium.Login)){
            checkServer();
        }else if (stadium.equals(SpelStadium.Nickname)){
            checkNickname();
        }else if (stadium.equals(SpelStadium.Tabel)){
            checkTabel();
        }
    }

    public void checkTabel(){
        if (model.isspelStart()) {
            if (model.isEerste()) {
                //TODO maak een kiezer van spelbord
            } else {
                //TODO luister voor spelbord van tegenstander + zandloper (Task)
            }
        }else{
            showAlert("Speler niet meer beschikbaar", "Deze speler is jammer genoeg niet meer beschikbaar de tabel wordt ge refresht en kies aub een andere");
        }
    }

    private void showAlert(String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public void checkNickname(){
        if (model.getNicknamebool()){
            tabelScreen = new MatchmakingTabel();
            tabelScreen.setModel(model);
            borderPane.setCenter(tabelScreen);
            stadium = SpelStadium.Tabel;
            //TODO verwijder nicknamescreen als listner in model
        }else if (!nicknameScreen.getTextField().equals("")){
            showAlert("Nickname in use", "The nickname you chose is already in use please try another one.");
        }
    }

    public void checkServer(){
        if (model.getServeraan()){
            nicknameScreen = new Nickname();
            nicknameScreen.setModel(model);
            borderPane.setCenter(nicknameScreen);
            stadium = SpelStadium.Nickname;
        }else {
            showAlert("Server not found", "The server you entered was not found please enter a valid adress and try again.");
        }
    }

    @Override
    public void setModel(SpelModel model){
        this.model = model;
        loginScreen.setModel(model);
        model.addListener(this);
    }

    public enum SpelStadium {
        Login,
        Nickname,
        Tabel,
        SpelBordKiezer,
        Spelbord,
        Einde
    }


}
