package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Pusher extends Pion {

    public Pusher(boolean matchmaking){
        super(matchmaking);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-pusher.png"));
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        return true; //TODO
    }
}
