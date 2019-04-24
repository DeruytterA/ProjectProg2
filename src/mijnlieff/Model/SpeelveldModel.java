package mijnlieff.Model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import mijnlieff.Kleur;
import mijnlieff.Pionnen.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpeelveldModel implements Observable {

    private ArrayList<InvalidationListener> listeners;
    private Pion[][] veld;
    private boolean[][] inSpelVeld;
    private Coordinaat[] bordconfiguratie;
    private boolean matchmaking;
    private Map<Kleur, ArrayList<Pion>> overigePionnen;
    private Pion laatstePion;
    private boolean wachten;
    private Pion teVerplaatsenPion;
    private Map<Kleur, Map<Character, ArrayList<Pion>>> allePionnenMap;
    private Map<Character, Supplier<Pion>> characterSupplierMap;
    private Character[] soortenPionnen;
    private ArrayList<Pion> stappenlijst;
    private Kleur kleur;
    private Integer plaatsnu;

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

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinaat){
                return this.x == ((Coordinaat) obj).getX() && this.y == ((Coordinaat) obj).getY();
            }
            return false;
        }
    }

    public SpeelveldModel(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, boolean matchmaking) {
        this.matchmaking = matchmaking;
        listeners = new ArrayList<>();
        bordconfiguratie = new Coordinaat[]{
                new Coordinaat(x1, y1),
                new Coordinaat(x2, y2),
                new Coordinaat(x3, y3),
                new Coordinaat(x4, y4)
        };

        overigePionnen = new HashMap<>(){{
            put(Kleur.WIT, new ArrayList<>());
            put(Kleur.ZWART, new ArrayList<>());
        }};

        soortenPionnen = new Character[]{'+', 'X', 'o', '@'};

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
            put('+', () -> new Toren(matchmaking));
            put('X', () -> new Loper(matchmaking));
            put('@', () -> new Pusher(matchmaking));
            put('o', () -> new Puller(matchmaking));
        }};

        overigePionnen = new HashMap<>(){{
            put(Kleur.WIT, new ArrayList<>());
            put(Kleur.ZWART, new ArrayList<>());
        }};

        initializeVeld();
        for (int j = 0; j < veld.length; j++) {
            for (int i = 0; i <veld[j].length ; i++) {
                Pion pion = new LegePion(matchmaking);
                veld[j][i] = pion;
                pion.setCoordinaten(j, i);
                pion.initialize();
            }
        }
        vulAllePionnen();
        vulZijkanten();
        awakeListners();
    }

    public void initializeVeld(){
        Integer[] grootsten = grootste();
        veld = new Pion[grootsten[0]][grootsten[1]];
        inSpelVeld = new boolean[grootsten[0]][grootsten[1]];
        for (boolean[] boolarr:inSpelVeld) {
            Arrays.fill(boolarr, false); //vul de volledige array met false om later de juiste waarden op true te plaatsen
        }
        for (Coordinaat coordinaat:bordconfiguratie) {
            inSpelVeld[coordinaat.getX()][coordinaat.getY()] = true;
            inSpelVeld[coordinaat.getX() + 1][coordinaat.getY()] = true;
            inSpelVeld[coordinaat.getX()][coordinaat.getY() + 1] = true;
            inSpelVeld[coordinaat.getX() + 1][coordinaat.getY() + 1] = true;
        }

        for (int i = 0; i < veld.length; i++) {
            for (int j = 0; j < veld[i].length; j++) {
                if (inSpelVeld[i][j]){
                    Pion pion = new LegePion(matchmaking);
                    veld[i][j] = pion;
                    pion.setCoordinaten(i, j);
                    pion.initialize();
                }else{
                    //TODO vul met pionnen die aanduiden dat het niet bruikbaar is.
                }
            }
        }
    }

    public Integer[] grootste(){
        int x = 0;
        int y = 0;
        for (Coordinaat coordinaat:bordconfiguratie) {
            if (coordinaat.getX() > x){
                x = coordinaat.getX();
            }
            if (coordinaat.getY() > y){
                y = coordinaat.getY();
            }
        }
        return new Integer[]{x + 1, y + 1}; //moet plus 1 omdat het 2x2 velden zijn
    }

    public void addListener(InvalidationListener var1){
        listeners.add(var1);
    }

    public void removeListener(InvalidationListener var1){
        listeners.remove(var1);
    }

    public void awakeListners(){
        listeners.forEach(o -> o.invalidated(this));
    }

    public void vulAllePionnen(){
        for (Kleur speler:Kleur.values()) {
            for (Character character:soortenPionnen) {
                for (int i = 0; i < 2 ; i++) {
                    Pion pion = characterSupplierMap.get(character).get();
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

    public void verplaatsPionNaar(int x, int y, Pion pion){
        if (laatstePion.checkCoordinates(x,y)){
            pion.setCoordinaten(x, y);
            add(pion);
            laatstePion = pion;
            teVerplaatsenPion = null;
            overigePionnen.get(pion.getKleur()).remove(pion);
            awakeListners();
        }
    }

    public void add(Pion pion){
        veld[pion.getXwaarde()][pion.getYwaarde()] = pion;
        awakeListners();
    }

    public void remove(Pion pion){
        if (pion.equals(veld[pion.getXwaarde()][pion.getYwaarde()])) {
            veld[pion.getXwaarde()][pion.getYwaarde()] = new LegePion(matchmaking);
            awakeListners();
        }
    }

    public Pion[][] getVeld(){
        return veld;
    }

    public Map<Kleur, ArrayList<Pion>> getOverigePionnen() {
        return overigePionnen;
    }

    public Pion getTeVerplaatsenPion() {
        return teVerplaatsenPion;
    }

    public void setTeVerplaatsenPion(Pion teVerplaatsenPion) {
        this.teVerplaatsenPion = teVerplaatsenPion;
        awakeListners();
    }

    public void voegToeAanGrid(){
        Pion pion = stappenlijst.get(plaatsnu - 1);
        add(pion);
        pion.opVeld();
        overigePionnen.get(pion.getKleur()).remove(pion);
    }

    public void verwijderUitGrid(){
        Pion pion = stappenlijst.get(plaatsnu);
        remove(pion);
        Pion legePion = new LegePion(matchmaking);
        legePion.setCoordinaten(pion.getXwaarde(), pion.getYwaarde());
        add(legePion);
        legePion.initialize();
        overigePionnen.get(pion.getKleur()).add(pion);
        pion.aanRand();
    }

    public Pion parseStringToPion(String line){
        char type = line.charAt(8);
        int xWaarde = Character.getNumericValue(line.charAt(4));
        int yWaarde = Character.getNumericValue(line.charAt(6));
        Pion pion;
        ArrayList<Pion> pionLijst =  allePionnenMap.get(kleur).get(type);
        if (stappenlijst.contains(pionLijst.get(0))){
            pion = allePionnenMap.get(kleur).get(type).get(1);
        }else{
            pion = allePionnenMap.get(kleur).get(type).get(0);
        }
        pion.setModel(this);
        veranderKleur();
        pion.setCoordinaten(xWaarde, yWaarde);
        laatstePion = pion;
        return pion;
    }

    public void parseStringToStap(String line){
        stappenlijst.add(parseStringToPion(line));
    }

    public int getAantalStappen(){
        return stappenlijst.size();
    }

    public int getPlaatsnu(){
        return plaatsnu;
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
        verwijderUitGrid();
        awakeListners();
    }

    public void backAll(){
        while(plaatsnu > 0){
            plaatsnu--;
            verwijderUitGrid();
        }
        awakeListners();
    }

}
