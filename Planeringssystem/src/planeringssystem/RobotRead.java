package planeringssystem;

import java.util.Arrays;
import java.util.Random;

public class RobotRead implements Runnable {

    private int sleepTime;
    private static Random generator = new Random(); // Vet inte om vi kommer behöva denna? Nej, tror vi kommer sätta en uppdateringstid som vi vill ha/S
    private GUI gui;
    private DataStore ds;
    private HTTPanrop http; // hur initierar man denna när den är en main? Kan den vara en main?
    private int currentX;
    private int currentY;
    private int currentArc;
    private int capacity;
    private String currentStatus;
    private Transceiver tr;
    private String[] agv;
    private String[] split_in;
    private String start;

    public RobotRead(DataStore ds, GUI gui) {
        this.gui = gui;
        this.ds = ds;
        tr = new Transceiver();
        http = new HTTPanrop();
        currentX = 70;
        currentY = 50;
        currentArc = 1;
        sleepTime = generator.nextInt(20000);
    }

    @Override
    public void run() {
        try {
            // Hur länge RobotReaden ska köras kanske inte behöver skrivas ut?
            gui.appendErrorMessage("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            int i = 0;

            // Denna borde köras så länge som roboten fortfarande kör (eventuellt ta bort i++)
            while (i <= 20) {
                while (gui.getButtonState()) {
                    Thread.sleep(sleepTime / 20);
                }
                /*
                 * Skapar meddelandet och kallar på transceiver som kan skicka iväg det till AGV.
                 */
                start = "#";
                ds.meddelande_in = "#12345 .1234   $";//meddelande vi får från AGV //ds.meddelande_in = tr.Transceiver(ds.meddelande_in);
                split_in = ds.meddelande_in.split("(?!^)");

                //Uppdaterar meddelandet
                if (gui.getButtonState()) {
                    ds.enable = '0';
                }

                if (Integer.parseInt(split_in[8]) == ds.ordernummer) {
                    ds.ordernummer += 1; // Vi vill "nollställa" ordernummer till varje ny rutt. 
                    ds.korinstruktion = ds.instructions.removeFirst(); // lägger första instruktionen i körinstruktion och tar bort det ur listan.                  
                }

                ds.antal_passagerare = '4'; // DENNA SKA ÄNDRAS varje gång vi plockar upp eller lämnar av passagerare. Borde hänga ihop med tauppdrag
                ds.kontroll++;

                //spegling = tempMeddelande.split(".");
                agv = ds.meddelande_in.split("(?!^)");
                ds.spegling = ""; // här nollställs spegling TA INTE BORT

                //Tar fram det som ska speglas
                for (int j = 9; j < agv.length; j++) {
                    ds.spegling += agv[j];
                }

                //Kollar så att speglingen har samma kontrollvariabel som vi skickade iväg
                if (Integer.parseInt(split_in[5]) != ds.kontroll) {
                    start = "1"; //Eftersom detta får AGV:n att starta om
                }

                //Kollar att AGVns kontrollvariable är ny varje gång de skickar något
                if (Integer.parseInt(split_in[11]) == ds.kontrollAGV) {
                start = "1";
                }
                               
                
                ds.kontrollAGV = split_in[11].charAt(0);

                ds.flagCoordinates = true;
                //currentX = 30; //Här ska robotens koordinater läggas till, kanske direkt från BT-metoden istället för via DS?
                //currentY = 40;
                currentArc = 1; // Här ska robotens aktuella länk läsas in kankse? 
                // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
                //gui.appendErrorMessage("Jag är tråd RobotRead för " + i + ":e gången.");
                capacity = getCurrentCapacity(8); // Hårdkodar att bilen har 8 platser totalt
                gui.appendCapacity("Nuvarade kapacitet i AGV: " + capacity);

                Thread.sleep(200);
                i++;
            }
        } catch (Exception e) {
        }

        // Ska vi ha kvar denna?
        gui.appendErrorMessage("Robotread är nu klar");
    }

    public int getCurrentCapacity(int cap) {
        return cap - http.getPassengers(); //minus det antal passagerare vi plockar upp på nuvarande uppdrag.(Just nu från endast en HHTPanropsklass) 
        //Vi måste komma på ett sätt att lägga till capacity igen när vi lämnat av folk.
    }

    public int getCurrentX() {
        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.
        return currentX;
    }

    public int getCurrentY() {
        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.
        return currentY;
    }

    public int getCurrentArc() {
        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.
        return currentArc;
    }

    public String getCurrentStatus() { //Kanske ska vara en int istället för String eftersom det är de 16 bytes vi får av dem.
        return currentStatus;
    }
}
