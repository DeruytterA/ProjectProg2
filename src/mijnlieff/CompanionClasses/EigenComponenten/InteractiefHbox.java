package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import mijnlieff.CompanionClasses.Controllers.InteractiefCompanion;

import java.io.IOException;

public class InteractiefHbox extends HBox {

    public InteractiefHbox(InteractiefCompanion companion){
        super();
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "InteractiefHbox.fxml"));
            loader.setRoot(this);
            loader.setController(companion);
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Login " + ex);
        }
    }
}
