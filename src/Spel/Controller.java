package Spel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {

    public GridPane grid;
    private SpelModel model;

    public void initialize () {
        for(int i = 0; i < grid.getColumnCount(); i++){
            for(int j = 0; j < grid.getRowCount(); j++ ){
                grid.add(new ImageView(new Image("Spel/Photos/achtergrond.png")),i,j);
            }
        }
    }


    public void setModel(SpelModel model){
        this.model = model;
    }

    public void buttenBackAll(){
        model.backAll();
    }
    public void buttonBack(){
        model.back();
    }
    public void buttonForward(){
        model.forward();
    }
    public void buttonForwardAll(){
        model.forwardAll();
    }
}
