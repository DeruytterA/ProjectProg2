package Spel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpelModel implements Observable {


    private ArrayList<AlgemenePion> stappenlijst = new ArrayList<>();
    private ArrayList<AlgemenePion> witOver = new ArrayList<>();
    private ArrayList<AlgemenePion> zwartOver = new ArrayList<>();
    private ArrayList<InvalidationListener> listeners = new ArrayList<>();
    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap = new HashMap<>();

    public SpelModel() {
        characterSupplierMap.put('+', Toren::new);
        characterSupplierMap.put('X', Loper::new);
        characterSupplierMap.put('@', Pusher::new);
        characterSupplierMap.put('o', Puller::new);
    }

    public void update(String line){
        AlgemenePion pion = parseStringToPion(line);
        stappenlijst.add(pion);
    }

    public void addListener(InvalidationListener var1){
        listeners.add(var1);
    }

    public void removeListener(InvalidationListener var1){
        listeners.remove(var1);
    }

    public AlgemenePion parseStringToPion(String line){
        int rij = Character.getNumericValue(line.charAt(5));
        int kolom = Character.getNumericValue(line.charAt(7));
        char type = line.charAt(9);
        AlgemenePion pion = characterSupplierMap.get(type).get();
        pion.setPositieX(rij);
        pion.setPositieY(kolom);
        return pion;
    }
}
