package planeringssystem;

import java.util.*;


public class OptPlan {
    
    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;
    
    public OptPlan (DataStore ds){
        this.ds = ds;
    }
    
    public void createPlan(){
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        
        //Här hade vi scanner för att läsa in var vi ville börja och sluta
        //Men det tog jag väck, för det ska vi ju läsa av hos AGV sen /S
        
        int start_node = 63;
        int dest_node = 17;
        
        //Set up network
        for (int i = 0; i < ds.nodes; i++){
            Vertex location = new Vertex("" + (i+1), "Nod #" + (i+1));
            nodes.add(location);
        }
        for (int i = 0; i < ds.arcs; i++){
            Edge lane = new Edge ("" +  (i+1), nodes.get(ds.arcStart[i]-1),
            nodes.get(ds.arcEnd[i] - 1), ds.arcCost[i]-1); //Last argument is arccost
            edges.add(lane);
        }
        
        Graph graph = new Graph(nodes,edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        
        //Beräkna kortaste rutt
        dijkstra.execute(nodes.get(start_node));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(dest_node));
        
        //Hämta kortast rutt
        for (int i = 0; i < path.size()-1; i++){
            for (int j = 0; j < ds.arcs; j++){
                if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                        && ds.arcEnd[j] == Integer.parseInt(path.get(i+1).getId())){
                    System.out.println("Arc: " + j);
                    ds.arcColor[j] = 1;
                }
            }
        }  
    }
}
