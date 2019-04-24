package mijnlieff.Pionnen;

import javafx.scene.image.ImageView;
import mijnlieff.CompanionClasses.EigenComponenten.SpelBord;
import mijnlieff.Kleur;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.util.ArrayList;

public abstract class Pion extends ImageView{
    protected Boolean opVeld;
    protected SpeelveldModel model;
    protected Kleur kleur;
    protected int xwaarde;
    protected int ywaarde;

    public Pion(boolean matchmaking) {
        super();
        this.setPickOnBounds(true);
        this.setPreserveRatio(true);
        aanRand();
        if (matchmaking){
            this.setOnMouseClicked(e -> checkMouseClicked());
        }
    }


    public int getXwaarde() {
        return xwaarde;
    }

    public int getYwaarde() {
        return ywaarde;
    }

    public void setCoordinaten(int xwaarde, int ywaarde){
        this.xwaarde = xwaarde;
        this.ywaarde = ywaarde;
    }

    public void opVeld(){
        opVeld = true;
    }

    public void aanRand(){
        this.fitHeightProperty().unbind();
        this.fitWidthProperty().unbind();
        opVeld = false;
        this.setFitHeight(75.0);
        this.setFitWidth(75.0);
    }

    public void checkMouseClicked(){
        if (!opVeld){
            model.setTeVerplaatsenPion(this);
        }else {
            if (this instanceof LegePion && model.getTeVerplaatsenPion() != null){
                model.verplaatsPionNaar(xwaarde, ywaarde, model.getTeVerplaatsenPion());
            }
            //TODO als er op een pion geklikt wordt dat al op het veld staat en het is geen lege pion
        }
    }

    public void setModel(SpeelveldModel model){
        this.model = model;
    }

    public void setKleur(Kleur kleur){
        this.kleur = kleur;
    }

    public Kleur getKleur() {
        return kleur;
    }

    public abstract void initialize();

    public abstract boolean checkCoordinates(int x, int y); //TODO implementeer dit in de verschillende pionnen

}
