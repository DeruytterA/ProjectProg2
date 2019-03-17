package mijnlieff;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerController {

    public ServerController(String server, String poort, SpelModel model) {
        try(Socket socket = new Socket(server, Integer.parseInt(poort));
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
        }catch (UnknownHostException ex){
            throw new RuntimeException("UnknownHostException when making socket " + ex);
        }catch (IOException ex){
            throw new RuntimeException("IOException when making socket " + ex);
        }
    }
}
