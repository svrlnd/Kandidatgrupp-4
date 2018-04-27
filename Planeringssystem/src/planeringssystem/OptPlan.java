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
        start_node = 10; // Här ska vi istället ta AGVs nuvarande position, från BT-klassen
        dest_node = 7;// Här ska vi istället ta in platser från HTTP-server

        int start_arc = 40; //här vill vi läsa in vilket nod vi är på?
        int dest_arc = 60;

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
                    ds.arcRoute.add(j);
                }
            }
        }
 
//        // Förklara rutt för robot, dvs meddela vilken som är nästa båge. (Använder vi ens dessa?)
//        start_arc = ds.arcRoute[0];
//        dest_arc = ds.arcRoute[ds.arcRoute.length - 1];// minus ett ty plats 4 (array börjar på 0)
//
//        // Detta bör hända när AGV:n läser in en QR-kod som säger i vilken korsning den är
//        for (int i = 0; i < ds.arcRoute.length; i++) {
//            if (ds.currentDummyArc == ds.arcRoute[i]) { // && roboten är i en korsning
//                ds.nextDummyArc = ds.arcRoute[i + 1]; }
        int a = 3;
        return a;
    }

    public void createInstructions() {
        //String instructions = "";

        //Loopar igenom alla länkar i arcRoute för att ta reda på startnoden och slutnoden för dessa länkar
        for (int i = 0; i < ds.arcRoute.size(); i++) {

            for (int j = 0; j < ds.arcStart.length; j++) {
                if (ds.arcRoute.get(i) == j) {
                    //Här läggs start-noderna i arcRoute in i dummyArcStart
                    ds.dummyArcStart.add(ds.arcStart[j] - 1);
                }
            }
            for (int k = 0; k < ds.arcEnd.length; k++) {
                if (ds.arcRoute.get(i) == k) {
                    //Här läggs slut-noderna i arcRoute in i dummyArcEnd
                    ds.dummyArcEnd.add(ds.arcEnd[k] - 1);
                }
            }
        }

        //Kontrollerar att dummtArcStart och dummyArcEnd
        System.out.println("arcRoute" + ds.arcRoute);
        System.out.println("dummayarcStart" + ds.dummyArcStart);
        System.out.println("dummayarcEnd" + ds.dummyArcEnd);

        //Nu har vi nodernas nummer, nu måste vi spara dessa koordinaterna
        //I denna loop sparas alla x-koordinater för start-noderna
        for (int i = 0; i < ds.dummyArcStart.size(); i++) {
            for (int j = 0; j < ds.nodeNumber.length; j++) {
                if (ds.dummyArcStart.get(i) == (int) ds.nodeNumber[j]-1) {
                    ds.dummyStartKoorX.add((int) ds.nodeX[j]);
                }
            }
        }
        //I denna loop sparas alla y-koordinater för start-noderna
        for (int i = 0; i < ds.dummyArcStart.size(); i++) {

            for (int j = 0; j < ds.nodeY.length; j++) {
                if (ds.dummyArcStart.get(i) == (int) ds.nodeNumber[j]-1) {
                    ds.dummyStartKoorY.add((int) ds.nodeY[j]);
                }
            }
        }
        //I denna loop sparas alla x-koordinater för slut-noderna
        for (int i = 0; i < ds.dummyArcEnd.size(); i++) {

            for (int j = 0; j < ds.nodeX.length; j++) {
                if (ds.dummyArcEnd.get(i) == (int) ds.nodeNumber[j]-1) {
                    ds.dummyEndKoorX.add((int) ds.nodeX[j]);
                }
            }
        }
        //I denna loop sparas alla y-koordinater för slut-noderna
        for (int i = 0; i < ds.dummyArcEnd.size(); i++) {

            for (int j = 0; j < ds.nodeY.length; j++) {
                if (ds.dummyArcEnd.get(i) == (int) ds.nodeNumber[j]-1) {
                    ds.dummyEndKoorY.add((int) ds.nodeY[j]);
                }
            }
        }

        //Skriver ut dessa för att se om de är lika långa
        System.out.println("dummyStartKoorx" + ds.dummyStartKoorX);
        System.out.println("dummyStartKoorY" + ds.dummyStartKoorY);
        System.out.println("dummyEndKoorx" + ds.dummyEndKoorX);
        System.out.println("dummyendKoory" + ds.dummyEndKoorY);

//Nu ska vi föröska räkna ut riktnignarna 
        for (int i = 0; i < ds.arcRoute.size() - 1; i++) {

            ds.directionNextArc = "";

            if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) < 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) < 0) {
                ds.directionNextArc = "NE";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) == 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) < 0) {
                ds.directionNextArc = "N";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) > 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) < 0) {
                ds.directionNextArc = "NW";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) < 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) == 0) {
                ds.directionNextArc = "E";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) > 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) == 0) {
                ds.directionNextArc = "W";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) < 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) > 0) {
                ds.directionNextArc = "SE";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) == 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) > 0) {
                ds.directionNextArc = "S";

            } else if ((ds.dummyStartKoorX.get(i + 1) - ds.dummyEndKoorX.get(i + 1)) > 0 && (ds.dummyStartKoorY.get(i + 1) - ds.dummyEndKoorY.get(i + 1)) > 0) {
                ds.directionNextArc = "SW";

            }

            //"Räkna ut" hur den ska svänga
            if (ds.direction.equals("N")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Rakt"
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                }
            } else if (ds.direction.equals("NE")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                }
            } else if (ds.direction.equals("E")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B tttttt");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                }
            } else if (ds.direction.equals("SE")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                }
            } else if (ds.direction.equals("S")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                }
            } else if (ds.direction.equals("SW")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                }
            } else if (ds.direction.equals("W")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                }
            } else if (ds.direction.equals("NW")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                    System.out.println("E");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                    System.out.println("F");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                    System.out.println("G");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                    System.out.println("H");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                    System.out.println("A");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Vänster"
                    ds.instructions.add("B");
                    System.out.println("B");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Svagt vänster ";
                    ds.instructions.add("C");
                    System.out.println("C");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                    System.out.println("D");
                }

            }

            ds.direction = ds.directionNextArc;
            //Detta funkar nu! 
            //Det vi behöver fixa nu är att den tar en länk i taget men AGV vill ha två meddelanden i samma, det får vi försöka lösa...
        }
        System.out.println("Array: " + ds.instructions);
        System.out.println("Size: " + ds.instructions.size());
        
        int counter = 0;

        for (int i = 0; i < ds.instructions.size() - 2 + counter; i++) {
            System.out.println("hejhej");

            // Hanterar u-svängar (passerar tre korsningslänkar)
            if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "B")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.add(i, "H");
            } 
            // Hanterar stark vänster (passerar två korsningslänkar) 
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "C")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                counter += 1;
            } 
            // Hanterar vänster (passerar två korsningslänkar)           
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "D")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i + 1);
            } 
            // Hanterar svag vänster (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "C")) {
                ds.instructions.remove(i);
            } 
            // Hanterar svag vänster ut från send sväng (passerar två korsningslänkar)           
            else if ((ds.instructions.get(i) == "E") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "D")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.add(i, "C");
            } 
            
            // Hanterar svag höger ut ur sned väg (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "E") && (ds.instructions.get(i + 1) == "D")) {
                ds.instructions.remove(i+1);
            } 

            //Hanterar rakt fram (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "D")) {
                System.out.println("kosikti");
                ds.instructions.remove(i);
