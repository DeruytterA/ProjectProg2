package mijnlieff.Model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import mijnlieff.Kleur;
import mijnlieff.Pionnen.*;
import mijnlieff.CompanionClasses.Controllers.ServerController;

import java.util.*;
import java.util.function.Supplier;

public class Model implements Observable {

    private boolean einde;
    private boolean matchmaking;
    private boolean spelBordKiezer;
    private boolean eerste;
    private boolean nickname;
    private boolean serverAan;
    private boolean spelStartBool;
    private int plaatsnu;
    private boolean inLijst;
    private String nicknameString;
    private String tegenstander;
    private Pion teVerplaatsenPion;

    private SpeelveldModel speelveld;

    private ArrayList<InvalidationListener> listeners;

    private Kleur kleur;

    public Model(String serverNaam, String poort, boolean matchmaking){
        this(matchmaking);
        speelveld = new SpeelveldModel(0, 0, 0, 2, 2, 0, 2, 2, matchmaking);
        startServer(serverNaam, poort);
        ServerController.interactief();
        awakeListners();
    }

    public Model(boolean matchmaking) {
        this.matchmaking = matchmaking;

        kleur = Kleur.WIT;
        listeners = new ArrayList<>();
        plaatsnu = 0;

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

    //Spelfunctionaliteiten

    public void parseStringToStap(String line){
        speelveld.parseStringToStap(line);
    }

    //vanaf hier alle serverCommunicatie

    public void serverNickname(String nickname){
        ServerController.nickname(nickname);
    }

    public void startServer(String serverNaam, String poort){
        new ServerController(serverNaam, poort);
        ServerController.setModel(this);
        ServerController.Startmatchmaking();
    }

    public void serverInlijst(){
        ServerController.plaatsInlijst();
    }

    public void serverUitLijst(){
        ServerController.haalUitLijst();
    }

    public void kiesSpeler(String speler){
        ServerController.kiesSpeler(speler);
        awakeListners();
    }

    public void serverStuurSpelBord(String string){
        ServerController.stuurSpelbord(string);
    }

    public void serverWachtOpSpelBord(){
        ServerController.ontvangSpelbord();
    }


    //vanaf hier alle getters en setters van het Model

    public boolean getServeraan(){
        return serverAan;
    }

    public void setServerAan(boolean serverAan) {
        this.serverAan = serverAan;
        awakeListners();
    }

    public boolean getNicknamebool(){
        return nickname;
    }

    public void setNickname(String nickname){
        nicknameString = nickname;
        awakeListners();
    }

    public void setNicknamebool(boolean nickname){
        this.nickname = nickname;
        awakeListners();
    }

    public SpeelveldModel getgrid() {
        return speelveld;
    }

    public int getPlaatsnu() {
        return plaatsnu;
    }

    public boolean isInLijst() {
        return inLijst;
    }

    public void setInLijst(boolean var) {
        inLijst = var;
        awakeListners();
    }

    public boolean isspelStart(){
        return spelStartBool;
    }

    public void setSpelStartBool(boolean spelStartBool){
        this.spelStartBool = spelStartBool;
        awakeListners();
    }

    public boolean isEerste() {
        return eerste;
    }

    public void setEerste(boolean eerste) {
        this.eerste = eerste;
        awakeListners();
    }

    public void setSpelBordKiezer(boolean spelBordKiezer){
        this.spelBordKiezer = spelBordKiezer;
        awakeListners();
    }

    public boolean isSpelBordKiezer(){
        return spelBordKiezer;
    }

    public String getNicknameString() {
        return nicknameString;
    }

    public String getTegenstander() {
        return tegenstander;
    }

    public void setTegenstander(String tegenstander) {
        this.tegenstander = tegenstander;
        awakeListners();
    }

    public void setSpeelveld(String input){
        //TODO maak het juiste speelveld met de input string

        setSpelBordKiezer(true);
    }

    public SpeelveldModel getSpeelveld(){
        return speelveld;
    }

    public Pion getTeVerplaatsenPion() {
        return teVerplaatsenPion;
    }

    public void setTeVerplaatsenPion(Pion teVerplaatsenPion) {
        this.teVerplaatsenPion = teVerplaatsenPion;
        awakeListners();
    }

    public boolean isEinde() {
        return einde;
    }

    public void setEinde(boolean einde) {
        this.einde = einde;
        awakeListners();
    }
}