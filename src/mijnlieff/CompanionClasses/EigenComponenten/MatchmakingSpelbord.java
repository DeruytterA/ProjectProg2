package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MatchmakingSpelbord extends StackPane {

    private Label wacht;
    private VBox vbox;

    public MatchmakingSpelbord(SpelBord spelBord){
        super();
        vbox = new VBox();
        vbox.getChildren().add(spelBord);
        this.getChildren().add(vbox);
        wacht = new Label("Wacht op zet tegenstander");
        wacht.setAlignment(Pos.CENTER);
        wacht.setFont(new Font(50));
        wacht.setTextFill(Color.BLACK);
        wacht.setPrefWidth(this.getWidth());
        wacht.setPrefHeight(this.getHeight());
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
