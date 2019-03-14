package Spel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {

    public ImageView Wit1;
    public ImageView Wit2;
    public ImageView Wit3;
    public ImageView Wit4;
    public ImageView Wit5;
    public ImageView Wit6;
    public ImageView Wit7;
    public ImageView Wit8;

    public ImageView Zwart1;
    public ImageView Zwart2;
    public ImageView Zwart3;
    public ImageView Zwart4;
    public ImageView Zwart5;
    public ImageView Zwart6;
    public ImageView Zwart7;
    public ImageView Zwart8;

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
                ImageView img = new ImageView();
                img.setImage(new Image("Spel/Photos/Kruis.png"));
                grid.add(img,i,j);
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
