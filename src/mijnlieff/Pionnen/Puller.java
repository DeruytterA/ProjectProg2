package mijnlieff.Pionnen;

import javafx.scene.image.Image;
import mijnlieff.Model.SpeelveldModel;

public class Puller extends Pion {

    private char character = 'o';

    public Puller(boolean matchmaking){
        super(matchmaking);
    }

    public Puller(boolean matchmaking, SpeelveldModel model){
        super(matchmaking, model);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-puller.png"));
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        return (xwaarde - 1 == x || xwaarde + 1 == x || xwaarde == x) && (ywaarde - 1 == y || ywaarde == y || ywaarde + 1 == y);
    }

    @Override
    public char getCharacter() {
        return character;
    }
}
