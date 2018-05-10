package planeringssystem_v2;

import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class Transceiver {

    DataStore ds;
    String meddelande_in;
    PrintStream bluetooth_ut;
    StreamConnection anslutning;
    InputStream bluetooth_in;
    byte buffer[];

    public Transceiver(DataStore ds) { //Den här tar in en ds för att kunna sätta ds.connection till true, men vet inte hur det ska funka än./A
        meddelande_in = "";
        this.ds = ds;
    }
    public void getConnection(){
    try {
            anslutning = (StreamConnection) Connector.open(
                    "btspp://201601205770:1"
            );
            bluetooth_ut = new PrintStream(anslutning.openOutputStream());

            bluetooth_in = anslutning.openInputStream(); // new InputStream(anslutning.openInputStream()); ?
            ds.bt = true;

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public String Transceiver(String meddelande_ut) {
        try {

            while (true) {
                if (meddelande_ut
                        == null) {
                    break;
                }
                buffer = new byte[16];
                
                System.out.println("Meddelande_ut: " + ds.meddelande_ut);
                bluetooth_ut.print(meddelande_ut);
                
                Thread.sleep(250);

                //System.out.print("F");
                int antal_bytes = bluetooth_in.read(buffer);
                //System.out.print("E");
                
                String meddelande_in = "";
                meddelande_in = new String(buffer, 0, antal_bytes);
//                System.out.println("L");
                System.out.println("meddelande_in i transciever: " + meddelande_in);
                //System.out.println("\n" + "Mottaget meddelande: " + meddelande_in); // return meddelande_in;
//                if (meddelande_in == null) {
//                    meddelande_in = "#2345678       $";
//                }
                return meddelande_in;
                
            }
            anslutning.close();
            
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return "";
    }
}
