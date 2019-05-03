package mijnlieff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import mijnlieff.CompanionClasses.Controllers.AlgemeenMatchmakingCompanion;
import mijnlieff.CompanionClasses.Controllers.InteractiefCompanion;
import mijnlieff.CompanionClasses.Controllers.MyController;
import mijnlieff.CompanionClasses.Controllers.ServerController;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.util.List;

public class Mijnlieff extends Application {

    private Model model;
    private ServerController serverController;

    @Override
    public void start(Stage primaryStage)throws Exception{
        List<String> parameters = getParameters().getRaw();
        if(parameters.size() > 0) {
            serverController = new ServerController(parameters.get(0), parameters.get(1));
            SpeelveldModel spelbordModel = new SpeelveldModel(0,0,0,2,2,0,2,2, false);
            serverController.setSpeelveldModel(spelbordModel);
            serverController.interactief();
            InteractiefCompanion companion = new InteractiefCompanion(spelbordModel);
            loadFile(primaryStage, "/mijnlieff/CompanionClasses/Controllers/FxmlEnCssFiles/Interactief.fxml", companion);
            if (parameters.size() > 2){ //start testModus
                new TestModus(spelbordModel, parameters.get(2), primaryStage.getScene());
                primaryStage.close();
            }
        }else {//Start matchmaking
            model = new Model();
            MyController controller = new AlgemeenMatchmakingCompanion(model);
            loadFile(primaryStage, "/mijnlieff/CompanionClasses/Controllers/FxmlEnCssFiles/AlgemeenMatchmaking.fxml", controller);
        }
    }

    private void loadFile(Stage primaryStage, String fxmlFile, MyController controller) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("MijnLieff");
        primaryStage.setScene(new Scene(root, 900, 780)); //breedte, hoogte
        primaryStage.show();
    }

    @Override
    public void stop(){
        if (serverController != null){
            serverController.close();
        }else {
            if (model.getServer() != null){
                model.getServer().close();
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Het spel wordt afgesloten");
        alert.setHeaderText("Afsluiten");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
