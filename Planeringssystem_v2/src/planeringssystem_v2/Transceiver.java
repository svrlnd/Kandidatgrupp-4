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

                bluetooth_ut.print(meddelande_ut);
                Thread.sleep(400);

                int antal_bytes = bluetooth_in.read(buffer);

                String meddelande_in = new String(buffer, 0, antal_bytes);

                //System.out.println("\n" + "Mottaget meddelande: " + meddelande_in); // return meddelande_in;
                return meddelande_in;
            }
            anslutning.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return "";
    }
}
