package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Toren extends AlgemenePion {

    public Toren(){
        super();
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-toren.png"));
    }
}