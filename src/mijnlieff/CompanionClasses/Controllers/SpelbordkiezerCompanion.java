package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import mijnlieff.CompanionClasses.EigenComponenten.MyToggleButton;

import java.util.ArrayList;


public class SpelbordkiezerCompanion extends MyController {

    @FXML
    private Button finaliseerButton;
    @FXML
    private GridPane gridpane;

    private MyToggleButton[][] buttons;
    private ArrayList<Selectie> geselecteerd;
    private SimpleBooleanProperty valid;

    @Override
    public void invalidated(Observable var1) {

    }

    public SpelbordkiezerCompanion(){
        buttons = new MyToggleButton[11][11];
        geselecteerd = new ArrayList<>();
        valid = new SimpleBooleanProperty(false);
    }

    public void initialize(){
        gridpane.setPrefSize(50*11,50*11);
        for (int i = 0; i < 11 ; i++) {
            for (int j = 0; j < 11 ; j++) {
                MyToggleButton button = new MyToggleButton(i,j);
                button.verWijderSelectie();
                button.setPrefSize(50,50);
                button.selectedProperty().addListener(o -> buttonPressed(button));
                buttons[i][j] = button;
                gridpane.add(button, j, i);
            }
        }
        finaliseerButton.disableProperty().bind(valid.not());
        checkvalues();
    }

    public void checkvalues(){
        if (geselecteerd.size() == 4){
            Integer[][] intarr = new Integer[4][2];
            int i = 0;
            while (i < geselecteerd.size()){
                intarr[i] = geselecteerd.get(i).getLinksBoven();
                i++;
            }
            boolean xWaarden = false;
            boolean yWaarden = false;
            for (Integer[] inarr :intarr) {
                if (inarr[0] == 0){
                    xWaarden = true;
                }
                if (inarr[1] == 0){
                    yWaarden = true;
                }
            }
            if (xWaarden && yWaarden){
                valid.set(true);
            }else {
                valid.set(false);
            }
        }else {
            valid.set(false);
        }
    }

    public boolean magKlikken(MyToggleButton button){
        if (button.getxWaarde() == 10 || button.getyWaarde() == 10 || geselecteerd.size() == 4){
            return false;
        }else {
            return !buttons[button.getxWaarde() + 1][button.getyWaarde()].isInSelectie() && !buttons[button.getxWaarde()][button.getyWaarde() + 1].isInSelectie() && !buttons[button.getxWaarde() + 1][button.getyWaarde() + 1].isInSelectie();
        }
    }

    public void buttonPressed(MyToggleButton button){
        checkvalues();
        if (button.isSelected() && !button.isInSelectie()){
            if (magKlikken(button)){
                new Selectie(button);
            }else {
                button.setSelected(false);
            }
        }else if (!button.isSelected() && button.isInSelectie()){
            Selectie selectie = button.getSelectie();
            selectie.select(false);
        }
    }

    public void finaliseerPressed(){
        model.serverStuurSpelbord(parseSelectieToString());
    }

    public String parseSelectieToString(){
        StringBuilder output = new StringBuilder("X ");
        for (Selectie selectie:geselecteerd) {
            Integer[] coordinaat =  selectie.getLinksBoven();
            output.append(coordinaat[0].toString()).append(" ").append(coordinaat[1].toString()).append(" ");
        }
        return output.substring(0, output.length() - 1);
    }

    public class Selectie{

        private MyToggleButton[] dezeSelectie;
        private Integer[] linksBoven;

        public Selectie(MyToggleButton button){
            linksBoven = new Integer[2];
            linksBoven[0] = button.getxWaarde();
            linksBoven[1] = button.getyWaarde();
            dezeSelectie = new MyToggleButton[] {
                    button,
                    buttons[linksBoven[0] + 1][linksBoven[1]],
                    buttons[linksBoven[0]][linksBoven[1] + 1],
                    buttons[linksBoven[0] + 1][linksBoven[1] + 1]
            };
            for (MyToggleButton knop:dezeSelectie) {
                knop.setSelectie(this);
            }
            select(true);
        }

        public void select(boolean bool){
            if (bool){
                geselecteerd.add(this);
            }else {
                for (MyToggleButton button:dezeSelectie) {
                    button.verWijderSelectie();
                }
                geselecteerd.remove(this);
            }
            for (ToggleButton button:dezeSelectie) {
                button.setSelected(bool);
            }
        }

        public Integer[] getLinksBoven() {
            return linksBoven;
        }
    }

}

