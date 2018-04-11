package planeringssystem;

import java.awt.event.WindowAdapter;

public class Planeringssystem {
    
    DataStore ds;
    GUI gui;
    RobotRead rr;
    GuiUpdate gu;
    Thread t1;
    Thread t2;
    OptPlan op;
    HTTPgrupp hg;
   
    Planeringssystem(){
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
        op.createPlan();
        //op.createInstructions();
        
        
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
        //hg = new HTTPgrupp();
        //hg.putmessage(0, "Testinggrupp4");
        
        
        // Testing testing såhär ska vi skicka till AGVn typ
        String start = "#";
        String enable = "1";
        char kontroll = 'a';
        kontroll++;
        
        String meddelande = start + enable + kontroll;
        
        System.out.println("meddelande: " + meddelande);
        //Slut på testing
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Planeringssystem x = new Planeringssystem();
        
    }

}
