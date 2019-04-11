package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import mijnlieff.Model.SpelModel;

public abstract class MyController implements InvalidationListener {

    protected SpelModel model;

    public void setModel(SpelModel model){
        this.model = model;
    }

    public abstract void invalidated(Observable var1);

}
