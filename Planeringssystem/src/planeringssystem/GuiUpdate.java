package planeringssystem;

import java.util.Random;

//Denna vill vi ha för att visa rutt och position på agv

public class GuiUpdate implements Runnable {
    
    private int sleepTime;
    private static Random generator = new Random();
    private DataStore ds;
    
    public GuiUpdate(DataStore ds) {
        this.ds = ds;
        sleepTime = generator.nextInt(20000); //Vad ska vi ha för sleeptime?
    }
    
    @Override
    public void run(){
    try{
        // Hur länge GuiUpdate ska köras kanske inte behöver skrivas ut?
        ds.gui.appendErrorMessage("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder.");
        
        int i = 1;
        while(true){ // Denna borde köras så länge som roboten fortfarande kör (eventuellt ta bort i++)
             while (ds.gui.getButtonState()) {
                    Thread.sleep(sleepTime / 1000);
                }
            Thread.sleep(sleepTime / 20);
            // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
            ds.gui.appendErrorMessage("Jag är tråd GuiUpdate! För " + i + ":e gången.");
            
            //ds.robotX = ds.robotX - 10; // Här kommer AGV:ns position istället läsas in
            
            ds.gui.repaint();
            i++;
        }
    
    } catch (InterruptedException exception){
        System.out.println("Catch i GuiUpdate");
    }
    
    // Ska vi ha kvar denna?
    ds.gui.appendErrorMessage("GuiUpdate är nu klar!");

    }
   
}
