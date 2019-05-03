package mijnlieff.Model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mijnlieff.CompanionClasses.Controllers.ServerController;
import mijnlieff.Kleur;
import mijnlieff.Pionnen.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpeelveldModel implements Observable {

    private static final Kleur STARTKLEUR = Kleur.WIT;
    private static final Character[] soortenPionnen = new Character[]{'+', 'X', 'o', '@'};
    private static final Kleur[] kleuren = new Kleur[]{Kleur.WIT, Kleur.ZWART};

    private Pion[][] veld;
    private boolean[][] inSpelVeld;
    private Coordinaat[] bordconfiguratie;

    private boolean matchmaking;
    private boolean wachten;
    private boolean einde;
    private boolean quit;
    private Integer mijnPunten;
    private Integer tegenstanderPunten;
    private Kleur mijnKleur;
    private Integer plaatsnu;
    private ServerController serverController;
    private Pion laatstePion;
    private Pion teVerplaatsenPion;

    private ArrayList<InvalidationListener> listeners;
    private ObservableList<Pion> stappenlijst;

    private Map<Kleur, ArrayList<Pion>> overigePionnen;
    private Map<Kleur, Map<Character, ArrayList<Pion>>> allePionnenMap;
    private Map<Character, Supplier<Pion>> characterSupplierMap;
    private boolean valsGespeeld;

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

    public SpeelveldModel(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, boolean matchmaking, Model model){
        bordconfiguratie = new Coordinaat[]{
                new Coordinaat(x1, y1),
                new Coordinaat(x2, y2),
                new Coordinaat(x3, y3),
                new Coordinaat(x4, y4)
        };
        serverController = model.getServer();
        serverController.setSpeelveldModel(this);
        if (model.isMaakSpelbord()){
            wachten = true;
            mijnKleur = Kleur.ZWART;
        }else {
            wachten = false;
            mijnKleur = Kleur.WIT;
        }
        initialize(matchmaking);
    }

    public void initialize(boolean matchmaking){
        valsGespeeld = false;
        if (matchmaking){
            wachten = !mijnKleur.equals(STARTKLEUR);
            einde = false;

            veld = new Pion[11][11];
            inSpelVeld = new boolean[11][11];
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
                        Pion pion = new LegePion(true);
                        pion.setModel(this);
                        pion.setKleur(Kleur.LEEG);
                        veld[i][j] = pion;
                        pion.setCoordinaten(i, j);
                        pion.initialize();
                    }else{
                        Pion pion = new ZwartePion(true);
                        veld[i][j] = pion;
                        pion.setCoordinaten(i, j);
                        pion.initialize();
                    }
                }
            }
            laatstePion = new LegePion(true);
        }else {
            veld = new Pion[4][4];
            for (int i = 0; i < veld.length; i++) {
                for (int j = 0; j < veld[i].length; j++) {
                    Pion pion = new LegePion(false);
                    pion.setModel(this);
                    pion.setKleur(Kleur.LEEG);
                    veld[i][j] = pion;
                    pion.setCoordinaten(i, j);
                    pion.initialize();
                }
            }
            plaatsnu = 0;
        }

        mijnPunten = 0;
        tegenstanderPunten = 0;
        this.matchmaking = matchmaking;

        listeners = new ArrayList<>();

        stappenlijst = FXCollections.observableArrayList();

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
            put('+', () -> new Toren(matchmaking ));
            put('X', () -> new Loper(matchmaking));
            put('@', () -> new Pusher(matchmaking));
            put('o', () -> new Puller(matchmaking));
        }};

        overigePionnen = new HashMap<>(){{
            put(Kleur.WIT, new ArrayList<>());
            put(Kleur.ZWART, new ArrayList<>());
        }};

        vulAllePionnen();
        vulZijkanten();
        awakeListners();
    }

    public SpeelveldModel(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, boolean matchmaking) {
        bordconfiguratie = new Coordinaat[]{
                new Coordinaat(x1, y1),
                new Coordinaat(x2, y2),
                new Coordinaat(x3, y3),
                new Coordinaat(x4, y4)
        };
        initialize(matchmaking);
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
        for (Kleur speler:kleuren) {
            for (Character character:soortenPionnen) {
                for (int i = 0; i < 2 ; i++) {
                    Pion pion = characterSupplierMap.get(character).get();
                    pion.setModel(this);
                    pion.setKleur(speler);
                    allePionnenMap.get(speler).get(character).add(pion);
                    pion.initialize();
                }
            }
        }
    }

    public void vulZijkanten(){
        for (Kleur kleur2:kleuren) {
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
            stappenlijst.add(pion);
            stuurZet(x,y,pion);
            awakeListners();
        }
    }

    public void add(Pion pion){
        wachten = false;
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

    public Pion parseStringToPion(String line, Kleur kleur){
        String[] gesplitteLijn = line.split(" ");
        char type = gesplitteLijn[4].charAt(0);
        int xWaarde = Integer.parseInt(gesplitteLijn[2]);
        int yWaarde = Integer.parseInt(gesplitteLijn[3]);
        Pion pion;
        ArrayList<Pion> pionLijst =  allePionnenMap.get(kleur).get(type);
        if (stappenlijst.contains(pionLijst.get(0))){
            pion = allePionnenMap.get(kleur).get(type).get(1);
        }else{
            pion = allePionnenMap.get(kleur).get(type).get(0);
        }
        pion.setModel(this);
        stappenlijst.add(pion);
        pion.setCoordinaten(xWaarde, yWaarde);
        return pion;
    }

    public void inputlijstToStappenLijst(ArrayList<String> lijst){
        Kleur kleur = Kleur.WIT;
        for (String lijn:lijst) {
            stappenlijst.add(parseStringToPion(lijn, kleur));
            kleur = veranderKleur(kleur);
        }
    }

    public int getPlaatsnu(){
        return plaatsnu;
    }

    public Kleur veranderKleur(Kleur kleur){
        if (kleur.equals(Kleur.WIT)){
            return Kleur.ZWART;
        }else{
            return Kleur.WIT;
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

    public Kleur getMijnKleur(){
        return mijnKleur;
    }

    public void parseZetTegenstander(String string){
        if (string.length() == 1){
            if (string.charAt(0) == 'Q'){
                quit = true;
                awakeListners();
            }else if (string.charAt(0) == 'X'){
                System.out.println("tegenstander heeft stap overgeslaan");
                checkSpelGedaan(mijnKleur);
                wachten = false;
                laatstePion = new LegePion(matchmaking);
            }
        }else {
            System.out.println("tegenstander heeft: " + string + " gestuurd");
            Pion pion = parseStringToPion(string, veranderKleur(mijnKleur));
            if (laatstePion.checkCoordinates(pion.getXwaarde(), pion.getYwaarde())){
                System.out.println("pion van tegenstander is een geldige zet");
                add(pion);
                overigePionnen.get(pion.getKleur()).remove(pion);
                laatstePion = pion;
                wachten = false;
                checkSpelGedaan(mijnKleur);
                if (!isMogelijkezet()){
                    System.out.println("ik kan geen geldige zet doen ik sla mijn stap over");
                    slaStapOver();
                }else {
                    System.out.println("ik kan wel een geldige zet doen");
                }
            }else {
                valsGespeeld = true;
            }
        }
        awakeListners();
    }

    private boolean isMogelijkezet() {
        System.out.println("ik check of ik nog een geldige zet kan doen");
        ArrayList<Pion> lijst = new ArrayList<>();
        for (Coordinaat coordinaat:bordconfiguratie) {
            if (laatstePion.checkCoordinates(coordinaat.getX(), coordinaat.getY())){
                Pion pion = veld[coordinaat.getX()][coordinaat.getY()];
                if (pion instanceof LegePion){
                    lijst.add(pion);
                }
            }
            if (laatstePion.checkCoordinates(coordinaat.getX() + 1, coordinaat.getY())){
                Pion pion = veld[coordinaat.getX() + 1][coordinaat.getY()];
                if (pion instanceof LegePion){
                    lijst.add(pion);
                }
            }
            if (laatstePion.checkCoordinates(coordinaat.getX(), coordinaat.getY() + 1)){
                Pion pion = veld[coordinaat.getX()][coordinaat.getY() + 1];
                if (pion instanceof LegePion){
                    lijst.add(pion);
                }
            }
            if (laatstePion.checkCoordinates(coordinaat.getX() + 1, coordinaat.getY() + 1)){
                Pion pion = veld[coordinaat.getX() + 1][coordinaat.getY() + 1];
                if (pion instanceof LegePion){
                    lijst.add(pion);
                }
            }
        }
        System.out.println("kan ik een geldige zet doen?: " + (lijst.size() > 0));
        return lijst.size() > 0;
    }

    public void checkSpelGedaan(Kleur kleur) {
        if (overigePionnen.get(kleur).size() == 0){
            einde = true;
            berekenPunten();
        }
    }

    private int addPunten(int aantal){
        return aantal - 2;
    }

    private void berekenPunten() {
        mijnPunten = 0;
        tegenstanderPunten = 0;
        for (int i = 0; i < veld.length; i++) {
            int mijnPionnenDezeRij = 0;
            int tegenstanderPionnenDezeRij = 0;
            int mijnPionnenKolom = 0;
            int tegenstanderPionnenKolom = 0;

            for (int j = 0; j < veld[i].length ; j++) {
                if (inSpelVeld[i][j]){
                    Pion pion = veld[i][j];
                    if (pion.getKleur().equals(mijnKleur)){
                        mijnPionnenDezeRij++;
                    }else if (pion.getKleur().equals(veranderKleur(mijnKleur))){
                        tegenstanderPionnenDezeRij++;
                    }
                }
                if (inSpelVeld[j][i]){
                    Pion pion = veld[j][i];
                    if (pion.getKleur().equals(mijnKleur)){
                        mijnPionnenKolom++;
                    }else if (pion.getKleur().equals(veranderKleur(mijnKleur))){
                        tegenstanderPionnenKolom++;
                    }
                }
            }

            if (mijnPionnenKolom > 2){
                mijnPunten += addPunten(mijnPionnenKolom);
            }
            if (tegenstanderPionnenKolom > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnenKolom);
            }
            if (mijnPionnenDezeRij > 2){
                mijnPunten += addPunten(mijnPionnenDezeRij);
            }
            if (tegenstanderPionnenDezeRij > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnenDezeRij);
            }
        }//berken punten van de rijen en de kolommen

        for (int i = veld.length - 1; i > 0; i--) {

            int mijnpionnen = 0;
            int tegenstanderPionnen = 0;

            for (int j = 0, x = i; x <= veld.length - 1; j++, x++) {
                if (inSpelVeld[x][j]){
                    Pion pion = veld[x][j];
                    if (pion.getKleur().equals(mijnKleur)){
                        mijnpionnen++;
                    }else if (pion.getKleur().equals(veranderKleur(mijnKleur))){
                        tegenstanderPionnen++;
                    }
                }
            }
            if (mijnpionnen > 2){
                mijnPunten += addPunten(mijnpionnen);
            }
            if (tegenstanderPionnen > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnen);
            }
        }//bereken punten van hoofdDiagonaal onder

        for (int i = 0; i <= veld.length - 1; i++) {

            int mijnpionnen = 0;
            int tegenstanderPionnen = 0;

            for (int j = 0, y = i; y <= veld.length - 1; j++, y++) {
                if (inSpelVeld[j][y]){
                    Pion pion = veld[j][y];
                    if (!pion.getKleur().equals(mijnKleur)){
                        tegenstanderPionnen++;
                    }else if (pion.getKleur().equals(veranderKleur(mijnKleur))){
                        mijnpionnen++;
                    }
                }
            }
            if (mijnpionnen > 2){
                mijnPunten += addPunten(mijnpionnen);
            }
            if (tegenstanderPionnen > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnen);
            }
        }//bereken punten van hoofdDiagonaal boven

        for( int k = 0 ; k < veld.length ; k++ ) {
            int mijnPionnnen = 0;
            int tegenstanderPionnen = 0;
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if (inSpelVeld[i][j]){
                    if (veld[i][j].getKleur().equals(mijnKleur)){
                        mijnPionnnen++;
                    }else if (veld[i][j].getKleur().equals(veranderKleur(mijnKleur))){
                        tegenstanderPionnen++;
                    }
                }
            }
            if (mijnPionnnen > 2){
                mijnPunten += addPunten(mijnPionnnen);
            }
            if (tegenstanderPionnen > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnen);
            }
        } //bereken punten van zijDiagonaal boven

        for( int k = veld.length - 2 ; k >= 0 ; k-- ) {
            int mijnPionnnen = 0;
            int tegenstanderPionnen = 0;
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if (inSpelVeld[veld.length - j - 1][veld.length - i - 1]){
                    if (inSpelVeld[i][j]){
                        if (veld[i][j].getKleur().equals(mijnKleur)){
                            mijnPionnnen++;
                        }else if (veld[i][j].getKleur().equals(veranderKleur(mijnKleur))){
                            tegenstanderPionnen++;
                        }
                    }
                }
            }
            if (mijnPionnnen > 2){
                mijnPunten += addPunten(mijnPionnnen);
            }
            if (tegenstanderPionnen > 2){
                tegenstanderPunten += addPunten(tegenstanderPionnen);
            }
        }//bereken punten van zijDiagonaal onder
    }

    public void stuurZet(int x, int y, Pion pion) {
        wachten = true;
        String teVersturen = "X ";
        if (einde){
            teVersturen += "T ";
        }else {
            teVersturen += "F ";
        }
        teVersturen += x + " " + y + " ";
        teVersturen += pion.getCharacter();
        serverController.stuurZet(teVersturen);
        checkSpelGedaan(veranderKleur(mijnKleur));
        if (!einde){
            serverController.ontvangZet();
        }
        awakeListners();
    }

    public boolean isWachten() {
        return wachten;
    }

    public boolean isEinde() {
        return einde;
    }

    public boolean isQuit(){
        return quit;
    }

    public void slaStapOver(){
        wachten = true;
        System.out.println("ik stuur een x");
        serverController.stuurZet("X");
        checkSpelGedaan(veranderKleur(mijnKleur));
        laatstePion = new LegePion(matchmaking);
        if (!einde){
            serverController.ontvangZet();
        }
        awakeListners();
    }

    public Integer getMijnPunten(){
        return mijnPunten;
    }

    public Integer getTegenstanderPunten(){
        return tegenstanderPunten;
    }

    public ObservableList getStappenlijst(){
        return stappenlijst;
    }

    public boolean isValsGespeeld() {
        return valsGespeeld;
    }
}