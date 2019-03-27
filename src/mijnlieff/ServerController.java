package mijnlieff;

import java.io.*;
import java.net.Socket;

public class ServerController implements Runnable{

    private String server;
    private int poort;
    private SpelModel model;
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private boolean matchmaking;

    public ServerController(String server, String poort, SpelModel model) {
        matchmaking = false;
        this.model = model;
        this.poort = Integer.parseInt(poort);
        this.server = server;
    }

    public void run(){
        if (matchmaking){
            Startmatchmaking();
        }else {
            interactief();
        }
    }


    //TODO Fase1 verbinding afsluiten (Q sturen)
    //TODO Fase1 lijst spelers krijgen (W sturen) (+ <naam> terug) | (+ (einde))
    //TODO Fase1 stuur username (I <naam> sturen) (- (naam bezet)) | (+ (naam gekregen))
    //TODO Fase1 zichzelf in lijst plaatsen (P sturen) (+ <T/F> <naam> als gekozen) CONNECTIE NAAR FASE 2
    //TODO Fase1 terug trekken uit lijst (R sturen) (+ (als gelukt)) (? anders)
    //TODO Fase1 speler kiezen uit de lijst ( C <naam> sturen) (- terug (als speler al uit lijst)) | (+ <T/F> <naam> als gekozen)
    //TODO Fase2 Als server Q stuurt = andere speler quit
    //TODO Fase2 Stuur/ontvang bord configuratie (X 8x natuurlijk getal) elk getal <= 23 en minstens 1 rij en 1 kolom = 0


    public void Startmatchmaking(){
        matchmaking = true;
        try(
                Socket socket = new Socket(server, poort);
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true)
                ){
            this.serverIn = serverIn;
            this.serverOut = serverOut;
        }catch (Exception ex){
            throw new RuntimeException("Er is iets mis gegaan met de verbinding met de Matchmaking server " + ex);
        }
    }

    public void interactief(){
        try(Socket socket = new Socket(server, poort);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true)
        ){
            serverOut.println("X");
            String inputlijn = serverIn.readLine();
            while (inputlijn.charAt(2) != 'T'){
                model.parseStringToStap(inputlijn);
                serverOut.println("X");
                inputlijn = serverIn.readLine();
            }
            model.parseStringToStap(inputlijn);
        }catch (Exception ex){
            throw new RuntimeException("UnknownHostException when making socket " + ex);
        }
    }

}
