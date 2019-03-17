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
