package bluetoothtransmitter;

import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class BluetoothTransmitter {

    public static void
            main(String args[]) {
        try {
            StreamConnection anslutning = (StreamConnection) Connector.open(
                    "btspp://001060D1CDC2:5" // Här ska vi istället ta in info om BT-adress och kanal från textruta i GUIn
            );
            PrintStream bluetooth_ut
                    = new PrintStream(anslutning.openOutputStream());

            bluetooth_ut.println(
                    "Test från grupp 4"
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
