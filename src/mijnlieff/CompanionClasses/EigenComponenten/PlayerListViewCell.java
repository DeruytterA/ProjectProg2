package mijnlieff.CompanionClasses.EigenComponenten;

import javafx.scene.control.ListCell;

public class PlayerListViewCell extends ListCell<String> {

    @Override
    protected void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        setText(string);
    }
}
