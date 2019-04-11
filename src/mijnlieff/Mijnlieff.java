package mijnlieff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mijnlieff.CompanionClasses.Controllers.MyController;
import mijnlieff.CompanionClasses.Controllers.ServerController;
import mijnlieff.Model.SpelModel;

import java.util.List;

public class Mijnlieff extends Application {

    @Override
    public void start(Stage primaryStage)throws Exception{
        //TODO server aanmaken in MODEL
        List<String> parameters = getParameters().getRaw();
        if(parameters.size() > 0){
            SpelModel model = new SpelModel(parameters.get(0), parameters.get(1));
            loadFile(primaryStage, model, "/mijnlieff/CompanionClasses/Controllers/FxmlEnCssFiles/Interactief.fxml");
            if (parameters.size() > 2){ //start testModus
                new TestModus(model, parameters.get(2), primaryStage.getScene());
                primaryStage.close();
            }
        }else{//Start matchmaking
            SpelModel model = new SpelModel();
            loadFile(primaryStage, model, "/mijnlieff/CompanionClasses/Controllers/FxmlEnCssFiles/AlgemeenMatchmaking.fxml");
            ServerController.setModel(model);
        }
    }

    public void loadFile(Stage primaryStage, SpelModel model, String fxmlFile) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        MyController cont = loader.getController();
        cont.setModel(model);
        model.addListener(cont);
        primaryStage.setTitle("MijnLieff");
        primaryStage.setScene(new Scene(root, 900, 780)); //breedte, hoogte
        primaryStage.show();
        model.awakeListners();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
