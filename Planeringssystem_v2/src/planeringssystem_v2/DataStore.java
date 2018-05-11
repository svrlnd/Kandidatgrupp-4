/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planeringssystem_v2;

import java.io.File;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Simon
 */
public class DataStore {

    String fileName = null;
    int nodes;
    int arcs;
    double[] nodeX;
    double[] nodeY;
    int[] arcStart;
    int[] arcEnd;
    int[] arcCost;
    boolean networkRead;
    boolean updateUIflag;
    int[] arcColor;
    double[] nodeNumber;
    int[] closeArc;
    double dummyX;
    double dummyY;
    String meddelande_in;
    String meddelande_ut;
    char enable;
    char ordernummer;
    int cap;
    int initial_cap;
    String korinstruktion;
    char kontroll;
    char kontrollAGV;
    String spegling;
    char antal_passagerare;
    int counterFirstInstructions;
    String[] platsLista;
    String[] startSlutNoder;
    String[] platser;
    String[] noder;
    int[] startnod;
    int[] slutnod;
    int distanceCP;
    int dest_node;
    int last_node;
    int first_node;
    String valdPlats;
    int a;
    LinkedList<Integer> dummyArcEnd;
    LinkedList<Integer> dummyArcStart;
    LinkedList<Integer> dummyStartKoorX;
    LinkedList<Integer> dummyStartKoorY;
    LinkedList<Integer> dummyEndKoorX;
    LinkedList<Integer> dummyEndKoorY;
    LinkedList<String> instructions;
    LinkedList<String> instructionsAGV;
    LinkedList<Integer> arcRoute;
    String direction;
    String directionNextArc;

    String[] uppdragsIDArray;
    String[] destinationPlatserArray;
    String[] destinationUppdragArray;
    String[] passengersArray;
    String[] samakningArray;
    String[] pointsArray;
    String[] destinationUppdragStart;
    String[] destinationUppdragSlut;
    LinkedList<String> uppdrag;
    
    int currentPassengers1; // så många kunder vi faktiskt tar från första uppdraget i kön
    int currentPassengers2; // Så många kunder vi faktiskt tar från ett annat uppdrag i listan (samåkning)
    boolean bt;
    boolean uppdragFylld;
    
    int currentArc;
    int distanceDO;
    int s;

    public DataStore() {
        nodes = 0;
        arcs = 0;
        nodeX = new double[1000];
        nodeY = new double[1000];
        arcStart = new int[1000];
        arcEnd = new int[1000];
        arcCost = new int[1000];
        networkRead = false;
        updateUIflag = false;
        arcColor = new int[1000];
        nodeNumber = new double[1000];
        closeArc = new int[1000];
        dummyX = 0;
        dummyY = 0;
        meddelande_in = "";
        meddelande_ut = "";
        ordernummer = '!';
        cap = 5;
        initial_cap = 5;
        korinstruktion = "";
        kontroll = '!';
        kontrollAGV = '!';
        spegling = "";
        antal_passagerare = '0';
        counterFirstInstructions = 0;
        distanceCP = 0;
        dest_node = 0;
        last_node = 0;
        first_node = 47;
        direction = "W";
        valdPlats = "";
        a = 0;
        dummyArcEnd = new LinkedList<Integer>();
        dummyArcStart = new LinkedList<Integer>();
        dummyStartKoorX = new LinkedList<Integer>();
        dummyStartKoorY = new LinkedList<Integer>();
        dummyEndKoorX = new LinkedList<Integer>();
        dummyEndKoorY = new LinkedList<Integer>();
        instructions = new LinkedList<String>();
        instructionsAGV = new LinkedList<String>();
        arcRoute = new LinkedList<Integer>(); 
        directionNextArc = "";
        uppdrag = new LinkedList<String>();
        currentPassengers1 = 0;
        currentPassengers2 = 0;
        bt = false;
        uppdragFylld = false;
        distanceDO = 0;
    }

    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void readNet() {
        String line;

        if (fileName == null) {
            System.err.println("No file name set. Data read aborted.");
            return;
        }
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file, "iso-8859-1");
            String[] sline;

            // Read number of nodes
            line = (scanner.nextLine());
            nodes = Integer.parseInt(line.trim());
            line = scanner.nextLine();
            arcs = Integer.parseInt(line.trim());

            // Debug printout: network size data
//            System.out.println("Nodes: " + nodes);
//            System.out.println("Arcs: " + arcs);
            // Read nodes as number, x, y
            for (int i = 0; i < nodes; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                nodeNumber[i] = Double.parseDouble(sline[0].trim());
                nodeX[i] = Double.parseDouble(sline[1].trim());
                nodeY[i] = Double.parseDouble(sline[2].trim());
            }

            // Debug printout: print data for node 1
//            System.out.println("Node 1: " + nodeX[0] + " " + nodeY[0]);
            // Read arc list as start node number, end node number
            for (int i = 0; i < arcs; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                arcStart[i] = Integer.parseInt(sline[0].trim());
                arcEnd[i] = Integer.parseInt(sline[1].trim());
                closeArc[i] = Integer.parseInt(sline[2].trim());

                // Här beräknas längden av länkarna
                dummyX = nodeX[arcStart[i] - 1] - nodeX[arcEnd[i] - 1];
                dummyY = nodeY[arcStart[i] - 1] - nodeY[arcEnd[i] - 1];
                arcCost[i] = (int) (closeArc[i] * round(sqrt(pow(dummyX, 2) + pow(dummyY, 2))));

            }

            networkRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
