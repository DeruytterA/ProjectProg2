package mijnlieff;

public class Stap {
    private AlgemenePion pion;
    private int xWaarde;
    private int yWaarde;

    public Stap(AlgemenePion pion, int xWaarde, int yWaarde){
        this.pion = pion;
        this.xWaarde = xWaarde;
        this.yWaarde = yWaarde;
    }


    public AlgemenePion getPion() {
        return pion;
    }

    public int getxWaarde() {
        return xWaarde;
    }

    public int getyWaarde() {
        return yWaarde;
    }
}
