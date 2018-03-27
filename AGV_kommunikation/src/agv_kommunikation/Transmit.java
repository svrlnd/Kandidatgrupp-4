package agv_kommunikation;

import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class Transmit implements Runnable {

    
    @Override
    public void run() {
        try {
            StreamConnection anslutning = (StreamConnection) Connector.open(
                    "btspp://001060D1CDC2:5" // Här ska vi istället ta in info om BT-adress och kanal
            );
            PrintStream bluetooth_ut
                    = new PrintStream(anslutning.openOutputStream());

            bluetooth_ut.println(
                    "Hej vi är planeringssystem grupp 4"
            );
            Thread.sleep(
                    500
            );
            anslutning.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
