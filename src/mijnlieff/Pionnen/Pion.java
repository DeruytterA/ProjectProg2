package mijnlieff.Pionnen;

import javafx.scene.image.ImageView;
import mijnlieff.CompanionClasses.EigenComponenten.SpelBord;
import mijnlieff.Kleur;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.util.ArrayList;

public abstract class Pion extends ImageView{

    private static char character = ' ';

    protected Boolean opVeld;
    protected SpeelveldModel model;
    protected Kleur kleur;
    protected int xwaarde;
    protected int ywaarde;
    protected boolean matchmaking;

    public Pion(boolean matchmaking) {
        super();
        this.matchmaking = matchmaking;
        this.setPickOnBounds(true);
        this.setPreserveRatio(true);
        aanRand();
    }

    public Pion(boolean matchmaking, SpeelveldModel model){
        this(matchmaking);
        this.model = model;
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
        }
    }

    public void setModel(SpeelveldModel model){
        this.model = model;
    }

    public void setKleur(Kleur kleur){
        this.kleur = kleur;
        if ((matchmaking && model.getMijnKleur().equals(kleur)) || this instanceof LegePion){
            this.setOnMouseClicked(e -> checkMouseClicked());
        }
    }

    public Kleur getKleur() {
        return kleur;
    }

    public abstract void initialize();

    public abstract boolean checkCoordinates(int x, int y); //TODO implementeer dit in de verschillende pionnen

    public abstract char getCharacter();

}
