package planeringssystem;

import java.util.Random;

public class GuiUpdate implements Runnable {
    
    private int sleepTime;
    private static Random generator = new Random();
    private GUI gui;
    private DataStore ds;
    
    public GuiUpdate(DataStore ds, GUI gui) {
        this.gui = gui;
        this.ds = ds;
        sleepTime = generator.nextInt(20000);
    }
    
    @Override

    public void run(){
    try{
        // Hur länge GuiUpdate ska köras kanske inte behöver skrivas ut?
        gui.appendErrorMessage("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder.");
        
        int i = 1;
        while(i <= 20){ // Denna borde köras så länge som roboten fortfarande kör (eventuellt ta bort i++)
            Thread.sleep(sleepTime / 20);
            // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
            gui.appendErrorMessage("Jag är tråd GuiUpdate! För " + i + ":e gången.");
            ds.robotX = ds.robotX - 10; // Här kommer AGV:ns position istället läsas in
            gui.repaint();
            i++;
        }
    
    } catch (InterruptedException exception){}
    
    // Ska vi ha kvar denna?
    gui.appendErrorMessage("GuiUpdate är nu klar!");

    public void run() {
        try {
            
            gui.appendErrorMessage("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder.");
            int i = 1;
                while (gui.getButtonState()) {                    
                    Thread.sleep(sleepTime / 1000);
                
                
                gui.appendErrorMessage("Jag är tråd GuiUpdate! För " + i + ":e gången.");
                ds.robotX = ds.robotX - 10; // Här kommer AGV:ns position istället läsas in
                gui.repaint();
                i++;
            }
            
        } catch (InterruptedException exception) {
        }
        gui.appendErrorMessage("GuiUpdate är nu klar!");
    }
    
}
