package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.EigenComponenten.*;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class AlgemeenMatchmakingCompanion extends MyController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vbox;

    @FXML
    private Label tegenstanderLabel;

    @FXML
    private Label nicknameLabel;

    private SpelStadium stadium;
    private Login loginScreen;
    private Nickname nicknameScreen;
    private MatchmakingTabel tabelScreen;
    private SpelbordKiezer spelbordKiezerScreen;
    private SpelBord spelBord;
    private SpeelveldModel speelveldModel;

    private Map<SpelStadium, Consumer> map;

    public void initialize(){
        stadium = SpelStadium.Login;
        loginScreen = new Login();
        borderPane.setCenter(loginScreen);

        map = new HashMap<>(){{
            put(SpelStadium.Login, e -> checkServer());
            put(SpelStadium.Nickname, e -> checkNickname());
            put(SpelStadium.SpelBordKiezer, e -> checkSpelbordKiezer());
            put(SpelStadium.Tabel, e -> checkTabel());
            put(SpelStadium.Spelbord, e -> checkSpelbord());
            put(SpelStadium.WachtOpKiezen, e -> checkWachtOpkiezen());
            put(SpelStadium.WachtOpSpelbord, e -> checkWachtOpSpelbord());
        }};
    }

    @Override
    public void invalidated(Observable var1) {
        if (model.getNicknamebool()){
            nicknameLabel.setText("jouw nickname is: " + model.getNicknameString());
        }
        if (model.isspelStart()){
            tegenstanderLabel.setText("jouw tegenstander is: " + model.getTegenstander());
        }
        map.get(stadium).accept(null);
    }

    public void iseerst(){
        if (model.isEerste()) {
            spelbordKiezerScreen = new SpelbordKiezer();
            borderPane.setCenter(spelbordKiezerScreen);
            stadium = SpelStadium.SpelBordKiezer;
        } else {
            Wachten wachten = new Wachten("Wacht op het spelbord van de tegenstander");
            borderPane.setCenter(wachten);
            stadium = SpelStadium.WachtOpSpelbord;
            model.serverWachtOpSpelBord();
        }
    }

    public void checkWachtOpkiezen(){
        if (model.isspelStart()){
            iseerst();
        }
    }

    public void checkWachtOpSpelbord(){
        if (model.getSpeelveld() != null){
            spelBord = new SpelBord(model.getSpeelveld());
            borderPane.setCenter(spelBord);
            stadium = SpelStadium.Spelbord;
        }
    }

    public void checkSpelbord(){
        if (model.isEinde()){
            //TODO maak een einde scherm met winnaar of verliezer
            stadium = SpelStadium.Einde;
        }
    }

    public void checkSpelbordKiezer(){
        if (model.isSpelBordKiezer()){
            spelBord = new SpelBord(model.getSpeelveld());
            borderPane.setCenter(spelBord);
            stadium = SpelStadium.Spelbord;
        }
    }

    public void checkTabel(){
        if (model.isspelStart()) {
            iseerst();
        }else if (model.isInLijst()){
            Wachten wachten = new Wachten("Wacht tot je gekozen wordt");
            borderPane.setCenter(wachten);
            stadium = SpelStadium.WachtOpKiezen;
        }
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
    public void setModel(Model model){
        this.model = model;
        loginScreen.setModel(model);
        model.addListener(this);
    }

    public enum SpelStadium {
        Login,
        Nickname,
        Tabel,
        SpelBordKiezer,
        WachtOpSpelbord,
        WachtOpKiezen,
        Spelbord,
        Einde,
        Zandloper
    }

    private void showAlert(String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
