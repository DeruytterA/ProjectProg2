package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import mijnlieff.CompanionClasses.EigenComponenten.SpelBord;
import mijnlieff.Model.SpeelveldModel;

public class InteractiefCompanion extends MyController {

    public Button buttonBack;
    public Button buttonBackAll;
    public Button buttonForward;
    public Button buttonForwardAll;

    public BorderPane borderPane;

    private SpeelveldModel model;
    private SpelBord spelBord;

    public InteractiefCompanion(SpeelveldModel model){
        this.model = model;
        model.addListener(this);
    }

    public void initialize() {
        buttonBack.setDisable(true);
        buttonBackAll.setDisable(true);
        spelBord = new SpelBord(model);
        borderPane.setCenter(spelBord);
        model.awakeListners();
        model.getStappenlijst().addListener((InvalidationListener) o -> checkButtons(model.getStappenlijst()));
    }

    public void invalidated(Observable var1) {
        checkButtons(model.getStappenlijst());
    }

    public void buttonBackAll() {
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

    public void checkButtons(ObservableList lijst) {
        if (lijst.size() == model.getPlaatsnu()) {
            buttonForward.setDisable(true);
            buttonForwardAll.setDisable(true);
        } else {
            buttonForward.setDisable(false);
            buttonForwardAll.setDisable(false);
        }
        if (model.getPlaatsnu() == 0) {
            buttonBackAll.setDisable(true);
            buttonBack.setDisable(true);
        } else {
            buttonBackAll.setDisable(false);
            buttonBack.setDisable(false);
        }
    }
}
