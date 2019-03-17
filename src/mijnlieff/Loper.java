package mijnlieff;

import javafx.scene.image.Image;

public class Loper extends AlgemenePion {

    public Loper(){
        super();
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur + "-loper.png"));
    }
}
