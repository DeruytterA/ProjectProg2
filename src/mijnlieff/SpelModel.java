package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import mijnlieff.Pionnen.*;

import java.util.*;
import java.util.function.Supplier;

public class SpelModel implements Observable {
    private int plaatsnu;

    private Speelveld speelveld;
    private Character[] soortenPionnen;
    private Kleur[] kleuren;
    private ArrayList<AlgemenePion> stappenlijst;

    private ArrayList<InvalidationListener> listeners;

    private Map<Kleur, ArrayList<AlgemenePion>> overigePionnen;
    private Map<Kleur, Map<Character, ArrayList<AlgemenePion>>> allePionnenMap;
    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap;
    private Kleur kleur;

    public SpelModel() {
        soortenPionnen = new Character[4];
        soortenPionnen[0] = '+';
        soortenPionnen[1] = 'X';
        soortenPionnen[2] = 'o';
        soortenPionnen[3] = '@';
        speelveld = new Speelveld(4, 4);
        allePionnenMap = new HashMap<>(){{
            put(Kleur.ZWART,
                    new HashMap<>(){{
                        put('o', new ArrayList<>());
                        put('X', new ArrayList<>());
                        put('@', new ArrayList<>());
                        put('+', new ArrayList<>());
                    }}
                    );
            put(Kleur.WIT,
                    new HashMap<>(){{
                        put('o', new ArrayList<>());
                        put('X', new ArrayList<>());
                        put('@', new ArrayList<>());
                        put('+', new ArrayList<>());
                    }}
                    );
        }};

        characterSupplierMap = new HashMap<>(){{
            put('+', Toren::new);
            put('X', Loper::new);
            put('@', Pusher::new);
            put('o', Puller::new);
        }};

        kleuren = new Kleur[2];
        kleuren[0] = Kleur.WIT;
        kleuren[1] = Kleur.ZWART;


        overigePionnen = new HashMap<>(){{
            put(Kleur.WIT, new ArrayList<>());
            put(Kleur.ZWART, new ArrayList<>());
        }};

        kleur = Kleur.WIT;
        listeners = new ArrayList<>();
        stappenlijst = new ArrayList<>();
        plaatsnu = 0;

        vulAllePionnen();
        vulZijkanten();

    }


    public void addListener(InvalidationListener var1){
        listeners.add(var1);
    }

    public void removeListener(InvalidationListener var1){
        listeners.remove(var1);
    }

    public void awakeListners(){
        listeners.forEach(o -> o.invalidated(this) );
    }

    public void vulAllePionnen(){
        for (Kleur speler:Kleur.values()) {
            for (Character character:soortenPionnen) {
                for (int i = 0; i < 2 ; i++) {
                    AlgemenePion pion = characterSupplierMap.get(character).get();
                    pion.setKleur(speler);
                    allePionnenMap.get(speler).get(character).add(pion);
                    pion.initialize();
                }
            }
        }
    }

    public void vulZijkanten(){
        for (Kleur kleur2:Kleur.values()) {
            for (Character soort: soortenPionnen) {
                overigePionnen.get(kleur2).addAll(allePionnenMap.get(kleur2).get(soort));
            }
        }
    }

    public void voegToeAanGrid(){
        AlgemenePion pion = stappenlijst.get(plaatsnu - 1);
        speelveld.add(pion);
        pion.opVeld();
        overigePionnen.get(pion.getKleur()).remove(pion);
    }

    public void verwijderUitGrid(int plaats){
        AlgemenePion pion = stappenlijst.get(plaats);
        speelveld.remove(pion);
        AlgemenePion legePion = new LegePion();
        legePion.setCoordinaten(pion.getXwaarde(), pion.getYwaarde());
        speelveld.add(legePion);
        legePion.initialize();
        overigePionnen.get(pion.getKleur()).add(pion);
        pion.aanRand();
    }

    public void parseStringToStap(String line){
        char type = line.charAt(8);
        int xWaarde = Character.getNumericValue(line.charAt(4));
        int yWaarde = Character.getNumericValue(line.charAt(6));
        AlgemenePion pion;
        ArrayList<AlgemenePion> pionLijst =  allePionnenMap.get(kleur).get(type);
        if (stappenlijst.contains(pionLijst.get(0))){
            pion = allePionnenMap.get(kleur).get(type).get(1);
        }else{
            pion = allePionnenMap.get(kleur).get(type).get(0);
        }
        pion.setModel(this);
        veranderKleur();
        pion.setCoordinaten(xWaarde, yWaarde);
        stappenlijst.add(pion);
    }

    public void veranderKleur(){
        if (kleur.equals(Kleur.WIT)){
            kleur = Kleur.ZWART;
        }else{
            kleur = Kleur.WIT;
        }
    }

    public void forwardAll(){
        for (int i = plaatsnu; i < stappenlijst.size();i++){
            plaatsnu++;
            voegToeAanGrid();
        }
        awakeListners();
    }

    public void forward(){
        plaatsnu++;
        voegToeAanGrid();
        awakeListners();
    }

    public void back(){
        plaatsnu--;
        verwijderUitGrid(plaatsnu);
        awakeListners();
    }

    public void backAll(){
       while(plaatsnu > 0){
           plaatsnu--;
           verwijderUitGrid(plaatsnu);
       }
        awakeListners();
    }

    public Speelveld getgrid() {
        return speelveld;
    }

    public Map<Kleur, ArrayList<AlgemenePion>> getOver(){
        return overigePionnen;
    }

    public int getPlaatsnu() {
        return plaatsnu;
    }

    public ArrayList<AlgemenePion> getStappenlijst() {
        return stappenlijst;
    }
}
