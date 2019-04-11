package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.Controllers.NicknameCompanion;

import java.io.IOException;

public class Nickname extends VBox {

    private NicknameCompanion companion;

    public Nickname(){
        super();
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "Nickname.fxml"));
            loader.setRoot(this);
            this.companion = new NicknameCompanion();
            loader.setController(companion);
            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Login " + ex);
        }
    }
}
