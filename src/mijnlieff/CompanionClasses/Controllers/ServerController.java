package mijnlieff.CompanionClasses.Controllers;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class ServerController {

    private String server;
    private int poort;
    private Model model;
    private SpeelveldModel spelbordModel;
    private Socket socket;
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private Task<String> wachtOpkiezen;
    private Task<String> ontvangZet;

    public ServerController(String serverNaam, String poortNummer) {
        poort = Integer.parseInt(poortNummer);
        server = serverNaam;
    }

    public ServerController(){}

    public void setModel(Model modell){
        model = modell;
    }

    public void setSpeelveldModel(SpeelveldModel speelveldModel1){
        spelbordModel = speelveldModel1;
    }


    //TODO Fase2 Stuur/ontvang bord configuratie (X 8x natuurlijk getal) elk getal <= 10 en minstens 1 rij en 1 kolom = 0
    //TODO Fase2 Stuur/ontvang stappen van/naar server
    //TODO Toevoegen van Alerts als connection niet werkt


    public void Startmatchmaking(){
        try {
            socket = new Socket(server, poort);
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            model.setServerAan(true);
        }catch (IOException ex){
            model.setServerAan(false);
        }
    }

    public void interactief(){
        try(Socket socketInteracief = new Socket(server, poort);
            BufferedReader serverInteractiefIn = new BufferedReader(new InputStreamReader(socketInteracief.getInputStream()));
            PrintWriter serverInteractiefOut = new PrintWriter(socketInteracief.getOutputStream(), true)
        ){
            serverInteractiefOut.println("X");
            String inputlijn = serverInteractiefIn.readLine();
            while (inputlijn.charAt(2) != 'T'){
                spelbordModel.parseStringToStap(inputlijn);
                serverInteractiefOut.println("X");
                inputlijn = serverInteractiefIn.readLine();
            }
            spelbordModel.parseStringToStap(inputlijn);
        }catch (IOException ex){
            throw new RuntimeException("UnknownHostException when making socket in interactief modus " + ex);
        }
    }

    public void StuurZet(String zet){
        serverOut.println(zet);
    }

    public void ontvangZet(){
        ontvangZet = new Task<>() {
            @Override
            protected String call() {
                return wachtOpAntwoord();
            }
        };
        ontvangZet.setOnSucceeded(o -> {
            try {
                parseZet(ontvangZet.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public void parseZet(String string){
        spelbordModel.add(spelbordModel.parseStringToPion(string));
    }

    private String receive(){
        String string;
        try {
            string = serverIn.readLine();
            System.out.println(string);
        }catch (IOException ex){
            throw new RuntimeException("Er is iets misgegaan bij het lezen van de server " + ex);
        }
        if (string == null || (string.length() == 1 && string.charAt(0) == 'Q')){
            //TODO quit when server gets Q
            return null;
        }else{
            return string;
        }
    }

    public void close(){
        if (serverOut != null) {
            serverOut.println("Q");
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException("Er is iets misgegaan bij het sluiten van de socket " + ex);
            }
        }
    }

    public void nickname(String naam){
        serverOut.println("I " + naam);
        String string = receive();
        System.out.println(string);
        if (string.equals("+")){
            model.setNicknamebool(true);
            model.setNickname(naam);
        }else {
            model.setNicknamebool(false);
            model.setNickname(null);
        }
    }

    public void getOpponents(ObservableList<String> lijst){
        lijst.clear();
        serverOut.println("W");
        String lijn = receive();
        while (lijn.length() != 1){
            lijst.add(lijn.substring(2));
            lijn = receive();
        }
    }

    public void wachtOpTegenstander(){
        wachtOpkiezen = new Task<>() {
            @Override
            protected String call() {
                String lijn = receive();
                while (lijn == null && !this.isCancelled()){
                    lijn = receive();
                }
                return lijn;
            }
        };
        wachtOpkiezen.setOnSucceeded(e -> {
            try {
                parseTegenstander(wachtOpkiezen.get());
            }catch (ExecutionException | InterruptedException ex){
                throw new RuntimeException("er is iets misgegaan bij het opvragen van de string van de task " + ex);
            }
        });
        Thread thread = new Thread(wachtOpkiezen);
        thread.start();
    }

    public void plaatsInlijst(){
        serverOut.println("P");
        model.setInLijst(true);
        wachtOpTegenstander();
    }

    private void parseTegenstander(String input){
        if (input.length() == 1 && input.charAt(0) == '-'){
            model.setSpelStartBool(false);
        }else {
            if (input.charAt(2) == 'T'){
                model.setEerste(true);
            }else {
                model.setEerste(false);
            }
            model.setSpelStartBool(true);
            model.setTegenstander(input.substring(4));
        }
    }

    public String wachtOpAntwoord(){
        String lijn = receive();
        while (lijn == null){
            lijn = receive();
        }
        return lijn;
    }

    //TODO Fase1 terug trekken uit lijst (R sturen) (+ (als gelukt)) (? anders)
    public void haalUitLijst(){
        serverOut.println("R");
        String string = receive();
        if (string.equals("+")){
            model.setInLijst(false);
        }
        wachtOpkiezen.cancel(true);
    }

    public void parseSpelbord(String lijn){
        model.setSpelBordString(lijn);
    }

    public void stuurSpelbord(String string){
        String output = "X " + string;
        serverOut.println(output);
        parseSpelbord(output);
    }

    public void ontvangSpelbord(){
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return wachtOpAntwoord();
            }
        };
        task.setOnSucceeded(e -> {
            try {
                parseSpelbord(task.get());
            }catch (ExecutionException | InterruptedException ex){
                throw new RuntimeException("er is iets misgegaan bij het opvragen van de string van de task " + ex);
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    //TODO Fase1 speler kiezen uit de lijst ( C <naam> sturen) (- terug (als speler al uit lijst)) | (+ <T/F> <naam> als gekozen)
    public void kiesSpeler(String naam){
        serverOut.println("C " + naam);
        String input = receive();
        parseTegenstander(input);
    }

}
