package mijnlieff.CompanionClasses.Controllers;

import mijnlieff.Model.SpelModel;

import java.io.*;
import java.net.Socket;

public class ServerController {

    private static String server;
    private static int poort;
    private static SpelModel model;
    private static BufferedReader serverIn;
    private static PrintWriter serverOut;
    private static boolean matchmaking;

    public ServerController(String server, String poort) {
        matchmaking = false;
        this.poort = Integer.parseInt(poort);
        this.server = server;
    }

    public static void setModel(SpelModel var1){
        model = var1;
    }


    //TODO Fase1 verbinding afsluiten (Q sturen)
    //TODO Fase1 lijst spelers krijgen (W sturen) (+ <naam> terug) | (+ (einde))
    //TODO Fase1 stuur username (I <naam> sturen) (- (naam bezet)) | (+ (naam gekregen))
    //TODO Fase1 zichzelf in lijst plaatsen (P sturen) (+ <T/F> <naam> als gekozen) CONNECTIE NAAR FASE 2
    //TODO Fase1 terug trekken uit lijst (R sturen) (+ (als gelukt)) (? anders)
    //TODO Fase1 speler kiezen uit de lijst ( C <naam> sturen) (- terug (als speler al uit lijst)) | (+ <T/F> <naam> als gekozen)
    //TODO Fase2 Als server Q stuurt = andere speler quit
    //TODO Fase2 Stuur/ontvang bord configuratie (X 8x natuurlijk getal) elk getal <= 23 en minstens 1 rij en 1 kolom = 0
    //TODO Fase2 Stuur/ontvang stappen van/naar server
    //TODO Toevoegen van Alerts als connection niet werkt


    public static void Startmatchmaking(){
        try {
            Socket socket = new Socket(server, poort);
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            model.setServerAan(true);
        }catch (IOException ex){
            model.setServerAan(false);
        }

    }

    public static void interactief(){
        try(Socket socket = new Socket(server, poort);
            BufferedReader serverInteractiefIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverInteractiefOut = new PrintWriter(socket.getOutputStream(), true)
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
            throw new RuntimeException("UnknownHostException when making socket " + ex);
        }
    }

    public static Boolean nickname(String naam) throws IOException{
        serverOut.println("I " + naam);
        return serverIn.readLine().equals("+");
    }


}
