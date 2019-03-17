package mijnlieff;

import javafx.scene.image.ImageView;

public abstract class AlgemenePion extends ImageView{

    protected SpelModel model;
    protected String kleur;

    public AlgemenePion() {
        super();
        this.setFitHeight(70.0);
        this.setPickOnBounds(true);
        this.setPreserveRatio(true);
        this.setFitHeight(75.0);
        this.setFitWidth(75.0);
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
