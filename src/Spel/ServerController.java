package Spel;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerController {

    private SpelModel model;
    private boolean gesloten;

    public ServerController(String server, String poort, SpelModel model) {
        this.model = model;
        try(Socket socket = new Socket(server, Integer.parseInt(poort));
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true)
        ){

            serverOut.println("X");
            String inputlijn = serverIn.readLine();
            while (inputlijn.charAt(2) != 'T'){
                model.update(inputlijn);
                serverOut.println("X");
                inputlijn = serverIn.readLine();
            }
            model.update(inputlijn);
        }catch (UnknownHostException ex){
            throw new RuntimeException("UnknownHostException when making socket " + ex);
        }catch (IOException ex){
            throw new RuntimeException("IOException when making socket " + ex);
        }
    }



}
