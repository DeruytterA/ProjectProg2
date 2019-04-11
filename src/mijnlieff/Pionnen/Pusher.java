package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class Pusher extends Pion {

    public Pusher(){
        super();
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-pusher.png"));
    }
}
