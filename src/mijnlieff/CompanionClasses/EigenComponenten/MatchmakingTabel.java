package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.Controllers.MatchmakingTabelCompanion;
import mijnlieff.Model.SpelModel;

import java.io.IOException;

public class MatchmakingTabel extends VBox {

    private MatchmakingTabelCompanion companion;

    public MatchmakingTabel(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    MatchmakingTabel.class.getResource(
                            "MatchmakingTabel.fxml"));
            loader.setRoot(this);
            companion = new MatchmakingTabelCompanion();
            loader.setController(companion);
            loader.load();
        }catch (IOException ex){//TODO hier error bij matchmaking
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van MatchmakingTabel " + ex);
        }
    }
    //TODO wat moet dit object nog kunnen

    public void setModel(SpelModel model){
        companion.setModel(model);
    }
}
