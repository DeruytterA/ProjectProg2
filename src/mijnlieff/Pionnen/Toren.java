package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Toren extends Pion {

    public Toren(boolean matchmaking){
        super(matchmaking);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-toren.png"));
    }
}