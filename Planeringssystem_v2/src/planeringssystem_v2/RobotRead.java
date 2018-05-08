package planeringssystem_v2;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

public class RobotRead implements Runnable {

//    private int sleepTime;
    private static Random generator = new Random(); // Vet inte om vi kommer behöva denna? Nej, tror vi kommer sätta en uppdateringstid som vi vill ha/S
    private GUI gui;
    private DataStore ds;
    private Transceiver tr;
    private String[] agv;
    private char [] split_in;
    private String start;

    public RobotRead(DataStore ds, GUI gui) {
        this.gui = gui;
        this.ds = ds;
        tr = new Transceiver(ds);
//        sleepTime = generator.nextInt(20000);
    }

    @Override
    public void run() {
        try {

            //Upprätta connection med AGVn
//            tr.getConnection();
            // Hur länge RobotReaden ska köras kanske inte behöver skrivas ut?
//            gui.appendErrorMessage("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            int i = 0;

            // Denna borde köras så länge som roboten fortfarande kör (eventuellt ta bort i++)
            while (true) {
               
                gui.appendErrorMessage("Jag är i loopen i robotRead");
//                System.out.println("Jag är i loopen i robotRead");
                while (!gui.getButtonState()) {
                    Thread.sleep(1000);
                }
                /*
                 * Skapar meddelandet och kallar på transceiver som kan skicka iväg det till AGV.
                 */
                
                start = "#";
                ds.enable = '1';
                
                //Gjorde denna println för att se vad som saknas, så kan vi bocka av
                //vad som blir klart.
                System.out.println("start " + start + " enable " + ds.enable
                + " ordnr " + ds.ordernummer +  " antal passagerare " 
                +  ds.antal_passagerare + " körinstruktion " + ds.korinstruktion 
                + " kontrollvar. " + ds.kontroll + "   spegling " + ds.spegling);
                
                ds.meddelande_in = "#12345 .!234   $";
                

                ds.meddelande_ut = start + ds.enable + ds.ordernummer + 
                        ds.antal_passagerare + ds.korinstruktion + ds.kontroll
                        + " " + " " + ds.spegling + '$';
//                ds.meddelande_in = tr.Transceiver(ds.meddelande_ut);//ds.meddelande_in = "#12345 .1234   $";//meddelande vi får från AGV //
                split_in = ds.meddelande_in.toCharArray();
                
                System.out.println("Split in "+Arrays.toString(split_in));
                
                System.out.println("Meddelande_ut: " + ds.meddelande_ut + " för " + i + "te gången");

                //Uppdaterar meddelandet
                if (gui.getButtonState()) {
                    ds.enable = '0';
                }

                int k = 0; //Används för att ta fram vilken arc i arcRoute vi är på just nu. 
                if (split_in[8] == ds.ordernummer) { //AGVn meddelar att den utfört order, dvs förflyttat sig till ny länk
                    System.out.println("If ord.nummer ");
                    ds.ordernummer += 1; // Vi vill "nollställa" ordernummer till varje ny rutt - FIXA DET. 
                    if (ds.instructions.getFirst() == null) {//Om nästa order är att stanna
                        if (ds.cap == ds.initial_cap) { //upphämtningsplats
                            //ta uppdrag, tänker att vi kanske kan ha en metod i en ny klass som heter typ stop som gör följande
                            // - kallar på ta uppdrag: ha.messagetype(String plats, int id, int passagerare, int grupp)
                            // - minskar kapaciteten: ds.cap = ds.cap - (antal passagerare vi tar upp)
                            // - påbörjar rutt till uppdragets avlämningsplats: uppdaterar dest_node och last_node samt kallar på op.createPlan och op.createInstructions
                        } else if (ds.cap < ds.initial_cap) { //avlämningsplats
                            //lämna av passagerare, kanske med en metod i klassen stop som gör följande: 
                            // - ökar kapaciteten
                            // - påbörjar rutt till närmsta upphämtningsplats
                        }
                    }
                    ds.korinstruktion = ds.instructions.removeFirst(); // lägger första instruktionen i körinstruktion och tar bort det ur listan.                  
                    ds.arcColor[ds.arcRoute.get(k)] = 2; // så att nuvarnade länk kan blinka, just nu blir den grön i MapPanel
                    i++;
                }

                ds.antal_passagerare = '4'; // DENNA SKA ÄNDRAS varje gång vi plockar upp eller lämnar av passagerare. Borde hänga ihop med tauppdrag
                ds.kontroll++; //kontrollvariablen förändras varje gång vi skickar något, bör den nollställas ibland? Vad händer när den kommer till slutet av ASCI-tabellen

                //spegling = tempMeddelande.split(".");
                agv = ds.meddelande_in.split("(?!^)");
                ds.spegling = ""; // här nollställs spegling TA INTE BORT

                //Tar fram det som ska speglas
                for (int j = 9; j < agv.length; j++) {
                    ds.spegling += agv[j];
                }

                //Kollar så att speglingen har samma kontrollvariabel som vi skickade iväg
                
                if (Character.getNumericValue(split_in[5]) != ds.kontroll) {
                    start = "1"; //Eftersom detta får AGV:n att starta om
                    //DETTA MÅSTE HÄNDA INNAN MEDDELANDET SKICKAS DÄR UPPE
                }

                //Kollar att AGVns kontrollvariable är ny varje gång de skickar något
                if (Character.getNumericValue(split_in[11]) == ds.kontrollAGV) {
                    start = "1";
                    //DETTA MÅSTE HÄNDA INNAN MEDDELANDET SKICKAS DÄR UPPE
                }

                ds.kontrollAGV = split_in[11]; // Spara kontrollvariabeln för att kunna jämföra den med nästa variabel. 

//                ds.flagCoordinates = true;
//                //currentX = 30; //Här ska robotens koordinater läggas till, kanske direkt från BT-metoden istället för via DS?
//                //currentY = 40;
//                currentArc = 1; // Här ska robotens aktuella länk läsas in kankse? 
                // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
                //gui.appendErrorMessage("Jag är tråd RobotRead för " + i + ":e gången.");
               

                Thread.sleep(350);
                i++;
            }
        } catch (InterruptedException e) {
            System.out.println("Catch i RobotRead");
        }

        // Ska vi ha kvar denna?
        gui.appendErrorMessage("Robotread är nu klar");
    }
//
//    public int getCurrentCapacity(int cap) {
//        return cap - ha.getPassengers(); //minus det antal passagerare vi plockar upp på nuvarande uppdrag.(Just nu från endast en HHTPanropsklass) 
//        //Vi måste komma på ett sätt att lägga till capacity igen när vi lämnat av folk.
//    }
//
//    public int getCurrentX() {
//        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.//Tror inte dessa gäller längre
//        return currentX;
//    }
//
//    public int getCurrentY() {
//        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.//Tror inte dessa gäller längre
//        return currentY;
//    }
//
//    public int getCurrentArc() {
//        //Här kommer vi kalla på en Bluetoothklass som tar reda på robotens pos.//Tror inte dessa gäller längre
//        return currentArc;
//    }
//
//    public String getCurrentStatus() { //Kanske ska vara en int istället för String eftersom det är de 16 bytes vi får av dem.
//        return currentStatus;
//    }
//

}
