package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import mijnlieff.CompanionClasses.EigenComponenten.InteractiefHbox;
import mijnlieff.Model.SpeelveldModel;

public class InteractiefCompanion extends MyController {

    public Button buttonBack;
    public Button buttonBackAll;
    public Button buttonForward;
    public Button buttonForwardAll;

    public BorderPane borderPane;

    private SpeelveldModel model;
    private InteractiefHbox hbox;

    public void initialize() {
        buttonBack.setDisable(true);
        buttonBackAll.setDisable(true);
        hbox = new InteractiefHbox(this);
        borderPane.setBottom(hbox);
    }

    public void invalidated(Observable var1) {
        checkButtons();
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

    public void checkButtons() {
        if (model.getAantalStappen() == model.getPlaatsnu()) {
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
