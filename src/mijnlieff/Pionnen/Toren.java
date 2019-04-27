package mijnlieff.Pionnen;

import javafx.scene.image.Image;
import mijnlieff.Model.SpeelveldModel;

public class Toren extends Pion {

    private static char character = '+';

    public Toren(boolean matchmaking){
        super(matchmaking);
    }

    public Toren(boolean matchmaking, SpeelveldModel model){
        super(matchmaking, model);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-toren.png"));
    }
    @Override
    public boolean checkCoordinates(int x, int y){
        return true; //TODO
    }

    @Override
    public char getCharacter() {
        return character;
    }
}