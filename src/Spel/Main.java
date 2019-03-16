package Spel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        List<String> parameters = getParameters().getRaw();
        ServerController server = new ServerController(parameters.get(0),parameters.get(1));
        SpelModel model = new SpelModel(server, cont);
        primaryStage.setTitle("MijnLieff");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
