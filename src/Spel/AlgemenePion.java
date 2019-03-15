package Spel;

import javafx.scene.image.ImageView;

public class AlgemenePion extends ImageView {

    private int positieX;
    private int positieY;
    private boolean geplaatst;
    private int kleur;

    public AlgemenePion() {
        super();
    }

    public boolean isGeplaatst() {
        return geplaatst;
    }

    public void setGeplaatst(boolean geplaatst) {
        this.geplaatst = geplaatst;
    }

    public int getPositieX() {
        return positieX;
    }

    public void setPositieX(int positieX) {
        this.positieX = positieX;
    }

    public int getPositieY() {
        return positieY;
    }

    public void setPositieY(int positieY) {
        this.positieY = positieY;
    }

    public int getKleur() {
        return kleur;
    }

    public void setKleur(int kleur) {
        this.kleur = kleur;
    }

    public void play(int xPositie, int yPositie){

    }
}
