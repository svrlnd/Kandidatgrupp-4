package planeringssystem;

import java.util.*;

public class OptPlan {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;
    private int cost;

    public OptPlan(DataStore ds) {
        this.ds = ds;
    }

    public int createPlan(int start_node, int dest_node) {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        cost = 0;

        //Här hade vi scanner för att läsa in var vi ville börja och sluta
        //Men det tog jag väck, för det ska vi ju läsa av hos AGV sen /S
        start_node = 67; // Här ska vi istället ta AGVs nuvarande position, från BT-klassen
        dest_node = 3;// Här ska vi istället ta in platser från HTTP-server

        int start_arc = 1; //här vill vi läsa in vilket nod vi är på?
        int dest_arc = 17;

        //Set up network
        for (int i = 0; i < ds.nodes; i++) {
            Vertex location = new Vertex("" + (i + 1), "Nod #" + (i + 1));
            nodes.add(location);
        }
        for (int i = 0; i < ds.arcs; i++) {
            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1),
                    nodes.get(ds.arcEnd[i] - 1), ds.arcCost[i] - 1); //Last argument is arccost
            edges.add(lane);
        }

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        //Beräkna kortaste rutt
        dijkstra.execute(nodes.get(start_node));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(dest_node));

        //Hämta kortast rutt
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = 0; j < ds.arcs; j++) {
                if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                        && ds.arcEnd[j] == Integer.parseInt(path.get(i + 1).getId())) {
                    //System.out.println("Arc: " + j); //Skirver ut de arcs som ingår i rutten
                    ds.arcColor[j] = 1;
                    ds.arcRoute[i] = j;
                    cost += ds.arcCost[j];
                }
            }
        }
        // Förklara rutt för robot, dvs meddela vilken som är nästa båge. (Använder vi ens dessa?)
        start_arc = ds.arcRoute[0];
        dest_arc = ds.arcRoute[ds.arcRoute.length - 1];// minus ett ty plats 4 (array börjar på 0)

        // Detta bör hända när AGV:n läser in en QR-kod som säger i vilken korsning den är
        for (int i = 0; i < ds.arcRoute.length; i++) {
            if (ds.currentDummyArc == ds.arcRoute[i]) { // && roboten är i en korsning
                ds.nextDummyArc = ds.arcRoute[i + 1];
            }
        }
        return cost;
    }

    public String createInstructions() {
        String instructions = "";
        //Gabriella testar lite, så denna borde kollas igenom en extra gång!

        //Loopar igenom alla länkar i arcRoute för att ta reda på startnoden och slutnoden för dessa länkar
//        for (int i = 0; i < ds.arcRoute.length; i++) {
//            ds.n = ds.arcRoute[i];
//
//            for (int j = 0; j < ds.arcStart.length; j++) {
//                if (j + 1 == (ds.n)) {
//                    //Här läggs start-noderna i arcRoute in i dummyArcStart
//                    ds.dummyArcStart[i] = ds.arcStart[j + 1];
//                }
//            }
//            for (int k = 0; k < ds.arcEnd.length; k++) {
//                if (k + 1 == ds.n) {
//                    //Här läggs slut-noderna i arcRoute in i dummyArcEnd
//                    ds.dummyArcEnd[i] = ds.arcEnd[k + 1];
//                }
//            }
        //Kontrollerar att dummtArcStart och dummyArcEnd är lika lång
        //System.out.println("hhejjejj" + ds.dummyArcStart.length);
        //System.out.println(ds.dummyArcEnd.length);
        //VI MÅSTE FIXA EN IF-SATS SOM FIXAR LÄNGEDEN PÅ ARCROUTE FÖR JUST NU ÄR DEN 1000.
//        }
//        //Nu har vi nodernas nummer, nu måste vi spara dessa koordinaterna
//        //I denna loop sparas alla x-koordinater för start-noderna
//        for (int m = 0; m < ds.dummyArcStart.length; m++) {
//
//            for (int n = 0; n < ds.nodeX.length; n++) {
//                if (ds.dummyArcStart[m] == ds.nodeX[n]);
//                {
//                    ds.dummyStartKoorX[m] = (int) ds.nodeX[n];
//                }
//            }
//        }
//        //I denna loop sparas alla y-koordinater för start-noderna
//        for (int l = 0; l < ds.dummyArcStart.length; l++) {
//
//            for (int n = 0; n < ds.nodeY.length; n++) {
//                if (ds.dummyArcStart[l] == ds.nodeY[n]);
//                {
//                    ds.dummyStartKoorY[l] = (int) ds.nodeY[n];
//                }
//            }
//        }
        //I denna loop sparas alla x-koordinater för slut-noderna
//        for (int o = 0; o < ds.dummyArcEnd.length; o++) {
//
//            for (int p = 0; p < ds.nodeX.length; p++) {
//                if (ds.dummyArcEnd[o] == ds.nodeX[p]);
//                {
//                    ds.dummyEndKoorX[o] = (int) ds.nodeX[p];
//                }
//            }
//        }
//        //I denna loop sparas alla y-koordinater för slut-noderna
//        for (int o = 0; o < ds.dummyArcEnd.length; o++) {
//
//            for (int p = 0; p < ds.nodeY.length; p++) {
//                if (ds.dummyArcEnd[o] == ds.nodeY[p]);
//                {
//                    ds.dummyEndKoorY[o] = (int) ds.nodeY[p];
//                }
//            }
//        }
        //Skriver ut dessa för att se om de är lika långa
        //Obs! Dessa kommer ju att vara 1000 om vi inte fixar det...
//        System.out.println(ds.dummyStartKoorX.length);
//        System.out.println(ds.dummyStartKoorY.length);
//        System.out.println(ds.dummyEndKoorX.length);
//        System.out.println(ds.dummyEndKoorY.length);
        //Nu ska vi föröska räkna ut riktnignarna
//        for (int i = 0; i < ds.arcRoute.length; i++) {
//
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) < 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) < 0) {
//                ds.directionNextArc = "NE";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) == 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) < 0) {
//                ds.directionNextArc = "N";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) > 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) < 0) {
//                ds.directionNextArc = "NW";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) < 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) == 0) {
//                ds.directionNextArc = "E";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) > 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) == 0) {
//                ds.directionNextArc = "W";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) < 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) > 0) {
//                ds.directionNextArc = "SE";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) == 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) > 0) {
//                ds.directionNextArc = "S";
//            }
//            if ((ds.dummyStartKoorX[i] - ds.dummyEndKoorX[i]) > 0 && (ds.dummyStartKoorY[i] - ds.dummyEndKoorY[i]) > 0) {
//                ds.directionNextArc = "SW";
//            }
//        }
        //Här bestäms körinstruktionerna
        int i = 0;
        if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength && ds.arcCost[ds.arcRoute[1]] == ds.korsningLength && ds.arcCost[ds.arcRoute[2]] == ds.korsningLength) {
            instructions += "H\n"; //u-sväng
            i = 3;
        } else if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength && ds.arcCost[ds.arcRoute[1]] == ds.korsningLength) {
            instructions += "B\n"; //vänster
            i = 2;
        } else if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength) {
            i = 1;
        }
        while (ds.arcRoute[i] != 0) {
            //for (int i = 0; i < ds.arcRoute.length; i++) {
            if (ds.arcRoute[i + 1] != 0 && ds.arcCost[ds.arcRoute[i + 1] - 1] == ds.korsningLength) {
                //Rakt fram eller vänster
                if (ds.arcRoute[i + 2] != 0 && ds.arcCost[ds.arcRoute[i + 2] - 1] == ds.korsningLength && ds.arcCost[ds.arcRoute[i + 3] - 1] == ds.korsningLength) {
                    instructions += "H\n"; //u-sväng
                    i = i + 3;
                } else if (ds.arcRoute[i + 2] != 0 && ds.arcCost[ds.arcRoute[i + 2] - 1] == ds.korsningLength) { //i+2 kommer generara arcCost[-1] de två sista varven

                    instructions += "B\n"; //vänster
                    i = i + 2;
                } else {
                    instructions += "D\n"; //rakt fram
                    i++;
                }
            } else if (ds.arcRoute[i + 1] != 0 && ds.arcCost[ds.arcRoute[i + 1] - 1] != ds.korsningLength) {
                instructions += "F\n"; //höger
                //Här måste vi ha en if som kollar om det är högersväng eller snesväng (Nordväst, Nordost)
            }

//
//            if (ds.direction.equals("N")) {
//                if (ds.directionNextArc.equals("N")) {
//                    //Skicka till roboten: "Straight"
//
//                    System.out.println("NN Straight");
//                }
//                if (ds.directionNextArc.equals("S")) {
//                    //Skicka till roboten: "U-turn";
//                    System.out.println("NS U-turn");
//                }
//                if (ds.directionNextArc.equals("W")) {
//                    //Skicka till roboten: "Left";
//                    System.out.println("NW Left");
//                }
//                if (ds.directionNextArc.equals("E")) {
//                    //Skicka till roboten: "Right";
//                    System.out.println("NE Right");
//                }
//            }
//            if (ds.direction.equals("S")) {
//                if (ds.directionNextArc.equals("N")) {
//                    //Skicka till roboten: "U-turn";
//                    System.out.println("SN U-turn");
//                }
//                if (ds.directionNextArc.equals("S")) {
//                    //Skicka till roboten: "Straight";
//                    System.out.println("SS Straight");
//                }
//                if (ds.directionNextArc.equals("W")) {
//                    //Skicka till roboten: "Right";
//                    System.out.println("SW Right");
//                }
//                if (ds.directionNextArc.equals("E")) {
//                    //Skicka till roboten: "Left";
//                    System.out.println("SE Left");
//                }
//            }
//            if (ds.direction.equals("W")) {
//                if (ds.directionNextArc.equals("N")) {
//                    //Skicka till roboten: "Right";
//                    System.out.println("WN Right");
//                }
//                if (ds.directionNextArc.equals("S")) {
//                    //Skicka till roboten: "Left";
//                    System.out.println("WS Left");
//                }
//                if (ds.directionNextArc.equals("W")) {
//                    //Skicka till roboten: "Straight";
//                    System.out.println("WW Staright");
//                }
//                if (ds.directionNextArc.equals("E")) {
//                    //Skicka till roboten: "U-turn";
//                    System.out.println("WE U-turn");
//                }
//            }
//            if (ds.direction.equals("E")) {
//                if (ds.directionNextArc.equals("N")) {
//                    //Skicka till roboten: "Left";
//                    System.out.println("EN Left");
//                }
//                if (ds.directionNextArc.equals("S")) {
//                    //Skicka till roboten: "Right";
//                    System.out.println("ES Right");
//                }
//                if (ds.directionNextArc.equals("W")) {
//                    //Skicka till roboten: "U-turn";
//                    System.out.println("EW U-turn");
//                }
//                if (ds.directionNextArc.equals("E")) {
//                    //Skicka till roboten: "Straight";
//                    System.out.println("EE Straight");
//                }
//            }
//            System.out.println(ds.directionNextArc);
            i++;
        }
        instructions += "I\n";
        return instructions;

    }
}
