package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mijnlieff.Model.SpeelveldModel;

public class MatchmakingSpelbord extends StackPane {

    private Label wacht;
    private VBox vbox;

    public MatchmakingSpelbord(SpelBord spelBord, SpeelveldModel model){
        super();
        vbox = new VBox();
        Button slaOverButton = new Button("Sla deze zet over");
        vbox.getChildren().add(spelBord);
        vbox.getChildren().add(slaOverButton);
        this.getChildren().add(vbox);
        wacht = new Label("Wacht op zet tegenstander");
        wacht.setAlignment(Pos.CENTER);
        wacht.setFont(new Font(50));
        wacht.setTextFill(Color.BLACK);
        slaOverButton.setOnMouseClicked( o -> model.slaStapOver());
    }

    public void setWacht(boolean bool){
        if (bool){
            this.getChildren().add(wacht);
            wacht.setMouseTransparent(false);
            wacht.setPrefWidth(this.getWidth());
            wacht.setPrefHeight(this.getHeight());
        }else{
            this.getChildren().remove(wacht);
        }
    }


}
