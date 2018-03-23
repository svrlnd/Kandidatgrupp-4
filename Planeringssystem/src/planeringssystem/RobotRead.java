package planeringssystem;

import java.util.Random;

public class RobotRead implements Runnable {

    private int sleepTime;
    private static Random generator = new Random(); // Vet inte om vi kommer behöva denna? Nej, tror vi kommer sätta en uppdateringstid som vi vill ha/S
    private GUI gui;
    private DataStore ds;

    public RobotRead(DataStore ds, GUI gui) {
        this.gui = gui;
        this.ds = ds;
        sleepTime = generator.nextInt(20000);
    }

    @Override
    public void run() {
        try {
            // Hur länge RobotReaden ska köras kanske inte behöver skrivas ut?
            gui.appendErrorMessage("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            int i = 1;

            // Denna borde köras så länge som roboten fortfarande kör (eventuellt ta bort i++)
            while (i <= 20) {
                while (gui.getButtonState()) {
                    Thread.sleep(sleepTime / 1000);
                }
                Thread.sleep(sleepTime / 20);
                // Här ska vi istället skriva ut meddelandet som kommer i från roboten!
                gui.appendErrorMessage("Jag är tråd RobotRead för " + i + ":e gången.");
                i++;
            }
        } catch (Exception e) {
        }

        // Ska vi ha kvar denna?
        gui.appendErrorMessage(
                "Robotread är nu klar");
    }
    
    public int getCurrentCapacity(int cap) {
        return cap; //minus det antal passagerare vi plockar upp på nuvarande uppdrag. 
        //Vi måste komma på ett sätt att lägga till capacity igen när vi lämnat av folk.
    }
}
