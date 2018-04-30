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
    private String tempMeddelande;
    private String spegling;
    private String[] agv;
    private String[] split_in;
    private String start;

    public RobotRead(DataStore ds, GUI gui) {
        this.gui = gui;
        this.ds = ds;
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
                tempMeddelande = "";
                tempMeddelande = "#12345 .1234   $";
                start = "#";

                if (gui.getButtonState()) {
                    ds.enable = '0';
                }
                ds.kontroll++;

                //spegling = tempMeddelande.split(".");
                agv = tempMeddelande.split("(?!^)");
                spegling = "";

                for (int j = 9; j < agv.length; j++) {
                    spegling += agv[j];
                }
                

                //Detta borde vara i en Thread? Det ska uppdateras med 0,2 s (= 200 ms) mellanrum.
                //Vi gjorde en createmessage till i createmessage
                ds.meddelande_ut = start + ds.enable + ds.ordernummer + ds.antal_passagerare + ds.korinstruktion
                        + ds.kontroll + ' ' + '.' + spegling + "$";

                gui.appendErrorMessage(ds.meddelande_ut);
                ds.meddelande_in = "#12345 .1234   $";//meddelande vi får från AGV
                split_in = ds.meddelande_in.split("(?!^)");
                
                if (Integer.parseInt(split_in [5]) != ds.kontroll){
                    start = "1"; //Eftersom detta får AGV:n att starta om
                } 
                
                if (Integer.parseInt(split_in [8]) == ds.ordernummer){
                    ds.ordernummer += 1;
                    //Uppdatera curNode till current arcEnd på något sätt.
                    //Behöver vi en getCurrentarcEnd i optplan eller nånstans?
                }
               
                
                
                
                
                
                
                

//        tr = new Transceiver();
//        tempMeddelande = tr.Transceiver(ds.meddelande);
                Thread.sleep(sleepTime / 20);
                ds.flagCoordinates = true;
                //currentX = 30; //Här ska robotens koordinater läggas till, kanske direkt från BT-metoden istället för via DS?
                //currentY = 40;
                currentArc = 1; // Här ska robotens aktuella länk läsas in kankse? 
                // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
                //gui.appendErrorMessage("Jag är tråd RobotRead för " + i + ":e gången.");
                capacity = getCurrentCapacity(8); // Hårdkodar att bilen har 8 platser totalt
                gui.appendCapacity("Nuvarade kapacitet i AGV: " + capacity);
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
