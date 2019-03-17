package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Controller implements InvalidationListener {

    public GridPane grid;
    public BorderPane borderPane;
    private VBox[] over;

    private SpelModel model;


    public void initialize () {
        for(int i = 0; i < grid.getColumnCount(); i++){
            for(int j = 0; j < grid.getRowCount(); j++ ){
                grid.add(new ImageView(new Image("mijnlieff/Photos/achtergrond.png")),i,j);
            }
        }
    }

    public void invalidated(Observable var1){
        grid = model.getgrid();
        over = model.getOver();
        borderPane.setLeft(over[0]);
        borderPane.setRight(over[1]);
        BorderPane.setAlignment(over[0], Pos.CENTER);
        BorderPane.setAlignment(over[1], Pos.CENTER);
    }

    public GridPane getSpeelveld(){
        return grid;
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

    /*
      <VBox fx:id="witteBox" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="150.0" BorderPane.alignment="CENTER">
         <children>
         </children>
      </VBox>
     */


}
