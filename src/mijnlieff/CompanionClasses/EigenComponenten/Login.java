package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mijnlieff.CompanionClasses.Controllers.LoginCompanion;
import mijnlieff.Model.SpelModel;

import java.io.IOException;

public class Login extends VBox {

    private LoginCompanion companion;

    public Login(){
        super();
        try {
            FXMLLoader loader = new FXMLLoader(
                    Login.class.getResource(
                            "Login.fxml"));
            loader.setRoot(this);
            this.companion = new LoginCompanion();
            loader.setController(companion);

            loader.load();
        }catch (IOException ex){
            throw new RuntimeException("Er is iets fout gegaan bij het inladen van de FXML van Login " + ex);
        }
    }

    public String[] getValues(){
        String[] arr = {companion.getServerNaam(), companion.getServerPoort()};
        return arr ;
    }

    public void setModel(SpelModel model){
        companion.setModel(model);
    }

    public Boolean getValid(){
        return companion.getValid();
    }

}
