package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mijnlieff.Kleur;
import mijnlieff.Model.SpeelveldModel;
import mijnlieff.Pionnen.Pion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class SpelbordCompanion extends MyController{

    @FXML
    private BorderPane borderPane;
    @FXML
    private GridPane grid;
    @FXML
    private VBox witteOver;
    @FXML
    private VBox zwarteOver;

    private SpeelveldModel model;

    public SpelbordCompanion(SpeelveldModel model){
        this.model = model;
        model.addListener(this);
    }

    public void initialize(){
        witteOver.setAlignment(Pos.CENTER);
        zwarteOver.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(grid, Pos.CENTER);
        BorderPane.setAlignment(witteOver, Pos.CENTER);
        BorderPane.setAlignment(zwarteOver, Pos.CENTER);
    }

    public void invalidated(Observable var1){
        checkGrid(model.getVeld());
        checkVboxen(model.getOverigePionnen());
    }

    public void checkGrid(Pion[][] veld){
        grid.getChildren().clear();
        for (Pion[] pionarr :veld) {
            for (Pion pion:pionarr) {
                grid.add(pion,pion.getYwaarde(),pion.getXwaarde());
                pion.opVeld();
            }
        }
        setPionGrootte(veld);
    }

    public void setPionGrootte(Pion[][] veld){
        for (Pion[] pionarr :veld) {
            for (Pion pion:pionarr) {
                pion.setFitHeight(600/veld.length);
                pion.setFitWidth(600/veld[0].length);
            }
        }
    }

    public void checkVboxen(Map<Kleur, ArrayList<Pion>> over){
        over.get(Kleur.WIT).sort(Comparator.comparing(o -> o.getClass().toString()));
        over.get(Kleur.ZWART).sort(Comparator.comparing(o -> o.getClass().toString()));
        witteOver.getChildren().clear();
        zwarteOver.getChildren().clear();
        witteOver.getChildren().addAll(over.get(Kleur.WIT));
        zwarteOver.getChildren().addAll(over.get(Kleur.ZWART));
    }
}
