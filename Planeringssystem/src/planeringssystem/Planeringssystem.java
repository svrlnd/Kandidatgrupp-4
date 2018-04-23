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
    String[] instructions;
    String[] dummyList1;
    String[] dummyList2;
    String[] dummyList3;
    String[] dummyList4;
    String[] platser;
    String[] koordinater;
    int[] platsX;
    int[] platsY;
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
        op = new OptPlan(ds);

        //op.createPlan(24,9);
        op.createInstructions();
        //Lägger körinstruktionerna från createInstructions i en array kallad instructions (bör vara lika lång som arcRoute som just nu är 100)
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

        /*
         * Testar att skapa en instance av HTTPgrupp för att testa metoderna
         */
        hg = new HTTPgrupp();

//        hg.putmessage(6, "Testinggrupp4");       
//        hg.getmessage(145);

        /*
         * Testar att skapa en instance av HTTPanrop för att testa metoderna
         */
        ha = new HTTPanrop();
        //Allmän kommentar om detta kodavsnitt: 
        //Här tänker vi att vi vill göra en loop som kör lika många varv som det finns platser i listaplatser. 
        //Sen ska avstånden beräknas för varje uppdrag som finns på respektive plats. /A och G

        //listaplatser----------------------------------------------------------
        dummyList1 = new String[Integer.parseInt(ha.messagetype()[0])];
        dummyList2 = new String[Integer.parseInt(ha.messagetype()[0])];
        platser = new String[Integer.parseInt(ha.messagetype()[0])];
        koordinater = new String[Integer.parseInt(ha.messagetype()[0])];
        platsX = new int[Integer.parseInt(ha.messagetype()[0]) + 1];
        platsY = new int[Integer.parseInt(ha.messagetype()[0]) + 1];

        //lägg alla upphämtningsplatser i "platser", samt deras koordinater (x,y) i "koordinater".
        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
            dummyList1 = ha.messagetype()[i + 1].toString().split(";");
            platser[i] = dummyList1[0];
            koordinater[i] = dummyList1[1];
        }

        //Dela upp koordinaterna i varsin array ("platsX" och "platsY") för X- resp. Y-koordinaterna.
        for (int j = 0; j < Integer.parseInt(ha.messagetype()[0]); j++) {
            dummyList2 = koordinater[j].toString().split(",");
            platsX[j] = Integer.parseInt(dummyList2[0]);
            platsY[j] = Integer.parseInt(dummyList2[1]);
        }
        platsX[2] = 435;
        platsY[2] = 16;

        double temp = 0;
        int dest_node = 0;
        int next_start_node = 0;
        int closest_postive_node = 345; //tillräckligt stort?
        int closest_negative_node = 345;
        double distance_positive = Double.MAX_VALUE;
        double distance_negative = Double.MAX_VALUE;
        //Leta fram den positiva och den negativa nod som är närmast varje upphämtningsplats
        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
            for (int j = 0; j < ds.nodes; j++) {
                //Vilka noder har samma x-koordinat?
                if (platsX[2] == ds.nodeX[j]) {

                    //Är avstånet till dess y-koordinat positivt eller negativt?
                    if (ds.nodeY[j] - platsY[2] < 0) {
                        temp = Math.abs(platsY[2] - ds.nodeY[j]);
                        if (temp < distance_negative) {
                            distance_negative = temp;
                            closest_negative_node = j + 1;
                        }
                    } else {
                        temp = Math.abs(platsY[i] - ds.nodeY[j]);
                        if (temp < distance_positive) {
                            distance_positive = temp;
                            closest_postive_node = j + 1;
                        }
                    }
                    //Om ingen nod hade samma x-koordinat, vilka noder har då samma y-koordinat?
                } else if (platsY[i] == ds.nodeY[j]) {
                    //Är avstånet till dess x-koordinat positivt eller negativt?
                    if (ds.nodeX[j] - platsX[i] < 0) {
                        temp = Math.abs(platsX[i] - ds.nodeX[j]);
                        if (temp < distance_negative) {
                            distance_negative = temp;
                            closest_negative_node = j + 1;
                        }
                    } else {
                        temp = Math.abs(platsX[i] - ds.nodeX[j]);
                        if (temp < distance_positive) {
                            distance_positive = temp;
                            closest_postive_node = j + 1;
                        }
                    }
                }
            }
            System.out.println("Närmsta positiva nod är: " + closest_postive_node);
            System.out.println("Närmsta negativa nod är: " + closest_negative_node);
            //Nu ska vi ta reda på vilken länk dessa noder tillhör. 
            for (int a = 0; a < ds.arcs; a++) {
                if (ds.arcStart[a] == closest_postive_node && ds.arcEnd[a] == closest_negative_node
                        || ds.arcEnd[a] == closest_postive_node && ds.arcStart[a] == closest_negative_node) {
                    dest_node = ds.arcEnd[a];
                    next_start_node = ds.arcStart[a];
                    op.createPlan(17, dest_node);
                }

            }
        }

        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats (dest_node) som har uppdrag med op.createPlan
        
        // Slut på listaplatser--------------------------------------------------
        // Loop för att ta fram alla uppdrag på alla de platser som fanns i listaplatser
        for (int j = 0;
                j < Integer.parseInt(ha.messagetype()[0]); j++) {
            //listaUppdrag----------------------------------------------------------
            // Skapa arrayer för det vi vill spara 
            uppdragsIDArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            destinationPlatserArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            destinationUppdragArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            passengersArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            samakningArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            pointsArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            //platserArray = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            destinationUppdragX = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];
            destinationUppdragY = new String[Integer.parseInt(ha.messagetype(platser[j])[0])];

            //Lägg rätt information i respektive array
            for (int i = 0; i < Integer.parseInt(ha.messagetype(platser[j])[0]); i++) {
                dummyList3 = ha.messagetype(platser[j])[i + 1].toString().split(";");
                uppdragsIDArray[i] = dummyList3[0];
                destinationUppdragArray[i] = dummyList3[1]; //Fattar den att detta är två olika noder?
                passengersArray[i] = dummyList3[2];
                samakningArray[i] = dummyList3[3];
                pointsArray[i] = dummyList3[4];
            }

            //Ta fram koordinater för avlämningsplatserna för de uppdrag som finns på den aktuella platsen (ha.messagetype(platser[j]))
            for (int i = 0; i < Integer.parseInt(ha.messagetype(platser[j])[0]); i++) {
                dummyList4 = destinationUppdragArray[i].toString().split(",");
                destinationUppdragX[i] = dummyList4[0];
                destinationUppdragY[i] = dummyList4[1];
            }
//            System.out.println("X " + Arrays.toString(destinationUppdragX));
//            System.out.println("Y " + Arrays.toString(destinationUppdragY));
            //slut på listaUppdrag----------------------------------------------

        }


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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Planeringssystem x = new Planeringssystem();

    }

}
