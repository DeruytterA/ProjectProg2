package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import mijnlieff.CompanionClasses.Controllers.SpelbordCompanion;
import mijnlieff.Model.SpeelveldModel;

import java.io.IOException;

public class SpelBord extends BorderPane {

    private SpelbordCompanion companion;
    private SpeelveldModel model;

    public SpelBord(SpeelveldModel model){
        super();
        this.model = model;
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "Spelbord.fxml"));
            loader.setRoot(this);
            this.companion = new SpelbordCompanion(model);
            loader.setController(companion);
            loader.load();
            invalidate();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Spelbord " + ex);
        }
    }

    public void invalidate(){
        model.awakeListners();
    }
}


