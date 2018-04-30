package planeringssystem;

import java.awt.event.WindowAdapter;
import java.util.Arrays;
import java.lang.Math;

public class Planeringssystem {

    DataStore ds;
    GUI gui;
    RobotRead rr;
    GuiUpdate gu;
    Thread t1;
    Thread t2;
    OptPlan op;
    HTTPgrupp hg;
    HTTPanrop ha;
    ClosestPlats cp;
    String[] instructions;
    String[] dummyList3;
    String[] dummyList4;
    String[] uppdragsIDArray;
    String[] destinationPlatserArray;
    String[] destinationUppdragArray;
    String[] passengersArray;
    String[] samakningArray;
    String[] pointsArray;
    String[] destinationUppdragX;
    String[] destinationUppdragY;
    int a;
    int b;

    Planeringssystem() {
        /*
         * Initialize the DataStore call where all "global" data will be stored
         */
        ds = new DataStore();


        /*
         * Reads in the file with the map. For now it is street.txt. Don´t know where the file will be stored.
         */
        ds.setFileName("streets.txt");
        ds.readNet();

        /*
         * Initialize an optplan
         */
        
        hg = new HTTPgrupp();

        ha = new HTTPanrop();

        cp = new ClosestPlats(ds, ha, op);
        
        op = new OptPlan(ds);

        if (ds.counterFirstInstructions == 0) {
            a = 4;
            b = ds.dest_node;
        } 
        else {
            a = ds.dummyArcEnd.getLast();
            b = ds.dest_node;
        }
        op.createPlan(a, b);
        op.createInstructions();
        ds.counterFirstInstructions = ds.counterFirstInstructions + 1;
        //Lägger körinstruktionerna från createInstructions i en array kallad instructions 
        //(bör vara lika lång som arcRoute som just nu är 100)
        //instructions = new String [ds.arcRoute.length];
        //instructions = op.createInstructions().split("\n"); // innehåller körinstruktioner, en i taget. NÄr AGVn har svarat med att de utfört et "kommando" skickas näst kommando i instructions.


        /*
         * För att få fram kartan verkar det som att vi behöver ändra lite i GUI.
         */
        gui = new GUI(ds);
        gui.setVisible(true);

        /*
         * Initialize RobotRead with its Thread
         */
        rr = new RobotRead(ds, gui);
        t1 = new Thread(rr);
        t1.start();

        /*
         * Initialize GuiUpdate with its Thread
         */
        gu = new GuiUpdate(ds, gui);
        t2 = new Thread(gu);
        t2.start();

        //Här ska vi bestämma vilket/vilka uppdrag som vi vill utföra.
        //Vi måste ta det första. Vi måste hålla oss till vår kapacitet
        //Vi måste kontrollera att samåkning tillåts.
        // Skapa arrayer för det vi vill spara 
        uppdragsIDArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationPlatserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        passengersArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        samakningArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        pointsArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        //platserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragX = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragY = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];

        //Lägg rätt information i respektive array
        for (int i = 0; i < Integer.parseInt(ha.messagetype(ds.closestPlats)[0]); i++) {
            dummyList3 = ha.messagetype(ds.closestPlats)[i + 1].toString().split(";");
            uppdragsIDArray[i] = dummyList3[0];
            destinationUppdragArray[i] = dummyList3[1]; //Fattar den att detta är två olika noder?
            passengersArray[i] = dummyList3[2];
            samakningArray[i] = dummyList3[3];
            pointsArray[i] = dummyList3[4];
        }

        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats (dest_node) som har uppdrag med op.createPlan
        //Här ska vi bestämma vilket/vilka uppdrag som vi vill utföra.
        //Vi måste ta det första. Vi måste hålla oss till vår kapacitet
        //Vi måste kontrollera att samåkning tillåts.
        // Skapa arrayer för det vi vill spara 
        uppdragsIDArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationPlatserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        passengersArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        samakningArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        pointsArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        //platserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragX = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        destinationUppdragY = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];

        //Lägg rätt information i respektive array
        for (int i = 0; i < Integer.parseInt(ha.messagetype(ds.closestPlats)[0]); i++) {
            dummyList3 = ha.messagetype(ds.closestPlats)[i + 1].toString().split(";");
            uppdragsIDArray[i] = dummyList3[0];
            destinationUppdragArray[i] = dummyList3[1]; //Fattar den att detta är två olika noder?
            passengersArray[i] = dummyList3[2];
            samakningArray[i] = dummyList3[3];
            pointsArray[i] = dummyList3[4];
        }

//        if (5 >= ds.cap) { //Integer.parseInt(passengersArray [0])
//            //Meddela företagsgrupp att närmsta upphämtningsplats är closestPlats
//            //och att avståndet dit är tempis + resterande routeCost
//            //och att vi tänker ta första uppdraget.
//            ds.cap -= 1;//Integer.parseInt(passengersArray[0]);
//            String message = ds.closestPlats + "#" + tempis + ds.routeCost + "#" + "1"; //uppdragsIDArray[0]
//            hg.putmessage(message);
//
//        } else {
//            //Här ska vi ta fler uppdrag från listan av de som vill samåka
//            //Ska med en loop kontrollera om någon vill samåka.
//            for (int i = 0; i < samakningArray.length; i++) {
//
//                if (Integer.parseInt(samakningArray[i]) == 1) {
//                    //av de som vill samåka, ska vi hitta den som avviker från rutten så lite som möjligt mha createplan. 
//                    //Vi tänker att vi behöver en ny funktion i optplan som beräknar detta genom att kika på vilka arcROutes som 
//                    //liknar varandra mest av de uppdrag som finns på upphämtningsplatsen
//                }
//            }
//        }
        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats med op.createPlan (tror inte detta stämmer längr/A)
        // Slut på listaplatser--------------------------------------------------
//        // Slut på listaplatser--------------------------------------------------
//        // Loop för att ta fram alla uppdrag på alla de platser som fanns i listaplatser
//        for (int j = 0; j < Integer.parseInt(ha.messagetype()[0]); j++) {
//            //listaUppdrag----------------------------------------------------------
//            // Skapa arrayer för det vi vill spara 
//            uppdragsIDArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            destinationPlatserArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            destinationUppdragArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            passengersArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            samakningArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            pointsArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            //platserArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            destinationUppdragX = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//            destinationUppdragY = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
//
//            //Lägg rätt information i respektive array
//            for (int i = 0; i < Integer.parseInt(ha.messagetype(platser[j])[0]); i++) {
//                dummyList3 = ha.messagetype(platser[j])[i + 1].toString().split(";");
//                uppdragsIDArray[i] = dummyList3[0];
//                destinationUppdragArray[i] = dummyList3[1]; //Fattar den att detta är två olika noder?
//                passengersArray[i] = dummyList3[2];
//                samakningArray[i] = dummyList3[3];
//                pointsArray[i] = dummyList3[4];
//            }
//
//            //Ta fram koordinater för avlämningsplatserna för de uppdrag som finns på den aktuella platsen (ha.messagetype(platser[j]))
//            for (int i = 0; i < Integer.parseInt(ha.messagetype(platser[j])[0]); i++) {
//                dummyList4 = destinationUppdragArray[i].toString().split(",");
//                destinationUppdragX[i] = dummyList4[0];
//                destinationUppdragY[i] = dummyList4[1];
    }
//            System.out.println("X " + Arrays.toString(destinationUppdragX));
//            System.out.println("Y " + Arrays.toString(destinationUppdragY));
    //slut på listaUppdrag----------------------------------------------

    //ha.messagetype("A", 1, 8, 4);      
    /*
        // Testing testing såhär ska vi skicka till AGVn typ

        String start = "#";
        String enable = "1";
        char kontroll = 'a';
        kontroll++;
        
        String meddelande = start + enable + kontroll;
        
        System.out.println("meddelande: " + meddelande);
     */
//        String start = "#";
//        String enable = "1";
//        char kontroll = 'a';
//        kontroll++;
//
//        String meddelande = start + enable + kontroll;
//
//        System.out.println("meddelande: " + meddelande);
    //Slut på testing
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Planeringssystem x = new Planeringssystem();

    }

}
