package mijnlieff.Pionnen;

import javafx.scene.image.Image;


public class Loper extends Pion {

    private char character = 'X';

    public Loper(boolean matchmaking){
        super(matchmaking);
    }

    public void initialize(){
        this.setImage(new Image("mijnlieff/Photos/" + kleur.toString().toLowerCase() + "-loper.png"));
    }

    @Override
    public boolean checkCoordinates(int x, int y){
        int grootsteAfstand = Math.abs(xwaarde) + Math.abs(ywaarde);
        if (Math.abs(xwaarde - 10) + Math.abs(ywaarde) > grootsteAfstand){
            grootsteAfstand = Math.abs(xwaarde - 10) + Math.abs(ywaarde);
        }
        if (Math.abs(xwaarde) + Math.abs(ywaarde - 10) > grootsteAfstand){
            grootsteAfstand = Math.abs(xwaarde) + Math.abs(ywaarde - 10);
        }
        if (Math.abs(xwaarde - 10) + Math.abs(ywaarde - 10) > grootsteAfstand){
            grootsteAfstand = Math.abs(xwaarde - 10) + Math.abs(ywaarde - 10);
        }
        grootsteAfstand = grootsteAfstand/2;
        int i = 0;
        while (i < grootsteAfstand && !(x == xwaarde + i && y == ywaarde + i) && !(x == xwaarde - i && y == ywaarde + i) && !(x == xwaarde + i && y == ywaarde - i) && !(x == xwaarde - i && y == ywaarde - i)){
            i ++;
        }
        return i < grootsteAfstand || (i == grootsteAfstand && ((x == xwaarde + i && y == ywaarde + i) || (x == xwaarde - i && y == ywaarde + i) || (x == xwaarde + i && y == ywaarde - i) || (x == xwaarde - i && y == ywaarde - i)));
    }

    @Override
    public char getCharacter() {
        return character;
    }
}
