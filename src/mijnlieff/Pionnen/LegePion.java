package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class LegePion extends Pion {

    public LegePion() {
        super();
    }

    @Override
    public void initialize() {
        if ((xwaarde + ywaarde) % 2 == 0){
            this.setImage(new Image("mijnlieff/Photos/Achtergrond2.png"));
        }else {
            this.setImage(new Image("mijnlieff/Photos/Achtergrond1.png"));
        }
        opVeld();
    }
}
