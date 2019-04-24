package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import mijnlieff.CompanionClasses.Controllers.SpelbordCompanion;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.io.IOException;

public class SpelBord extends BorderPane {

    private SpelbordCompanion companion;

    public SpelBord(SpeelveldModel model){
        super();
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "Spelbord.fxml"));
            loader.setRoot(this);
            this.companion = new SpelbordCompanion(model);
            loader.setController(companion);
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Spelbord " + ex);
        }
    }
}


