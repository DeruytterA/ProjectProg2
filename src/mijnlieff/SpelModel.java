package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.function.Supplier;

public class SpelModel implements Observable {

    private int plaatsnu;

    private GridPane speelveld;
    private Character[] soortenPionnen;
    private String[] kleuren;
    private ArrayList<AlgemenePion> stappenlijst;

    private ArrayList<InvalidationListener> listeners;

    private Map<String, VBox> overigePionnen;
    private Map<String, Map<Character, ArrayList<AlgemenePion>>> allePionnenMap;
    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap;
    private String kleur;


    public SpelModel(Controller controller) {

        soortenPionnen = new Character[4];
        soortenPionnen[0] = '+';
        soortenPionnen[1] = 'X';
        soortenPionnen[2] = 'o';
        soortenPionnen[3] = '@';

        allePionnenMap = new HashMap<>(){{
            put("zwart",
                    new HashMap<>(){{
                        put('o', new ArrayList<>());
                        put('X', new ArrayList<>());
                        put('@', new ArrayList<>());
                        put('+', new ArrayList<>());
                    }}
                    );
            put("wit",
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

        kleuren = new String[2];
        kleuren[0] = "wit";
        kleuren[1] = "zwart";


        overigePionnen = new HashMap<>(){{
            put("wit", new VBox());
            put("zwart", new VBox());
        }};

        speelveld = new GridPane();
        kleur = "wit";
        listeners = new ArrayList<>();
        stappenlijst = new ArrayList<>();
        plaatsnu = 0;

        vulAllePionnen();
        vulZijkanten("wit");
        vulZijkanten("zwart");

        controller.setModel(this);
        addListener(controller);
        awakeListners();
    }


    public void addListener(InvalidationListener var1){
        listeners.add(var1);
    }

    public void removeListener(InvalidationListener var1){
        listeners.remove(var1);
    }

    public void awakeListners(){
        for (InvalidationListener listner:listeners) {
            listner.invalidated(this);

        }
    }

    public void vulAllePionnen(){
        for (String speler:kleuren) {
            for (Character character:soortenPionnen) {
                for (int i = 0; i < 2 ; i++) {
                    AlgemenePion pion = characterSupplierMap.get(character).get();
                    pion.setKleur(speler);
                    allePionnenMap.get(speler).get(character).add(pion);
                }
            }
        }
    }

    public void vulZijkanten(String kleur){
        for (Character soort: soortenPionnen) {
            overigePionnen.get(kleur).getChildren().addAll(allePionnenMap.get(kleur).get(soort));
        }
    }

    public void voegToeAanGrid(){
        AlgemenePion pion = stappenlijst.get(plaatsnu - 1);
        speelveld.add(pion, pion.getXwaarde(), pion.getYwaarde());
        pion.opVeld();
        overigePionnen.get(pion.getKleur()).getChildren().remove(pion);
    }

    public void verwijderUitGrid(int plaats){
        AlgemenePion pion = stappenlijst.get(plaats);
        speelveld.getChildren().remove(pion);
        overigePionnen.get(pion.getKleur()).getChildren().add(pion);
        pion.aanRand();
    }

    public void parseStringToStap(String line){
        char type = line.charAt(8);
        int xWaarde = Character.getNumericValue(line.charAt(4));
        int yWaarde = Character.getNumericValue(line.charAt(6));
        AlgemenePion pion;
        if (stappenlijst.contains(allePionnenMap.get(kleur).get(type).get(0))){
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
        if (kleur.equals("wit")){
            kleur = "zwart";
        }else{
            kleur = "wit";
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
        plaatsnu = 0;
        for (int i=0; i< stappenlijst.size();i++){
            verwijderUitGrid(i);
        }
        awakeListners();
    }

    public GridPane getgrid() {
        return speelveld;
    }

    public Map<String, VBox> getOver(){
        return overigePionnen;
    }

    public int getPlaatsnu() {
        return plaatsnu;
    }

    public ArrayList<AlgemenePion> getStappenlijst() {
        return stappenlijst;
    }
}
