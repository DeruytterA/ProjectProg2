package mijnlieff.CompanionClasses.Controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import mijnlieff.Model.Model;

public abstract class MyController implements InvalidationListener {

    protected Model model;

    public void setModel(Model model){
        this.model = model;
    }

    public abstract void invalidated(Observable var1);

}
