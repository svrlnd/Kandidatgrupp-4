package planeringssystem;

import java.io.File;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import java.util.Scanner;
import java.util.*;

/*
 * Den här klassen är inkopierad från lab 2 rakt av. 
 * Jag, Anna, tror att vi kommer behöva den men annars ändrar vi det i efterhand.
 */
public class DataStore {

    String fileName = null;
    int nodes;
    int arcs;
    double[] nodeNumber;
    double[] nodeX;
    double[] nodeY;
    int[] arcStart;
    int[] arcEnd;
    int[] closeArc;
    int[] arcCost;
    int routeCost;
    int min; //Kostnaden till den närmsta upphämtningsplatsen
    LinkedList<Integer> dummyArcStart;
    LinkedList<Integer> dummyArcEnd;
    LinkedList<Integer> dummyStartKoorX;
    LinkedList<Integer> dummyStartKoorY;
    LinkedList<Integer> dummyEndKoorX;
    LinkedList<Integer> dummyEndKoorY;
    LinkedList<String> instructions;
    boolean networkRead;
    boolean updateUIflag;
    double robotX;
    double robotY;
    int[] arcColor;
    LinkedList<Integer> arcRoute;
    double dummyX;
    double dummyY;
    int currentDummyArc;
    int nextDummyArc;
    int dummyArc;
    String direction;
    String directionNextArc;
    int cap; // AGV capacity
    //Testar att skapa dessa två men vi kanske får ta bort dem sen /Anna
    RobotRead rr;
    GUI gui;
    boolean flagCoordinates;
    //meddelande-variabler
    char enable;
    char ordernummer;
    char antal_passagerare;
    String korinstruktion; //Kan denna vara String eller måste den vara en char? 
    char kontroll;
    char kontrollAGV;
    String spegling;
    String meddelande_in;
    String meddelande_ut;
    String closestPlats;
    int firstNode;
    int lastNode;
    int counterFirstInstructions;
    int dest_node;
    String[] uppdragsIDArray;
    String[] destinationPlatserArray;
    String[] destinationUppdragArray;
    String[] passengersArray;
    String[] samakningArray;
    String[] pointsArray;
    String[] destinationUppdragX;
    String[] destinationUppdragY;
    int a;
    String[] platsLista;
    String[] startSlutNoder;
    String[] platser;
    String[] noder;
    int[] startnod;
    int[] slutnod;
    
    // Testing testing
    public DataStore() {
        // Initialize the datastore with fixed size arrays for storing the network data
        nodes = 0;
        arcs = 0;
        nodeNumber = new double[1000];
        nodeX = new double[1000];
        nodeY = new double[1000];
        arcStart = new int[1000];
        arcEnd = new int[1000];
        closeArc = new int[1000];
        arcCost = new int[1000];
        dummyArcStart = new LinkedList<Integer>();
        dummyArcEnd = new LinkedList<Integer>();
        dummyStartKoorX = new LinkedList<Integer>();
        dummyStartKoorY = new LinkedList<Integer>();
        dummyEndKoorX = new LinkedList<Integer>();
        dummyEndKoorY = new LinkedList<Integer>();
        instructions = new LinkedList<String>();
        networkRead = false;
        updateUIflag = false;
        arcColor = new int[128];
        arcRoute = new LinkedList<Integer>();
        dummyX = 0;
        dummyY = 0;
        cap = 4;
        direction = "W";
        enable = '1';
        ordernummer = '!'; //Denna bör uppdaters enligt ASCII tabellen efter varje slutfört uppdrag
        antal_passagerare = '0';
        korinstruktion = "d";
        kontroll = '!';
        kontrollAGV = ' ';
        spegling = "";
        meddelande_in = "";
        meddelande_ut = "";
        directionNextArc = "";
        firstNode = 71;
        counterFirstInstructions = 0;

        // Kan man bara skapa nya instanser av dessa på det här viset? KOpplas det ändå samman med allt annat?
        gui = new GUI(this);
        rr = new RobotRead(this, gui);
        flagCoordinates = false;
        currentDummyArc = rr.getCurrentArc();
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
                arcCost[i] = closeArc[i] * (int) round(sqrt(pow(dummyX, 2) + pow(dummyY, 2)));

            }

            networkRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {
            e.printStackTrace();
        }

        

        //Man måste få detta att uppdatera sig på något vis.. Kanske med en while-loop och en flag
        //Detta sker ändå före eftersom RobtoRead är en tråd?
        /*while (flagCoordinates) {
            robotX = rr.getCurrentX();
            robotY = rr.getCurrentY();
        }*/
    }
}
