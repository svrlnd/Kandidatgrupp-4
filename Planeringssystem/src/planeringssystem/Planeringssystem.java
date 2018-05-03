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
    UppdragsInfo ui;
    CreateMessage cm;
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

        op = new OptPlan(ds);
        
        cp = new ClosestPlats(ds, ha, op);
        
        
        
        cp.getClosestPlats();
        
        ui = new UppdragsInfo(ds, ha);
        
        ui.UppdragsInfo(ds, ha);
        
        cm = new CreateMessage(ds,cp);
        
        System.out.println(cm.createMessageAGV());
        
        

        op.createPlan(ds.a, ds.dest_node);
        op.createInstructions();
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

        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats (dest_node) som har uppdrag med op.createPlan
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
