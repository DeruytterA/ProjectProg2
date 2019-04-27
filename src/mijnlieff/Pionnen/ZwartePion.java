package mijnlieff.Pionnen;

import javafx.scene.image.Image;

public class ZwartePion extends Pion {

    private char character = ' ';

    public ZwartePion(boolean matchmaking) {
        super(matchmaking);
    }

    @Override
    public void initialize() {
        this.setImage(new Image("mijnlieff/Photos/Zwart.png"));
        opVeld();
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        return false;//TODO
    }

    @Override
    public char getCharacter() {
        return character;
    }
}