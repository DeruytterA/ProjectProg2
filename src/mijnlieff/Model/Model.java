package mijnlieff.Model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.collections.ObservableList;
import mijnlieff.CompanionClasses.Controllers.ServerController;

import java.util.*;

public class Model implements Observable {

    private boolean einde;
    private boolean quit;
    private boolean spelBordKiezer;
    private boolean maakSpelbord;
    private boolean nickname;
    private boolean serverAan;
    private boolean spelStartBool;
    private boolean inLijst;

    private String nicknameString;
    private String tegenstander;
    private ServerController serverController;
    private SpeelveldModel speelveldModel;

    private ArrayList<InvalidationListener> listeners;

    public Model(ServerController serverController) {
        this();
        this.serverController = serverController;
    }

    public Model(){
        listeners = new ArrayList<>();
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

    //vanaf hier alle serverCommunicatie

    public void serverNickname(String nickname){
        serverController.nickname(nickname);
    }

    public void startServer(String serverNaam, String poort){
        serverController = new ServerController(serverNaam, poort);
        serverController.setModel(this);
        serverController.Startmatchmaking();
    }

    public void serverInlijst(){
        serverController.plaatsInlijst();
    }

    public void kiesSpeler(String speler){
        serverController.kiesSpeler(speler);
        awakeListners();
    }

    public void serverWachtOpSpelBord(){
        serverController.ontvangSpelbord();
    }

    public void serverStuurSpelbord(String spelbord){
        setSpelBordString(spelbord);
        serverController.stuurSpelbord(spelbord);
    }

    public void getOpponents(ObservableList<String> lijst){
        serverController.getOpponents(lijst);
    }

    public void serverQuit(){
        serverController.close();
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
    }

    public void setNicknamebool(boolean nickname){
        this.nickname = nickname;
        awakeListners();
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

    public boolean isMaakSpelbord() {
        return maakSpelbord;
    }

    public void setMaakSpelbord(boolean maakSpelbord) {
        this.maakSpelbord = maakSpelbord;
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
    }

    public boolean isQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
        awakeListners();
    }

    public void setSpelBordString(String spelbord){
        speelveldModel = new SpeelveldModel(
                Character.getNumericValue(spelbord.charAt(2)),
                Character.getNumericValue(spelbord.charAt(4)),
                Character.getNumericValue(spelbord.charAt(6)),
                Character.getNumericValue(spelbord.charAt(8)),
                Character.getNumericValue(spelbord.charAt(10)),
                Character.getNumericValue(spelbord.charAt(12)),
                Character.getNumericValue(spelbord.charAt(14)),
                Character.getNumericValue(spelbord.charAt(16)),
                true,
                this
        );
        setSpelBordKiezer(true);
    }

    public ServerController getServer(){
        return serverController;
    }

    public boolean isEinde() {
        return einde;
    }

    public void setEinde(boolean einde) {
        this.einde = einde;
        awakeListners();
    }

    public SpeelveldModel getSpeelveldModel() {
        return speelveldModel;
    }
}