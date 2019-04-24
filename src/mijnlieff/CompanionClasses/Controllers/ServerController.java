package mijnlieff.CompanionClasses.Controllers;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import mijnlieff.Model.Model;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class ServerController {

    private static String server;
    private static int poort;
    private static Model model;
    private static Socket socket;
    private static BufferedReader serverIn;
    private static PrintWriter serverOut;

    public ServerController(String serverNaam, String poortNummer) {
        poort = Integer.parseInt(poortNummer);
        server = serverNaam;
    }

    public static void setModel(Model modell){
        model = modell;
    }


    //TODO Fase2 Stuur/ontvang bord configuratie (X 8x natuurlijk getal) elk getal <= 10 en minstens 1 rij en 1 kolom = 0
    //TODO Fase2 Stuur/ontvang stappen van/naar server
    //TODO Toevoegen van Alerts als connection niet werkt


    public static void Startmatchmaking(){
        try {
            socket = new Socket(server, poort);
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            model.setServerAan(true);
        }catch (IOException ex){
            model.setServerAan(false);
        }
    }

    public static void interactief(){
        try(Socket socketInteracief = new Socket(server, poort);
            BufferedReader serverInteractiefIn = new BufferedReader(new InputStreamReader(socketInteracief.getInputStream()));
            PrintWriter serverInteractiefOut = new PrintWriter(socketInteracief.getOutputStream(), true)
        ){
            serverInteractiefOut.println("X");
            String inputlijn = serverInteractiefIn.readLine();
            while (inputlijn.charAt(2) != 'T'){
                model.parseStringToStap(inputlijn);
                serverInteractiefOut.println("X");
                inputlijn = serverInteractiefIn.readLine();
            }
            model.parseStringToStap(inputlijn);
            model.setServerAan(true);
        }catch (Exception ex){
            throw new RuntimeException("UnknownHostException when making socket in interactief modus " + ex);
        }
    }

    private static String receive(){
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

    public static void close(){
        if (serverOut != null) {
            serverOut.println("Q");
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException("Er is iets misgegaan bij het sluiten van de socket " + ex);
            }
        }
    }

    public static void nickname(String naam){
        serverOut.println("I " + naam);
        String string = receive();
        if (string.equals("+")){
            model.setNicknamebool(true);
            model.setNickname(naam);
        }else {
            model.setNicknamebool(false);
            model.setNickname(null);
        }
    }

    public static void getOponents(ObservableList<String> lijst){
        lijst.clear();
        serverOut.println("W");
        String lijn = receive();
        while (lijn.length() != 1){
            lijst.add(lijn.substring(2));
            lijn = receive();
        }
    }

    public static void plaatsInlijst(){
        serverOut.println("P");
        model.setInLijst(true);
        Task<String> task = new Task<String>() {
            @Override
            protected String call() {
                return wachtOpAntwoord();
            }
        };
        task.setOnSucceeded(e -> {
            try {
                parseTegenstander(task.get());
            }catch (ExecutionException | InterruptedException ex){
                throw new RuntimeException("er is iets misgegaan bij het opvragen van de string van de task " + ex);
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    private static void parseTegenstander(String input){
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

    public static String wachtOpAntwoord(){
        String lijn = receive();
        while (lijn == null){
            lijn = receive();
        }
        return lijn;
    }

    //TODO Fase1 terug trekken uit lijst (R sturen) (+ (als gelukt)) (? anders)
    public static boolean haalUitLijst(){
        serverOut.println("R");
        String string = receive();
        if (string.equals("+")){
            model.setInLijst(false);
            return true;
        }else {
            model.setInLijst(true);
            return false;
        }
    }

    public static void parseSpelbord(String lijn){
        model.setSpeelveld(lijn);
    }

    public static void stuurSpelbord(String string){
        String output = "X " + string;
        serverOut.println(output);
        parseSpelbord(output);
    }

    public static void ontvangSpelbord(){
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
    public static void kiesSpeler(String naam){
        serverOut.println("C " + naam);
        String input = receive();
        parseTegenstander(input);
    }
}
