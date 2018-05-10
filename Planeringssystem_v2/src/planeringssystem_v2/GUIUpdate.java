package planeringssystem_v2;

import java.util.Random;

public class GUIUpdate implements Runnable {

    private int sleepTime;
    private static Random generator = new Random();
    private GUI gui;
    private DataStore ds;
    private int count;

    public GUIUpdate(GUI gui, DataStore ds) {
        this.gui = gui;
        this.ds = ds;
        sleepTime = generator.nextInt(20000);
        count = 0;
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
//                System.out.println(i);
                Thread.sleep(1000);
//                gui.appendErrorMessage("Jag är tråd GuiUpdate, för"
//                        + i + ":te gången");
                count++;
                
                //Gör knappen blå äntligen
                if (!ds.bt) {
                    gui.jTextField1.setBackground(new java.awt.Color(255, 0, 51));
                    gui.jTextField1.setText("No Connection");
                } else if (ds.bt) {
                    if ((count & 1) == 0) {
                        gui.jTextField1.setBackground(new java.awt.Color(10, 94, 203));
                        gui.jTextField1.setText("Connection");
                    } else {
                        gui.jTextField1.setBackground(new java.awt.Color(255, 255, 255));
                        gui.jTextField1.setText("Connection");
                    }
                }
                gui.repaint();

                i++;
            }
        } catch (InterruptedException exception) {
            System.out.println("Catch");
        }
//                 gui.appendErrorMessage("GuiUpdate är nu klAr.");

    }

}
