package mijnlieff;

import javafx.scene.image.Image;

public class Pusher extends AlgemenePion {

    public Pusher(){
        super();
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur + "-pusher.png"));
    }
}
