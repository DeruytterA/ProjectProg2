package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.Controllers.SpelbordkiezerCompanion;
import mijnlieff.Model.Model;
import java.io.IOException;

public class SpelbordKiezer extends VBox {

    private SpelbordkiezerCompanion companion;

    public SpelbordKiezer(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "SpelbordKiezer.fxml"));
            loader.setRoot(this);
            this.companion = new SpelbordkiezerCompanion();
            loader.setController(companion);
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Spelbordkiezer " + ex);
        }
    }

    public void setModel(Model model){
        companion.setModel(model);
    }

}
