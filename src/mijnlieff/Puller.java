package mijnlieff;

import javafx.scene.image.Image;

public class Puller extends AlgemenePion {

    public Puller(){
        super();
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur + "-puller.png"));
    }
}
