package mijnlieff;

import mijnlieff.Pionnen.AlgemenePion;
import mijnlieff.Pionnen.LegePion;

public class Speelveld {

    private AlgemenePion[][] veld;

    public Speelveld(int xAs, int yAs) {
        veld = new AlgemenePion[xAs][yAs];
        for (int j = 0; j < veld.length; j++) {
            for (int i = 0; i <veld[j].length ; i++) {
                AlgemenePion pion = new LegePion();
                veld[j][i] = pion;
                pion.setCoordinaten(j,i);
                pion.initialize();
            }
        }
        System.out.println(veld);
    }

    public void add(AlgemenePion pion){
        veld[pion.getXwaarde()][pion.getYwaarde()] = pion;
    }

    public void remove(AlgemenePion pion){
        if (pion.equals(veld[pion.getXwaarde()][pion.getYwaarde()])) {
            LegePion legePion = new LegePion();
            veld[pion.getXwaarde()][pion.getYwaarde()] = legePion;
        }
    }

    public AlgemenePion[][] getVeld(){
        return veld;
    }
}
