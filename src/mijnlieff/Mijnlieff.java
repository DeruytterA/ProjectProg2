package mijnlieff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mijnlieff.CompanionClasses.Controllers.MyController;
import mijnlieff.CompanionClasses.Controllers.ServerController;
import mijnlieff.Model.Model;

import java.util.List;

public class Mijnlieff extends Application {

    @Override
    public void start(Stage primaryStage)throws Exception{
        List<String> parameters = getParameters().getRaw();
        if(parameters.size() > 0) {
            Model model = new Model(parameters.get(0), parameters.get(1), false);
            loadFile(primaryStage, model, "/mijnlieff/CompanionClasses/EigenComponenten/InteractiefHbox.fxml");
            if (parameters.size() > 2){ //start testModus
                new TestModus(model, parameters.get(2), primaryStage.getScene());
                primaryStage.close();
            }
        }else {//Start matchmaking
            Model model = new Model(true);
            loadFile(primaryStage, model, "/mijnlieff/CompanionClasses/Controllers/FxmlEnCssFiles/AlgemeenMatchmaking.fxml");
            ServerController.setModel(model);
        }
    }

    private void loadFile(Stage primaryStage, Model model, String fxmlFile) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        MyController cont = loader.getController();
        cont.setModel(model);
        primaryStage.setTitle("MijnLieff");
        primaryStage.setScene(new Scene(root, 900, 780)); //breedte, hoogte
        primaryStage.show();
    }

    public void stop(){
        ServerController.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
