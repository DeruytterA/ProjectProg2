package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;


public class MatchmakingTabelCompanion extends MyController {

    private ObservableList<String> spelersLijst;

    @FXML
    private VBox vbox;
    @FXML
    private ListView spelersLijstView;
    @FXML
    private Button verwijderUitLijst;
    @FXML
    private Button voegtoeAanlijst;
    @FXML
    private Button kiesSpeler;

    public MatchmakingTabelCompanion(){
        spelersLijst = FXCollections.observableArrayList();
        ServerController.getOponents(spelersLijst);
    }

    public void initialize(){
        verwijderUitLijst.setDisable(true);
        spelersLijstView.setItems(spelersLijst);
        spelersLijstView.setCellFactory(playerListView -> new ListCell<String>(){

            @Override
            protected void updateItem(String string, boolean empty){
                super.updateItem(string, empty);
                setText(string);
            }

        });
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
        model.serverUitLijst();
    }

    public void voegToeAanlijst(){
        model.serverInlijst();
    }

    public void kiesSpeler(){
        model.kiesSpeler((String) spelersLijstView.getSelectionModel().getSelectedItem());
    }

    public void doRefreshList(){
        ServerController.getOponents(spelersLijst);
    }
}
