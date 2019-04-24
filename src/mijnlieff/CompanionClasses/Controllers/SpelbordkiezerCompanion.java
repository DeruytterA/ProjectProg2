package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpelbordkiezerCompanion extends MyController {

    @FXML
    private VBox vBox;

    @FXML
    private Button finaliseerButton;

    @FXML
    private GridPane gridpane;

    @FXML
    private TextField x1;

    @FXML
    private TextField y1;

    @FXML
    private TextField x2;

    @FXML
    private TextField y2;

    @FXML
    private TextField x3;

    @FXML
    private TextField y3;

    @FXML
    private TextField x4;

    @FXML
    private TextField y4;

    private TextField[][] alleTextFields;
    private Integer[][] alleWaarden;
    private boolean numeric;

    public void initialize(){
        numeric = false;
        alleTextFields = new TextField[][] {
                new TextField[]{
                        x1, x2, x3, x4
                }, new TextField[]{
                        y1, y2, y3, y4
                }
        };
        alleWaarden = new Integer[][]{
                new Integer[4], new Integer[4]
        };
        for (TextField[] fieldarr:alleTextFields) {
            for (TextField field:fieldarr) {
                field.textProperty().addListener(o -> checkValue());
            }
        }
    }

    public void checkValue(){
        numeric = true;
        vBox.getStyleClass().removeAll("invalid");
        int i = 0;
        while (i < alleTextFields.length && numeric){
            int j = 0;
            while (j < alleTextFields[i].length && numeric){
                try {
                    alleWaarden[i][j] = Integer.parseInt(alleTextFields[i][j].getText());
                }catch (NumberFormatException ex){
                    numeric = false;
                    vBox.getStyleClass().add("invalid");
                }
                j++;
            }
            i++;
        }
        if (numeric){
            for (int j = 0; j < alleWaarden.length; j++) {
                if (isCoordinaatInput(alleWaarden[0][j] + 1 , alleWaarden[1][j])){
                    vBox.getStyleClass().add("invalid");
                    finaliseerButton.setDisable(true);
                }else if (isCoordinaatInput(alleWaarden[0][j], alleWaarden[1][j] + 1)){
                    vBox.getStyleClass().add("invalid");
                    finaliseerButton.setDisable(true);
                }else if (isCoordinaatInput(alleWaarden[0][j] + 1, alleWaarden[1][j] + 1)){
                    vBox.getStyleClass().add("invalid");
                    finaliseerButton.setDisable(true);
                }else {
                    updateGridpane();
                }
            }
        }
    }
    
    public void invalidated(Observable var1){

    }

    public void opschuiven(Integer[] arr, int kleinste){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] - kleinste;
        }
    }

    public boolean checkWaarden(Integer[] arr){
        for (int getal:arr) {
            if (getal == 0){
                return true;
            }
        }
        return false;
    }

    public void updateGridpane(){
        //TODO zware opkuis hier ik bedoel deze klasse
        int grootsteX = grootste(alleWaarden[0]);
        int grootsteY = grootste(alleWaarden[1]);
        gridpane.setPrefSize(grootsteX * 10, grootsteY * 10);
        vBox.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < grootsteX; i++) {
            gridpane.addColumn(i);
        }
        for (int i = 0; i < grootsteY; i++){
            gridpane.addRow(i);
        }
        for (int i = 0; i < grootsteX ; i++) {
            for (int j = 0; j < grootsteY ; j++) {
                if (isCoordinaatInput(i, j)){
                    Rectangle[] rects = new Rectangle[4];
                    for (int k = 0; k < 4 ; k++) {
                        Rectangle rect = new Rectangle();
                        rect.setHeight(20);
                        rect.setWidth(20);
                        rect.setFill(Color.GREEN);
                        rects[k] = rect;
                    }
                    gridpane.add(rects[0], i, j);
                    gridpane.add(rects[1], i + 1, j);
                    gridpane.add(rects[2], i, j +1);
                    gridpane.add(rects[3], i + 1, j + 1);
                }
            }
        }
    }

    public boolean isCoordinaatInput(int x, int y){
        for (int i = 0; i < alleWaarden[0].length; i++) {
            if (alleWaarden[0][i] == x && alleWaarden[1][i] == y){
                return true;
            }
        }
        return false;
    }

    public int grootste(Integer[] array){
        int output = 0;
        System.out.println(array);
        for (Integer getal:array) {
            if (getal > output){
                output = getal;
            }
        }
        return output;
    }

    public int kleinste(Integer[] array){
        int output = array[0];
        for (Integer getal:array) {
            if (getal < output){
                output = getal;
            }
        }
        return output;
    }

    public void finaliseerPressed(){
        if (!checkWaarden(alleWaarden[0])){
            opschuiven(alleWaarden[0], kleinste(alleWaarden[0]));
        }
        if (!checkWaarden(alleWaarden[1])){
            opschuiven(alleWaarden[1], kleinste(alleWaarden[1]));
        }
        updateGridpane();

    }
}

