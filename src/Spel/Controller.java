package Spel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {

    public AlgemenePion Wit1;
    public AlgemenePion Wit2;
    public AlgemenePion Wit3;
    public AlgemenePion Wit4;
    public AlgemenePion Wit5;
    public AlgemenePion Wit6;
    public AlgemenePion Wit7;
    public AlgemenePion Wit8;

    public AlgemenePion Zwart1;
    public AlgemenePion Zwart2;
    public AlgemenePion Zwart3;
    public AlgemenePion Zwart4;
    public AlgemenePion Zwart5;
    public AlgemenePion Zwart6;
    public AlgemenePion Zwart7;
    public AlgemenePion Zwart8;

    public GridPane grid;

    public void initialize () {
        Wit1.setImage(new Image("Spel/Photos/wit-loper.png"));
        Wit2.setImage(new Image("Spel/Photos/wit-loper.png"));
        Wit3.setImage(new Image("Spel/Photos/wit-puller.png"));
        Wit4.setImage(new Image("Spel/Photos/wit-puller.png"));
        Wit5.setImage(new Image("Spel/Photos/wit-pusher.png"));
        Wit6.setImage(new Image("Spel/Photos/wit-pusher.png"));
        Wit7.setImage(new Image("Spel/Photos/wit-toren.png"));
        Wit8.setImage(new Image("Spel/Photos/wit-toren.png"));

        Zwart1.setImage(new Image("Spel/Photos/zwart-loper.png"));
        Zwart2.setImage(new Image("Spel/Photos/zwart-loper.png"));
        Zwart3.setImage(new Image("Spel/Photos/zwart-puller.png"));
        Zwart4.setImage(new Image("Spel/Photos/zwart-puller.png"));
        Zwart5.setImage(new Image("Spel/Photos/zwart-pusher.png"));
        Zwart6.setImage(new Image("Spel/Photos/zwart-pusher.png"));
        Zwart7.setImage(new Image("Spel/Photos/zwart-toren.png"));
        Zwart8.setImage(new Image("Spel/Photos/zwart-toren.png"));

        for(int i = 0; i < grid.getColumnCount(); i++){
            for(int j = 0; j < grid.getRowCount(); j++ ){
                grid.add(new ImageView(new Image("Spel/Photos/achtergrond.png")),i,j);
            }
        }
    }

    public void buttenBackAll(){

    }
    public void buttonBack(){

    }
    public void buttonForward(){

    }
    public void buttonForwardAll(){

    }
}
