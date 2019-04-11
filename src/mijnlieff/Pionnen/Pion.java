package mijnlieff.Pionnen;

import javafx.scene.image.ImageView;
import mijnlieff.Kleur;
import mijnlieff.Model.SpelModel;

public abstract class Pion extends ImageView{
    protected Boolean opVeld;
    protected SpelModel model;
    protected Kleur kleur;
    protected int xwaarde;
    protected int ywaarde;

    public Pion() {
        super();
        this.setPickOnBounds(true);
        this.setPreserveRatio(true);
        aanRand();
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

    public void setModel(SpelModel model){
        this.model = model;
    }

    public void setKleur(Kleur kleur){
        this.kleur = kleur;
    }

    public Kleur getKleur() {
        return kleur;
    }

    public abstract void initialize();

}
