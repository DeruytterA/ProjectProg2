package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.EigenComponenten.PlayerListViewCell;


public class MatchmakingTabelCompanion extends MyController {

    private ObservableList<String> spelersLijst;

    public VBox vbox;
    public ListView spelersLijstView;
    public Button verwijderUitLijst;
    public Button voegtoeAanlijst;
    public Button kiesSpeler;

    public MatchmakingTabelCompanion(){
        spelersLijst = FXCollections.observableArrayList();
        ServerController.getOponents(spelersLijst);
    }

    public void initialize(){
        verwijderUitLijst.setDisable(true);
        spelersLijstView.setItems(spelersLijst);
        spelersLijstView.setCellFactory(playerListView -> new PlayerListViewCell());
    }

    public void invalidated(Observable observable){
        updateButtons();
        doRefreshList();
    }

    public void updateButtons(){
        boolean inlijst = model.isInLijst();
        voegtoeAanlijst.setDisable(inlijst);
        verwijderUitLijst.setDisable(!inlijst);
        kiesSpeler.setDisable(!inlijst);
    }

    public void verwijderUitLijst(){
        //todo stop met wachten tot gekozen
        model.setInLijst(false);
    }

    public void voegToeAanlijst(){
        //todo wachten tot gekozen
        model.setInLijst(true);
    }

    public void kiesSpeler(){
        ServerController.kiesSpeler( (String) spelersLijstView.getSelectionModel().getSelectedItem());
    }

    public void doRefreshList(){
        ServerController.getOponents(spelersLijst);
    }

}
