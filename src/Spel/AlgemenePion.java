package Spel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.ImageView;

public class AlgemenePion extends ImageView implements InvalidationListener{

    private int positieX;
    private int positieY;
    private boolean geplaatst;
    private int kleur;

    public AlgemenePion() {
        super();
    }

    public void invalidated(Observable var1){

    }

    public void setPositieX(int x){
        positieX = x;
    }

    public void setPositieY(int y){
        positieY = y;
    }
}
