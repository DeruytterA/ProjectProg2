package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Loper extends Pion {

    public Loper(boolean matchmaking){
        super(matchmaking);
    }

    public void initialize(){
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-loper.png"));
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        return true; //TODO
    }
}
