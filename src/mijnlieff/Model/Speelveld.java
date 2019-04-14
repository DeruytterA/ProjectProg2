package mijnlieff.Model;

import mijnlieff.Pionnen.Pion;
import mijnlieff.Pionnen.LegePion;

public class Speelveld {

    private Pion[][] veld;
    private Coordinaat[] bordconfiguratie;

    public class Coordinaat{
        private int x;
        private int y;

        public Coordinaat(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Integer[] getcoordinaat(){
            Integer[] output = new Integer[2];
            output[0] = x;
            output[1] = y;
            return output;
        }
    }

    public Speelveld(int xAs, int yAs) {
        veld = new Pion[xAs][yAs];
        for (int j = 0; j < veld.length; j++) {
            for (int i = 0; i <veld[j].length ; i++) {
                Pion pion = new LegePion();
                veld[j][i] = pion;
                pion.setCoordinaten(j, i);
                pion.initialize();
            }
        }
    }

    public void add(Pion pion){
        veld[pion.getXwaarde()][pion.getYwaarde()] = pion;
    }

    public void remove(Pion pion){
        if (pion.equals(veld[pion.getXwaarde()][pion.getYwaarde()])) {
            veld[pion.getXwaarde()][pion.getYwaarde()] = new LegePion();
        }
    }

    public Pion[][] getVeld(){
        return veld;
    }
}
