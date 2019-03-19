package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Map;

public class Controller implements InvalidationListener {

    public Button buttonBack;
    public Button buttonBackAll;
    public Button buttonForward;
    public Button buttonForwardAll;

    private GridPane grid;
    public BorderPane borderPane;

    private SpelModel model;


    public void initialize() {
        //grid.setBackground(new Background(new BackgroundImage(new Image("mijnlieff/Photos/achtergrond.png"), BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,new BackgroundSize(150,150,false,false,false,false))));
        buttonBack.setDisable(true);
        buttonBackAll.setDisable(true);
        grid = (GridPane) borderPane.getCenter();
    }

    public void invalidated(Observable var1) {
        grid = (GridPane) borderPane.getCenter();
        grid = model.getgrid();
        borderPane.setCenter(grid);
        Map<String, VBox> over = model.getOver();
        borderPane.setLeft(over.get("wit"));
        borderPane.setRight(over.get("zwart"));
        BorderPane.setAlignment(over.get("wit"), Pos.CENTER);
        BorderPane.setAlignment(over.get("zwart"), Pos.CENTER);
        over.get("wit").setAlignment(Pos.CENTER);
        over.get("zwart").setAlignment(Pos.CENTER);
        checkButtons();
    }

    public void setModel(SpelModel model) {
        this.model = model;
    }

    public void buttenBackAll() {
        model.backAll();
    }

    public void buttonBack() {
        model.back();
    }

    public void buttonForward() {
        model.forward();
    }

    public void buttonForwardAll() {
        model.forwardAll();
    }

    public void checkButtons() {
        int plaats = model.getPlaatsnu();
        ArrayList<AlgemenePion> stappenlijst = model.getStappenlijst();
        if (stappenlijst.size() == plaats) {
            buttonForward.setDisable(true);
            buttonForwardAll.setDisable(true);
        } else {
            buttonForward.setDisable(false);
            buttonForwardAll.setDisable(false);
        }
        if (plaats == 0) {
            buttonBackAll.setDisable(true);
            buttonBack.setDisable(true);
        } else {
            buttonBackAll.setDisable(false);
            buttonBack.setDisable(false);
        }
    }

    public GridPane getGrid() {
        return grid;
    }
}
