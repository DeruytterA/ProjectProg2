package mijnlieff.CompanionClasses.Controllers;

import javafx.application.Platform;
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

    @FXML
    private Label kleurLabel;

    private SpelStadium stadium;
    private Login loginScreen;
    private Nickname nicknameScreen;
    private MatchmakingTabel tabelScreen;
    private SpelbordKiezer spelbordKiezerScreen;
    private SpeelveldModel speelveldModel;
    private MatchmakingSpelbord spelbord;
    private boolean exit;

    private Map<SpelStadium, Consumer> map;

    public AlgemeenMatchmakingCompanion(Model model) {
        exit = false;
        model.addListener(this);
        this.model = model;
        loginScreen = new Login();
        loginScreen.setModel(model);
        stadium = SpelStadium.Login;
        map = new HashMap<>(){{
            put(SpelStadium.Login, e -> checkServer());
            put(SpelStadium.Nickname, e -> checkNickname());
            put(SpelStadium.SpelBordKiezer, e -> checkSpelbordKiezer());
            put(SpelStadium.Tabel, e -> checkTabel());
            put(SpelStadium.Spelbord, e -> checkSpelbord());
            put(SpelStadium.WachtOpKiezen, e -> checkWachtOpkiezen());
            put(SpelStadium.WachtOpSpelbord, e -> checkSpelbordKiezer());
            put(SpelStadium.WachtOpTegenSpeler, e -> checkWachtOpTegenspeler());
        }};
    }

    public void initialize(){
        borderPane.setCenter(loginScreen);
        nicknameLabel.setVisible(false);
        tegenstanderLabel.setVisible(false);
        kleurLabel.setVisible(false);
    }

    @Override
    public void invalidated(Observable var1) {
        if ( speelveldModel != null && speelveldModel.isEinde() && !exit){
            if (speelveldModel.getMijnPunten() > speelveldModel.getTegenstanderPunten()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Het spel is gedaan!");
                alert.setContentText("Proficiat je hebt gewonnen! \n Jij hebt " + speelveldModel.getMijnPunten() + " punten en de tegenstander maar " + speelveldModel.getTegenstanderPunten() + ".");
                alert.showAndWait();
            }else if (speelveldModel.getMijnPunten() < speelveldModel.getTegenstanderPunten()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Het spel is gedaan!");
                alert.setContentText("Jammer je hebt verloren. \n Jij hebt " + speelveldModel.getMijnPunten() + " punten en de tegenstander " + speelveldModel.getTegenstanderPunten() + ".");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Het spel is gedaan!");
                alert.setContentText("Je hebt gelijk gespeeld! \n Jij hebt " + speelveldModel.getMijnPunten() + " punten en de tegenstander ook.");
                alert.showAndWait();
            }
            if (!exit){
                exit = true;
                model.serverQuit();
                Platform.exit();
            }
        }else if ((model.isQuit() || (speelveldModel != null && speelveldModel.isQuit()) && !exit) ){
            showAlert("Quit", "De andere speler is geQuit het spel wordt afgesloten");
            if (!exit){
                exit = true;
                model.serverQuit();
                Platform.exit();
            }
        }else{
            map.get(stadium).accept(null);
        }
    }

    public void iseerst(){
        if (model.isMaakSpelbord()) {
            spelbordKiezerScreen = new SpelbordKiezer();
            spelbordKiezerScreen.setModel(model);
            borderPane.setCenter(spelbordKiezerScreen);
            stadium = SpelStadium.SpelBordKiezer;
            kleurLabel.setText("Jij bent: zwart");
            kleurLabel.setVisible(true);
        } else {
            Wachten wachten = new Wachten("Wacht op het spelbord van de tegenstander");
            borderPane.setCenter(wachten);
            stadium = SpelStadium.WachtOpSpelbord;
            model.serverWachtOpSpelBord();
            kleurLabel.setText("Jij bent: wit");
            kleurLabel.setVisible(true);
        }
    }

    public void checkWachtOpTegenspeler(){
        if (!speelveldModel.isWachten()){
            spelbord.setWacht(false);
            stadium = SpelStadium.Spelbord;
        }
    }

    public void checkWachtOpkiezen(){
        if (model.isspelStart()){
            iseerst();
            tegenstanderLabel.setText("jouw tegenstander is: " + model.getTegenstander());
            tegenstanderLabel.setVisible(true);
        }
    }

    public void checkSpelbord(){
        if (speelveldModel.isValsGespeeld()){
            showAlert("Valsgespeeld", "De Tegenstander heeft vals gespeeld het spel wordt nu afgesloten");
            model.serverQuit();
            Platform.exit();
        }else if (speelveldModel.isWachten()){
            spelbord.setWacht(true);
            stadium = SpelStadium.WachtOpTegenSpeler;
        }
    }

    public void checkSpelbordKiezer(){
        if (model.isSpelBordKiezer()){
            speelveldModel = model.getSpeelveldModel();
            SpelBord spelBord = new SpelBord(speelveldModel);
            speelveldModel.addListener(this);
            spelbord = new MatchmakingSpelbord(spelBord);
            spelbord.setWacht(speelveldModel.isWachten());
            borderPane.setCenter(spelbord);
            if (speelveldModel.isWachten()){
                stadium = SpelStadium.WachtOpTegenSpeler;
            }else {
                stadium = SpelStadium.Spelbord;
            }
        }
    }

    public void checkTabel(){
        if (model.isspelStart()) {
            iseerst();
            tegenstanderLabel.setText("jouw tegenstander is: " + model.getTegenstander());
            tegenstanderLabel.setVisible(true);
        }else if (model.isInLijst()){
            Wachten wachten = new Wachten("Wacht tot je gekozen wordt");
            borderPane.setCenter(wachten);
            stadium = SpelStadium.WachtOpKiezen;
        }
    }

    public void checkNickname(){
        if (model.getNicknamebool()){
            tabelScreen = new MatchmakingTabel(model);
            tabelScreen.setModel(model);
            borderPane.setCenter(tabelScreen);
            nicknameLabel.setText("jouw nickname is: " + model.getNicknameString());
            nicknameLabel.setVisible(true);
            stadium = SpelStadium.Tabel;
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
        WachtOpTegenSpeler
    }

    private void showAlert(String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
