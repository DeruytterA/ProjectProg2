package mijnlieff;

import javafx.scene.image.ImageView;

public abstract class AlgemenePion extends ImageView{

    protected SpelModel model;
    protected String kleur;
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
        this.setFitHeight(150);
        this.setFitWidth(150);
    }
    public void aanRand(){
        this.setFitHeight(75.0);
        this.setFitWidth(75.0);
    }

    public void setModel(SpelModel model){
        this.model = model;
    }

    public void setKleur(String kleur){
        this.kleur = kleur;
    }

    public String getKleur() {
        return kleur;
    }

    public abstract void initialize();
}
