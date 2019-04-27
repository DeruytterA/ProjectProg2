package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.scene.control.ToggleButton;
import mijnlieff.CompanionClasses.Controllers.SpelbordkiezerCompanion;

public class MyToggleButton extends ToggleButton {

    private int xWaarde;
    private int yWaarde;
    private boolean inSelectie;
    private SpelbordkiezerCompanion.Selectie selectie;

    public MyToggleButton(int xWaarde, int yWaarde){
        super();
        this.xWaarde = xWaarde;
        this.yWaarde = yWaarde;
        inSelectie = false;
    }

    public int getxWaarde() {
        return xWaarde;
    }

    public int getyWaarde() {
        return yWaarde;
    }

    public void setSelectie(SpelbordkiezerCompanion.Selectie selectie){
        inSelectie = true;
        this.selectie = selectie;
    }

    public void verWijderSelectie(){
        inSelectie = false;
        this.selectie = null;
    }

    public boolean isInSelectie() {
        return inSelectie;
    }

    public SpelbordkiezerCompanion.Selectie getSelectie() {
        return selectie;
    }
}