//                if (ds.instructions.get(i) == "D" && ds.instructions.getLast() != "D") {
//                    ds.instructions.remove(i);
//                }
            }
        }
        
        ds.instructions.add("I");
        System.out.println("Update: " + ds.instructions);
        //instructions += "I\n";
        //return instructions;
    }
}

//instructions += "I\n";
//return instructions;
//Här bestäms körinstruktionerna
//        int i = 0;
//        if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength && ds.arcCost[ds.arcRoute[1]] == ds.korsningLength && ds.arcCost[ds.arcRoute[2]] == ds.korsningLength) {
//            instructions += "H\n"; //u-sväng
//            i = 3;
//        } else if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength && ds.arcCost[ds.arcRoute[1]] == ds.korsningLength) {
//            instructions += "B\n"; //vänster
//            i = 2;
//        } else if (ds.arcCost[ds.arcRoute[0]] == ds.korsningLength) {
//            i = 1;
//        }
//        while (ds.arcRoute[i] != 0) {
//            //for (int i = 0; i < ds.arcRoute.length; i++) {
//            if (ds.arcRoute[i + 1] != 0 && ds.arcCost[ds.arcRoute[i + 1] - 1] == ds.korsningLength) {
//                //Rakt fram eller vänster
//                if (ds.arcRoute[i + 2] != 0 && ds.arcCost[ds.arcRoute[i + 2] - 1] == ds.korsningLength && ds.arcCost[ds.arcRoute[i + 3] - 1] == ds.korsningLength) {
//                    instructions += "H\n"; //u-sväng
//                    i = i + 3;
//                } else if (ds.arcRoute[i + 2] != 0 && ds.arcCost[ds.arcRoute[i + 2] - 1] == ds.korsningLength) { //i+2 kommer generara arcCost[-1] de två sista varven
//
//                    instructions += "B\n"; //vänster
//                    i = i + 2;
//                } else {
//                    instructions += "D\n"; //rakt fram
//                    i++;
//                }
//            } else if (ds.arcRoute[i + 1] != 0 && ds.arcCost[ds.arcRoute[i + 1] - 1] != ds.korsningLength) {
//                instructions += "F\n"; //höger
//                //Här måste vi ha en if som kollar om det är högersväng eller snesväng (Nordväst, Nordost)
//            }
//          i++;
//  }
