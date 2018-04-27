package planeringssystem;

import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class Transceiver {
        String meddelande_in;
    
    public Transceiver (){
        meddelande_in = "";
    }
    public String Transceiver(String meddelande_ut){
        try {
            StreamConnection anslutning = (StreamConnection) Connector.open(
                    "btspp://201601205770:1"
            );
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
            
            InputStream bluetooth_in = anslutning.openInputStream(); // new InputStream(anslutning.openInputStream()); ?
            byte buffer[] = new byte[16];        
            
            while (true) {
                if (meddelande_ut
                        == null) {
                    break;
                }
                
                bluetooth_ut.print(meddelande_ut);
                Thread.sleep(1000);
                
                int antal_bytes = bluetooth_in.read(buffer);
                
                String meddelande_in = new String(buffer, 0, antal_bytes);
            
            System.out.println("\n" + "Mottaget meddelande: " + meddelande_in); // return meddelande_in;
            }
            anslutning.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return meddelande_in;
    }   
}
