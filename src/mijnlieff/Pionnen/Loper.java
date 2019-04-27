package mijnlieff.Pionnen;

import javafx.scene.image.Image;
import mijnlieff.Model.SpeelveldModel;

public class Loper extends Pion {

    private char character = 'X';

    public Loper(boolean matchmaking){
        super(matchmaking);
    }

    public Loper(boolean matchmaking, SpeelveldModel model){
        super(matchmaking, model);
    }

    public void initialize(){
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-loper.png"));
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
