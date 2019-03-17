package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class Controller implements InvalidationListener {

    public Button buttonBack;
    public Button buttonBackAll;
    public Button buttonForward;
    public Button buttonForwardAll;

    public GridPane grid;
    public BorderPane borderPane;
    private VBox[] over;

    private SpelModel model;


    public void initialize () {
        //grid.setBackground(new Background(new BackgroundImage(new Image("mijnlieff/Photos/achtergrond.png"), BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,new BackgroundSize(150,150,false,false,false,false))));
        buttonBack.setDisable(true);
        buttonBackAll.setDisable(true);
    }

    public void invalidated(Observable var1){
        grid = model.getgrid();
        ObservableList<Node> childrens = grid.getChildren();
        for (Node child:childrens) {
            if (child instanceof AlgemenePion) {
                AlgemenePion hulp = (AlgemenePion) child;
                hulp.setFitWidth(150.0);
                hulp.setFitHeight(150.0);
            }
        }
        over = model.getOver();
        borderPane.setLeft(over[0]);
        borderPane.setRight(over[1]);
        BorderPane.setAlignment(over[0], Pos.CENTER);
        BorderPane.setAlignment(over[1], Pos.CENTER);
        checkButtons();
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

    public void checkButtons(){
        int plaats = model.getPlaatsnu();
        ArrayList<Stap> stappenlijst = model.getStappenlijst();
        if (stappenlijst.size() == plaats){
            buttonForward.setDisable(true);
            buttonForwardAll.setDisable(true);
        }else{
            buttonForward.setDisable(false);
            buttonForwardAll.setDisable(false);
        }
        if (plaats == 0){
            buttonBackAll.setDisable(true);
            buttonBack.setDisable(true);
        }else{
            buttonBackAll.setDisable(false);
            buttonBack.setDisable(false);
        }
    }

    /*
      <VBox fx:id="witteBox" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="150.0" BorderPane.alignment="CENTER">
         <children>
         </children>
      </VBox>
     */


}
