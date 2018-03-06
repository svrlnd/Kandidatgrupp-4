package planeringssystem;

public class Planeringssystem {
    
    DataStore ds;
    GUI gui;
    RobotRead rr;
    Thread t1;
    OptPlan op;
    
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
        
       
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Planeringssystem x = new Planeringssystem();
        
    }

}
