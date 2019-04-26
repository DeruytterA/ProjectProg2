package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Puller extends Pion {

    public Puller(boolean matchmaking){
        super(matchmaking);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-puller.png"));
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        return true; //TODO
    }
}
