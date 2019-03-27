package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import mijnlieff.Pionnen.AlgemenePion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Controller implements InvalidationListener {

    public Button buttonBack;
    public Button buttonBackAll;
    public Button buttonForward;
    public Button buttonForwardAll;

    public VBox witteOver;
    public VBox zwarteOver;
    public GridPane grid;
    public BorderPane borderPane;

    private SpelModel model;


    public void initialize() {
        grid.maxHeightProperty().bindBidirectional(grid.maxWidthProperty());
        grid.minHeightProperty().bindBidirectional(grid.minWidthProperty());
        grid.maxHeightProperty().bindBidirectional(grid.minHeightProperty());
        grid.minWidthProperty().bindBidirectional(grid.maxWidthProperty());
        buttonBack.setDisable(true);
        buttonBackAll.setDisable(true);
        BorderPane.setAlignment(grid, Pos.CENTER);
        witteOver.setAlignment(Pos.CENTER);
        zwarteOver.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(witteOver, Pos.CENTER);
        BorderPane.setAlignment(zwarteOver, Pos.CENTER);
    }

    public void invalidated(Observable var1) {
        updateGrid(model.getgrid());
        updateVboxen(model.getOver());
        checkButtons();
    }

    public void updateGrid(Speelveld veld){
        grid.getChildren().clear();
        AlgemenePion[][] arrayVeld = veld.getVeld();
        for (int i = 0; i < arrayVeld.length; i++) {
            for (int j = 0; j < arrayVeld[0].length; j++) {
                arrayVeld[i][j].fitWidthProperty().bind(grid.widthProperty().divide(4));
                arrayVeld[i][j].fitHeightProperty().bind(grid.heightProperty().divide(4));
                grid.add(arrayVeld[i][j], j, i);
            }
        }
    }

    public void updateVboxen(Map<Kleur, ArrayList<AlgemenePion>> over){
        over.get(Kleur.WIT).sort(Comparator.comparing(o -> o.getClass().toString()));
        over.get(Kleur.ZWART).sort(Comparator.comparing(o -> o.getClass().toString()));
        witteOver.getChildren().clear();
        zwarteOver.getChildren().clear();
        witteOver.getChildren().addAll(over.get(Kleur.WIT));
        zwarteOver.getChildren().addAll(over.get(Kleur.ZWART));
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
}
