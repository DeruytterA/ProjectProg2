package mijnlieff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {

    public static void Startmatchmaking(String server, int poort, String speler){
        try {
            Socket socket = new Socket(server, poort);
            BufferedReader serverin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            serverOut.println("I " + speler);
            System.out.println(serverin.readLine());
            serverOut.println("W");
            String line = serverin.readLine();
            while (line.length() > 1) {
                System.out.println(line);
                line = serverin.readLine();
            }
        }catch (IOException ex){
            throw new RuntimeException("blablabla " + ex);
        }

    }
}
