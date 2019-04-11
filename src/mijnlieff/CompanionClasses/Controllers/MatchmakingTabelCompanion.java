package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;




public class MatchmakingTabelCompanion extends MyController {

    private boolean inLijst;

    public VBox vbox;
    public ListView spelersLijst;
    public Button verwijderUitLijst;
    public Button voegtoeAanlijst;
    public Button kiesSpeler;

    public void initialize(){
        verwijderUitLijst.setDisable(true);
    }

    public void invalidated(Observable observable){
        updateSpelersLijst();
        updateButtons();
    }

    public void updateButtons(){
        voegtoeAanlijst.setDisable(inLijst);
        verwijderUitLijst.setDisable(!inLijst);
        kiesSpeler.setDisable(!inLijst);
    }

    public void updateSpelersLijst(){
        //TODO
    }

}
