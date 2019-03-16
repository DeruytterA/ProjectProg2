package Spel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpelModel implements Observable {

    private ServerController server;
    private Controller controller;

    private ArrayList<AlgemenePion> stappenlijst;
    private ArrayList<AlgemenePion> witOver;
    private ArrayList<AlgemenePion> zwartOver;
    private ArrayList<InvalidationListener> listeners;
    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap = new HashMap<>();
    private Character[] soortenPionnen;

    private int plaatsnu;

    public SpelModel(ServerController server, Controller controller) {
        plaatsnu = 0;
        this.controller = controller;
        this.server = server;
        //het vullen van de lijst met soorten pionnen
        soortenPionnen = new Character[4];
        soortenPionnen[0] = '+';
        soortenPionnen[1] = 'X';
        soortenPionnen[2] = '@';
        soortenPionnen[3] = 'o';
        //het vullen van de hashmap van factory's
        characterSupplierMap.put('+', Toren::new);
        characterSupplierMap.put('X', Loper::new);
        characterSupplierMap.put('@', Pusher::new);
        characterSupplierMap.put('o', Puller::new);

        witOver = new ArrayList<>();
        zwartOver = new ArrayList<>();
        vulLijst(witOver);
        vulLijst(zwartOver);

        controller.setModel(this);
        server.setModel(this);
    }

    private void vulLijst(ArrayList<AlgemenePion> lijst){
        for (Character teken:soortenPionnen) {
            for (int j = 0; j < 2; j++) {
                lijst.add(characterSupplierMap.get(teken).get());
            }
        }
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
        char type = line.charAt(9);
        return characterSupplierMap.get(type).get();
    }

    public void forwardAll(){

    }

    public void forward(){

    }

    public void back(){

    }

    public void backAll(){

    }
}
