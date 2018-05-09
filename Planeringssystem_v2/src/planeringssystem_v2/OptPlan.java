package planeringssystem_v2;

import java.util.*;

public class OptPlan {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;
    private int cost;

    public OptPlan(DataStore ds) {
        this.ds = ds;
    }

    public int getCost(int start_node, int dest_node) {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        //cost = 0;

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

        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = 0; j < ds.arcs; j++) {
                if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                        && ds.arcEnd[j] == Integer.parseInt(path.get(i + 1).getId())) {
                    cost += ds.arcCost[j];
                }
            }
        }

        //ds.routeCost = cost; Har ej lagt in denna i nya DS än, behövs det?
        return cost;
    }

    public void createPlan(int start_node, int dest_node) {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

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

        //Hämta kortaste rutt
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = 0; j < ds.arcs; j++) {
                if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                        && ds.arcEnd[j] == Integer.parseInt(path.get(i + 1).getId())) {
                    //System.out.println("Arc: " + j); //Skriver ut de arcs som ingår i rutten
                    ds.arcColor[j] = 1;
                    ds.arcRoute.add(j);
                }
            }
        }

        //Här lägger vi till den FÖRSTA LÄNKEN i arcRoute för att det ska bli rätt med körinstruktionerna!
        //Lägg till första länken i arcroute genom att hitta länken för firstNode och start_Node.
        for (int i = 0; i < ds.arcs; i++) {
            if ((ds.first_node + 1) == ds.arcStart[i] && (start_node + 1) == ds.arcEnd[i]) {
                ds.arcRoute.addFirst(i);
                ds.arcColor[i] = 1;
            }
        }

        //Här lägger vi till den SISTA LÄNKEN i arcRoute för att det ska bli rätt med körinstruktionerna!
        //Lägg till sista länken i arcroute genom att hitta noderna för upphämtning eller avlämningen
        //Om: Upphämtning på en plats
        for (int i = 0; i < ds.arcs; i++) {
            if (ds.dest_node + 1 == ds.arcStart[i] && ds.last_node + 1 == ds.arcEnd[i]) {
                ds.arcRoute.addLast(i);
                ds.arcColor[i] = 1;

            }
        }

    }

    public void createInstructions() { //Ändra denna till public STRING createInstructions

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

        //Nu har vi nodernas nummer, nu måste vi spara dessa koordinaterna
        //I denna loop sparas alla x-koordinater för start-noderna
        for (int i = 0; i < ds.dummyArcStart.size(); i++) {
            for (int j = 0; j < ds.nodeNumber.length; j++) {
                if (ds.dummyArcStart.get(i) == (int) ds.nodeNumber[j] - 1) {
                    ds.dummyStartKoorX.add((int) ds.nodeX[j]);
                }
            }
        }
        //I denna loop sparas alla y-koordinater för start-noderna
        for (int i = 0; i < ds.dummyArcStart.size(); i++) {

            for (int j = 0; j < ds.nodeY.length; j++) {
                if (ds.dummyArcStart.get(i) == (int) ds.nodeNumber[j] - 1) {
                    ds.dummyStartKoorY.add((int) ds.nodeY[j]);
                }
            }
        }
        //I denna loop sparas alla x-koordinater för slut-noderna
        for (int i = 0; i < ds.dummyArcEnd.size(); i++) {

            for (int j = 0; j < ds.nodeX.length; j++) {
                if (ds.dummyArcEnd.get(i) == (int) ds.nodeNumber[j] - 1) {
                    ds.dummyEndKoorX.add((int) ds.nodeX[j]);
                }
            }
        }
        //I denna loop sparas alla y-koordinater för slut-noderna
        for (int i = 0; i < ds.dummyArcEnd.size(); i++) {

            for (int j = 0; j < ds.nodeY.length; j++) {
                if (ds.dummyArcEnd.get(i) == (int) ds.nodeNumber[j] - 1) {
                    ds.dummyEndKoorY.add((int) ds.nodeY[j]);
                }
            }
        }

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
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                }
            } else if (ds.direction.equals("NE")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                }
            } else if (ds.direction.equals("E")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                }
            } else if (ds.direction.equals("SE")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                }
            } else if (ds.direction.equals("S")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "starkt höger";
                    ds.instructions.add("G");
                }
            } else if (ds.direction.equals("SW")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                }
            } else if (ds.direction.equals("W")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Vänster";
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Svagt vänster";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                }
            } else if (ds.direction.equals("NW")) {
                if (ds.directionNextArc.equals("N")) {
                    //Skicka till roboten: "Svagt höger";
                    ds.instructions.add("E");
                } else if (ds.directionNextArc.equals("NE")) {
                    //Skicka till roboten: "Höger";
                    ds.instructions.add("F");
                } else if (ds.directionNextArc.equals("E")) {
                    //Skicka till roboten: "Starkt höger";
                    ds.instructions.add("G");
                } else if (ds.directionNextArc.equals("SE")) {
                    //Skicka till roboten: "U-sväng";
                    ds.instructions.add("H");
                } else if (ds.directionNextArc.equals("S")) {
                    //Skicka till roboten: "Starkt vänster";
                    ds.instructions.add("A");
                } else if (ds.directionNextArc.equals("SW")) {
                    //Skicka till roboten: "Vänster"
                    ds.instructions.add("B");
                } else if (ds.directionNextArc.equals("W")) {
                    //Skicka till roboten: "Svagt vänster ";
                    ds.instructions.add("C");
                } else if (ds.directionNextArc.equals("NW")) {
                    //Skicka till roboten: "Rakt fram";
                    ds.instructions.add("D");
                }
            }

            ds.direction = ds.directionNextArc;
        }

        System.out.println("Array: " + ds.instructions);

        int counter = 0;

        if (ds.instructions.getLast() == "D" && ds.instructions.size() > 3) {
            ds.instructions.removeLast();
        }
        
        System.out.println("instructions" + ds.instructions.size());
 
        for (int i = 0; i < ds.instructions.size() - 2 + counter; i++) {
            
            // Hanterar u-svängar (passerar tre korsningslänkar)
            if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "B")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                //ds.instructions.remove(i); Kankse nån if, prova med AGV först
                ds.instructions.add(i, "H");
            } // Hanterar stark vänster (passerar två korsningslänkar) 
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "C")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                counter += 1;
            } // Hanterar vänster (passerar två korsningslänkar)           
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "D")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i + 1);
            } // Hanterar svag vänster (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "C")) {
                ds.instructions.remove(i);
            } // Hanterar svag vänster ut från send sväng (passerar två korsningslänkar)           
            else if ((ds.instructions.get(i) == "E") && (ds.instructions.get(i + 1) == "B") && (ds.instructions.get(i + 2) == "D")) {
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.remove(i);
                ds.instructions.add(i, "C");
            } // Hanterar svag höger ut ur sned väg (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "E") && (ds.instructions.get(i + 1) == "D")) {
                ds.instructions.remove(i + 1);
            } //Hanterar rakt fram (passerar en korsningslänk)
            else if ((ds.instructions.get(i) == "D") && (ds.instructions.get(i + 1) == "D")) {
                ds.instructions.remove(i);
                if (ds.arcRoute.size() <= 7) {
                    if (ds.instructions.get(i) == "D" && ds.instructions.getLast() != "D") {
                        ds.instructions.remove(i);
                    }
             }
            }
        }

        ds.instructions.add("I");
        System.out.println("Update: " + ds.instructions);

    }
}
