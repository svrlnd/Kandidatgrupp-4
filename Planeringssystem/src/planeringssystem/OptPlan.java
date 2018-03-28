package planeringssystem;

import java.util.*;

public class OptPlan {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;

    public OptPlan(DataStore ds) {
        this.ds = ds;
    }

    public void createPlan() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        //Här hade vi scanner för att läsa in var vi ville börja och sluta
        //Men det tog jag väck, för det ska vi ju läsa av hos AGV sen /S
        int start_node = 70; // Här ska vi istället ta AGVs nuvarande position, från BT-klassen
        int dest_node = 24; // Här ska vi istället ta in platser från HTTP-server

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
                    System.out.println("Arc: " + j);
                    ds.arcColor[j] = 1;
                    for (int k = 0; k < path.size() - 1; k++) {
                        ds.arcRoute[k] = j;
                    }
                }
            }
        }

        // Förklara rutt för robot, dvs meddela vilken som är nästa båge. 
        start_arc = ds.arcRoute[0];
        dest_arc = ds.arcRoute[ds.arcRoute.length - 1];// minus ett ty plats 4 (array börjar på 0)
        
        // Detta bör hända när AGV:n läser in en QR-kod som säger i vilken korsning den är
        for (int i = 0; i < ds.arcRoute.length; i++) {
            if (ds.currentDummyArc == ds.arcRoute[i]) { // && roboten är i en korsning
                ds.nextDummyArc = ds.arcRoute[i + 1];
            }
        }
    }
}
