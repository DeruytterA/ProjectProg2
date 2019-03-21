package mijnlieff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Mijnlieff extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        List<String> parameters = getParameters().getRaw();
        SpelModel model = new SpelModel();
        cont.setModel(model);
        model.addListener(cont);
        new ServerController(parameters.get(0),parameters.get(1),model);
        primaryStage.setTitle("MijnLieff");
        //breedte, hoogte
        primaryStage.setScene(new Scene(root, 900, 780));
        model.awakeListners();
        primaryStage.show();
        if (parameters.size() > 2){
            new TestModus(model, parameters.get(2), primaryStage.getScene());
            primaryStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
