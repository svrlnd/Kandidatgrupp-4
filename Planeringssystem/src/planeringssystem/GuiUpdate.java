package planeringssystem;

import java.util.Random;

/**
 *
 * @author annda430
 */
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
    public void run() {
        try {
            
            gui.appendErrorMessage("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder.");
            int i = 1;
            while (i <= 20) {
                while (gui.getButtonState()) {                    
                    Thread.sleep(sleepTime / 1000);
                }
                
                Thread.sleep(sleepTime / 20);
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
