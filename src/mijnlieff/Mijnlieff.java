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
        SpelModel model = new SpelModel(cont);
        ServerController server = new ServerController(parameters.get(0),parameters.get(1),model);
        primaryStage.setTitle("MijnLieff");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        model.awakeListners();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
