package Spel;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerController {

    private BufferedReader serverIn;
    private BufferedWriter serverOut;
    private SpelModel model;
    private boolean gesloten;
    private Socket socket;

    public ServerController(String server, String poort) {
        try(Socket socket = new Socket(server, Integer.parseInt(poort))){
            this.socket = socket;
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            gesloten = false;
        }catch (UnknownHostException ex){
            throw new RuntimeException("UnknownHostException when making socket" + ex);
        }catch (IOException ex){
            throw new RuntimeException("IOException when making socket" + ex);
        }
    }

    public void setModel(SpelModel model) {
        this.model = model;
    }

    public void eenStap(){
        try {
            serverOut.write("X");
            String line = serverIn.readLine();
            if (line.charAt(3) == 'T'){
                close();
                gesloten = true;
            }
            model.update(line);
        }catch (IOException ex){
            throw new RuntimeException("IOException when writing to server" + ex);
        }
    }

    public void alleStappen(){
        while (!gesloten){
            eenStap();
        }
    }

    public void close(){
        try {
            socket.close();
        }catch (IOException ex){
            throw new RuntimeException("IOException when closing the socket" + ex);
        }
    }

}
