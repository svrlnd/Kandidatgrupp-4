package agv_kommunikation;

import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class Transmit implements Runnable {

    
    @Override
    public void run() {     //denna ska ta in en vinkel som parameter från OptPlan och skriva denna till AGV:n
        try {
            StreamConnection anslutning = (StreamConnection) Connector.open(
                    "btspp://001060D1CDC2:5" // AGV adress och kanal
            );
            PrintStream bluetooth_ut
                    = new PrintStream(anslutning.openOutputStream());

            bluetooth_ut.println(
                    "Hej vi är planeringssystem grupp 4" // in med vinkeln i detta meddelande!
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
