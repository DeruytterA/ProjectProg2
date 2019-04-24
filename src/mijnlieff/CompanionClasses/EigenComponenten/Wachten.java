package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.Controllers.WachtenController;

import java.io.IOException;

public class Wachten extends VBox {

    public Wachten(String labelWaarde){
        WachtenController controller = new WachtenController(labelWaarde);
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "Wachten.fxml"));
            loader.setRoot(this);
            loader.setController(controller);
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Spelbordkiezer " + ex);
        }
    }


}
