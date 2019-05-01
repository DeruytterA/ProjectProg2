package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class LegePion extends Pion {

    private char character = ' ';

    public LegePion(boolean matchmaking) {
        super(matchmaking);
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

    @Override
    public boolean checkCoordinates(int x, int y){
        return true;
    }

    @Override
    public char getCharacter() {
        return character;
    }
}
