package planeringssystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    DataStore ds;

    MapPanel(DataStore ds) {
        this.ds = ds;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Color LIGHT_COLOR = new Color(150, 150, 150);
        final Color DARK_COLOR = new Color(0, 0, 0);
        final Color RED_COLOR = new Color(255, 0, 0);
        final Font THE_FONT = new Font ("Fonten", Font.PLAIN,10);
        int x, y;
        int x1, y1;
        int x2, y2;

        final int circlesize = 10;
        final int ysize = 350;
        final int xsize = 700;

        if (ds.networkRead == true) { // Only try to plot is data has been properly read from file

            // Compute scale factor in order to keep the map in proportion when the window is resized
            int height = getHeight();
            int width = getWidth();
            double xscale = 1.0 * width / xsize;
            double yscale = 1.0 * height / ysize;

//            g.setColor(Color.GREEN);
            g.setFont(THE_FONT);

//            // Draw robot as a large circle
//            g.drawOval((int) (ds.robotX * xscale) - ((circlesize + 10) / 2), height - (int) (ds.robotY * yscale) - (circlesize + 10) / 2,
//                    circlesize + 10, circlesize + 10);
//            g.fillOval((int) (ds.robotX * xscale) - ((circlesize + 10) / 2), height - (int) (ds.robotY * yscale) - (circlesize + 10) / 2,
//                    circlesize + 10, circlesize + 10);

            // Draw nodes as circles
            g.setColor(DARK_COLOR);
            for (int i = 0; i < ds.nodes; i++) {
                x = (int) (ds.nodeX[i] * xscale);
                y = (int) (ds.nodeY[i] * yscale);

                g.fillOval(x - (circlesize / 2), height - y - circlesize / 2, circlesize, circlesize);
            }

            // Draw arcs
            g.setColor(RED_COLOR);
            for (int i = 0; i < ds.arcs; i++) {
                if (ds.arcColor[i] == 0) {
                    g.setColor(DARK_COLOR);
                } else if (ds.arcColor[i] == 1) {
                    g.setColor(RED_COLOR);
                }
                x1 = (int) (ds.nodeX[ds.arcStart[i] - 1] * xscale);
                y1 = (int) (ds.nodeY[ds.arcStart[i] - 1] * yscale);
                x2 = (int) (ds.nodeX[ds.arcEnd[i] - 1] * xscale);
                y2 = (int) (ds.nodeY[ds.arcEnd[i] - 1] * yscale);
                g.drawLine(x1, height - y1, x2, height - y2);
                // System.out.println("Arc "+i+": "+ds.arcStart[i]+" "+ds.arcEnd[i]);
                g.drawString(toString(ds.arcCost[i]), (x1 + x2) / 2, ((height - y1) + (height - y2)) / 2);

            }
        }
    } // end paintComponent

    private String toString(int str) {
        return "" + str + "";
    }
}
