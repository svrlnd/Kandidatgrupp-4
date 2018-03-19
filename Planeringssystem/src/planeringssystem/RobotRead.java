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
            gui.appendErrorMessage("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            int i = 1;
            while (i <= 20) {
                Thread.sleep(sleepTime / 20);
                gui.appendErrorMessage("Jag är tråd RobotRead för " + i + ":e gången.");
                i++;
            }
        } catch (Exception e) {
        }

        gui.appendErrorMessage("Robotread är nu klar");
    }
}
