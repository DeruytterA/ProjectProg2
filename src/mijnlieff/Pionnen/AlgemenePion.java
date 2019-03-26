package mijnlieff.Pionnen;

import javafx.scene.image.ImageView;
import mijnlieff.SpelModel;

public abstract class AlgemenePion extends ImageView{
    protected Boolean opVeld;
    protected SpelModel model;
    protected SpelModel.Kleur kleur;
    protected int xwaarde;
    protected int ywaarde;

    public AlgemenePion() {
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

    public void setKleur(SpelModel.Kleur kleur){
        this.kleur = kleur;
    }

    public SpelModel.Kleur getKleur() {
        return kleur;
    }

    public abstract void initialize();

}
