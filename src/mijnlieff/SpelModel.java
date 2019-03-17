package mijnlieff;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.function.Supplier;

public class SpelModel implements Observable {

    private int plaatsnu;
    private Controller controller;

    private GridPane speelveld;

    private ArrayList<Stap> stappenlijst;
    private VBox witOver;
    private VBox zwartOver;
    private ArrayList<InvalidationListener> listeners;
    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap;
    private Character[] soortenPionnen;
    private String kleur;


    public SpelModel(Controller controller) {
        speelveld = new GridPane();
        speelveld = controller.getSpeelveld();
        kleur = "wit";
        characterSupplierMap = new HashMap<>();
        listeners = new ArrayList<>();
        stappenlijst = new ArrayList<>();
        plaatsnu = 0;
        this.controller = controller;

        //het vullen van de hashmap van factory's
        characterSupplierMap.put('+', Toren::new);
        characterSupplierMap.put('X', Loper::new);
        characterSupplierMap.put('@', Pusher::new);
        characterSupplierMap.put('o', Puller::new);

        Object[] objects = characterSupplierMap.keySet().toArray();
        soortenPionnen = new Character[objects.length];
        for (int i = 0; i < objects.length; i++){
            soortenPionnen[i] = objects[i].toString().charAt(0);
        }

        witOver = vulKolommen("wit");
        zwartOver = vulKolommen("zwart");

        controller.setModel(this);
        addListener(controller);
        awakeListners();
    }

    private VBox vulKolommen(String kleur){
        ArrayList<AlgemenePion> hulplijst = new ArrayList<>();
        for (Character teken:soortenPionnen) {
            for (int j = 0; j < 2; j++) {
                hulplijst.add(characterSupplierMap.get(teken).get());
            }
        }
        for (AlgemenePion pion:hulplijst) {
            pion.setKleur(kleur);
            pion.setModel(this);
            pion.initialize();
        }
        return new VBox(hulplijst.toArray(new AlgemenePion[hulplijst.size()]));
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

    public void voegToeAanGrid(){
        Stap stap = stappenlijst.get(plaatsnu - 1);
        speelveld.add(stap.getPion(), stap.getxWaarde(), stap.getyWaarde());
        //TODO verwijderen uit VBox
    }

    public void verwijderUitGrid(){
        speelveld = new GridPane();;
        for (int i = 0; i < plaatsnu; i++){
            voegToeAanGrid();
        }
        //TODO toevoegen aan VBOX
    }

    public void parseStringToPion(String line){
        char type = line.charAt(8);
        int xWaarde = Character.getNumericValue(line.charAt(4));
        int yWaarde = Character.getNumericValue(line.charAt(6));
        AlgemenePion pion = characterSupplierMap.get(type).get();
        pion.setKleur(kleur);
        pion.setModel(this);
        pion.initialize();
        veranderKleur();
        stappenlijst.add(new Stap(pion, xWaarde, yWaarde));
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
        verwijderUitGrid();
        awakeListners();
    }

    public void backAll(){
        plaatsnu = 0;
        verwijderUitGrid();
        awakeListners();
    }


    public GridPane getgrid() {
        return speelveld;
    }

    public VBox[] getOver(){
        VBox[] data = new VBox[2];
        data[0] = witOver;
        data[1] = zwartOver;
        return data;
    }
}
