package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;


public class SpelbordkiezerCompanion extends MyController {

    @FXML
    private Button finaliseerButton;
    @FXML
    private GridPane gridpane;

    private ToggleButton[][] buttons;
    private SimpleListProperty<Selectie> geselecteerd;
    private SimpleBooleanProperty valid;

    @Override
    public void invalidated(Observable var1) {

    }

    public SpelbordkiezerCompanion(){
        buttons = new ToggleButton[11][11];
        geselecteerd = new SimpleListProperty<>();
        valid = new SimpleBooleanProperty(false);
    }

    public void initialize(){
        gridpane.setPrefSize(50*11,50*11);
        for (int i = 0; i < 11 ; i++) {
            for (int j = 0; j < 11 ; j++) {
                System.out.println("button " + i + " " + j);
                ToggleButton button = new ToggleButton();
                button.setSelected(false);
                button.setPrefSize(50,50);
                button.selectedProperty().addListener(o -> buttonPressed(button));
                buttons[i][j] = button;
                gridpane.add(button, i, j);
            }
        }
        finaliseerButton.disableProperty().bind(valid);
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
        }
        valid.set(false);
    }

    public void buttonPressed(ToggleButton button){
        checkvalues();
        if (button.isSelected()){
            if (magKlikken(button)){
                geselecteerd.add(new Selectie(button).select(true));
            }else {
                button.setSelected(false);
            }
        }else {
            Selectie selectie = inWelkeSelectie(button);
            if (selectie != null){
                geselecteerd.remove(inWelkeSelectie(button).select(false));
            }
        }
    }

    public boolean magKlikken(ToggleButton button){
        Integer[] coordinaat = vindLocatie(button);
        return coordinaat[0] != 10 && coordinaat[1] != 10;
    }

    public Selectie inWelkeSelectie(ToggleButton button){
        int i = 0;
        while (i < geselecteerd.size() && !geselecteerd.get(i).buttonInSelectie(button)){
            i++;
        }
        if (i < geselecteerd.size()){
            return geselecteerd.get(i);
        }
        return null;
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
        return output.substring(0, output.length() - 2);
    }

    public Integer[] vindLocatie(ToggleButton button){
        Integer[] output = new Integer[2];
        boolean found = false;
        int i = 0;
        while (i < buttons.length && ! found){
            int j = 0;
            while (j < buttons[i].length && ! found){
                if (buttons[i][j].equals(button)){
                    found = true;
                    output[0] = i;
                    output[1] = j;
                }
                j++;
            }
            i++;
        }
        return output;
    }

    public class Selectie{

        private ToggleButton[] dezeSelectie;
        private Integer[] linksBoven;

        public Selectie(ToggleButton button){
            linksBoven = vindLocatie(button);
            dezeSelectie = new ToggleButton[] {
                    button,
                    buttons[linksBoven[0] + 1][linksBoven[1]],
                    buttons[linksBoven[0]][linksBoven[1] + 1],
                    buttons[linksBoven[0] + 1][linksBoven[1] + 1]
            };
        }

        public Selectie select(boolean bool){
            for (ToggleButton button:dezeSelectie) {
                button.setSelected(bool);
            }
            return this;
        }

        public boolean buttonInSelectie(ToggleButton button){
            int i = 0;
            while (i < dezeSelectie.length && !dezeSelectie[i].equals(button)){
                i++;
            }
            return i < dezeSelectie.length;
        }

        public Integer[] getLinksBoven() {
            return linksBoven;
        }
    }

}

