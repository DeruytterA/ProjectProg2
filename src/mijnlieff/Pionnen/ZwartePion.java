package mijnlieff.Pionnen;

public class ZwartePion extends Pion {

    private char character = ' ';

    public ZwartePion(boolean matchmaking) {
        super(matchmaking);
    }

    @Override
    public void initialize() {
        this.setImage(null);
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