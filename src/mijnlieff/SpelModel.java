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
    private ArrayList<Stap> stappenlijst;
    private VBox witOver;
    private VBox zwartOver;
    private ArrayList<Node> allePionnen;

    private ArrayList<Loper> zwarteLopers;
    private ArrayList<Puller> zwartePullers;
    private ArrayList<Pusher> zwartePuchers;
    private ArrayList<Toren> zwarteTorens;

    private ArrayList<Loper> witteLopers;
    private ArrayList<Puller> wittePullers;
    private ArrayList<Pusher> wittePuchers;
    private ArrayList<Toren> witteTorens;


    private ArrayList<InvalidationListener> listeners;

    private Map<Character, Supplier<AlgemenePion>> characterSupplierMap;
    private Character[] soortenPionnen;
    private String kleur;


    public SpelModel(Controller controller) {
        zwartePullers = new ArrayList<>();
        zwartePuchers = new ArrayList<>();
        zwarteLopers = new ArrayList<>();
        zwarteTorens = new ArrayList<>();

        wittePullers = new ArrayList<>();
        wittePuchers = new ArrayList<>();
        witteLopers = new ArrayList<>();
        witteTorens = new ArrayList<>();

        speelveld = new GridPane();
        speelveld = controller.getSpeelveld();
        kleur = "wit";
        characterSupplierMap = new HashMap<>();
        listeners = new ArrayList<>();
        stappenlijst = new ArrayList<>();
        plaatsnu = 0;

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
        allePionnen = new ArrayList<>();

        allePionnen.addAll(witOver.getChildren());
        allePionnen.addAll(zwartOver.getChildren());

        setLijst();
        controller.setModel(this);
        addListener(controller);
        awakeListners();
    }

    private VBox vulKolommen(String kleur){
        ArrayList<AlgemenePion> hulplijst = new ArrayList<>();
        for (Character teken:soortenPionnen) {
            for (int j = 0; j < 2; j++) {
                AlgemenePion pion = characterSupplierMap.get(teken).get();
                pion.setKleur(kleur);
                hulplijst.add(pion);
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
        stap.getPion().opVeld();
        if (stap.getPion().getKleur().equals("wit")){
            witOver.getChildren().remove(stap.getPion());
        }else{
            zwartOver.getChildren().remove(stap.getPion());
        }
    }

    public void verwijderUitGrid(){
        //TODO helemaal opnieuw maken
        speelveld = new GridPane();
        Stap stap = stappenlijst.get(plaatsnu);
        stap.getPion().aanRand();
        for (int i = 0; i + 1 < plaatsnu; i++){
            Stap stapi = stappenlijst.get(i);
            speelveld.add(stapi.getPion(), stapi.getxWaarde(), stapi.getyWaarde());
        }
        if (stap.getPion().getKleur().equals("wit")){
            witOver.getChildren().add(stap.getPion());
            stap.getPion().setFitHeight(75.0);
            stap.getPion().setFitWidth(75.0);
        }else{
            zwartOver.getChildren().add(stap.getPion());
            stap.getPion().setFitHeight(75.0);
            stap.getPion().setFitWidth(75.0);
        }
    }

    public void setLijst(){
        for (Node pion:allePionnen) {
            if (pion instanceof Loper){
                if(((Loper) pion).getKleur().equals("wit")){
                    witteLopers.add((Loper) pion);
                }else{
                    zwarteLopers.add((Loper) pion);
                }
            }else if (pion instanceof Puller){
                if(((Puller) pion).getKleur().equals("wit")){
                    wittePullers.add((Puller) pion);
                }else{
                    zwartePullers.add((Puller) pion);
                }
            }else if (pion instanceof Pusher){
                if(((Pusher) pion).getKleur().equals("wit")){
                    wittePuchers.add((Pusher) pion);
                }else{
                    zwartePuchers.add((Pusher) pion);
                }
            }else if (pion instanceof Toren){
                if(((Toren) pion).getKleur().equals("wit")){
                    witteTorens.add((Toren) pion);
                }else{
                    zwarteTorens.add((Toren) pion);
                }
            }
        }
    }

    public void parseStringToStap(String line){
        char type = line.charAt(8);
        int xWaarde = Character.getNumericValue(line.charAt(4));
        int yWaarde = Character.getNumericValue(line.charAt(6));
        AlgemenePion pion;
        if (type == '+'){
            if (kleur.equals("wit")){
                if (speelveld.getChildren().contains(witteTorens.get(0))){
                    pion = witteTorens.get(1);
                }else{
                    pion = witteTorens.get(0);
                }
            }else{
                if (speelveld.getChildren().contains(zwarteTorens.get(0))){
                    pion = zwarteTorens.get(1);
                }else{
                    pion = zwarteTorens.get(0);
                }
            }
        }else if (type == 'X'){
            if (kleur.equals("wit")){
                if (speelveld.getChildren().contains(witteLopers.get(0))){
                    pion = witteLopers.get(1);
                }else{
                    pion = witteLopers.get(0);
                }
            }else{
                if (speelveld.getChildren().contains(zwarteLopers.get(0))){
                    pion = zwarteLopers.get(1);
                }else{
                    pion = zwarteLopers.get(0);
                }
            }
        }else if (type == '@'){
            if (kleur.equals("wit")){
                if (speelveld.getChildren().contains(wittePuchers.get(0))){
                    pion = wittePuchers.get(1);
                }else{
                    pion = wittePuchers.get(0);
                }
            }else{
                if (speelveld.getChildren().contains(zwartePuchers.get(0))){
                    pion = zwartePuchers.get(1);
                }else{
                    pion = zwartePuchers.get(0);
                }
            }
        }else {
            if (kleur.equals("wit")){
                if (speelveld.getChildren().contains(wittePullers.get(0))){
                    pion = wittePullers.get(1);
                }else{
                    pion = wittePullers.get(0);
                }
            }else{
                if (speelveld.getChildren().contains(zwartePullers.get(0))){
                    pion = zwartePullers.get(1);
                }else{
                    pion = zwartePullers.get(0);
                }
            }
        }
        pion.setModel(this);
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

    public int getPlaatsnu() {
        return plaatsnu;
    }

    public ArrayList<Stap> getStappenlijst() {
        return stappenlijst;
    }
}
