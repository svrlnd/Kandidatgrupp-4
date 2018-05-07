/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planeringssystem_v2;

import java.util.Random;

/**
 *
 * @author Simon
 */

public class GUIUpdate implements Runnable {

    private int sleepTime;
    private static Random generator = new Random();
    private GUI gui;


    public GUIUpdate(GUI gui) {
        this.gui = gui;
        sleepTime = generator.nextInt(20000);
    }

    @Override
    public void run() {
        try {
//            while (!ds.updateUIflag) {
//                Thread.sleep(sleepTime / 100);
//            }
//            gui.appendErrorMessage("GuiUpdate startar och kommer köra i "
//                    + sleepTime + " millisekunder");

            int i = 1;
            while (true) {
                System.out.println(i);
                Thread.sleep(1000);
                gui.appendErrorMessage("Jag är tråd GuiUpdate, för"
                        + i + ":te gången");
                gui.repaint();
                
                i++;
            }
        } 
        
        catch (InterruptedException exception) {
            System.out.println("Catch");
        }
                 gui.appendErrorMessage("GuiUpdate är nu klAr.");
   
    }

}
