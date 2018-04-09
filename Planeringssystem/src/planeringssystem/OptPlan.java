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
        int dest_node = 3; // Här ska vi istället ta in platser från HTTP-server

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
    
    public void createInstructions() {
        //Gabriella testar lite, så denna borde kollas igenom en extra gång!
        
        //Två stycken for-loopar i en for-loop för att kunna ta fram dummyNodeStart och dummyNodeEnd
        for (int i = 0; i < ds.arcRoute.length; i++) {
        
        for (int j = 0; j < ds.arcStart.length; j++) {
            if (ds.arcRoute[i] == ds.arcStart[j]);{
            ds.dummyArcStart[i] = ds.arcStart[j];
        }
        }
            
        for (int k = 0; k < ds.arcEnd.length; k++) {
            if (ds.arcRoute[i] == ds.arcEnd[k]);{
            ds.dummyArcEnd[i] = ds.arcEnd[k];
        }
        }
        }
        //Skriver ut dessa för att se om de är lika långa
        System.out.println(ds.dummyArcStart.length);
        System.out.println(ds.dummyArcEnd.length);
        
        //Nu har jag nodernas nummer, nu måste jag spara koordinaterna
        
        for(int m = 0; m < ds.dummyArcStart.length; m++) {
            
            for (int n = 0; n < ds.nodeX.length; n++) {
            if (ds.dummyArcStart[m] == ds.nodeX[n]);{
            ds.dummyKoorX[m] = (int) ds.nodeX[n];
        }
        }
        }
            
        for (int o = 0; o < ds.dummyArcEnd.length; o++) {
            
            for (int p = 0; p < ds.nodeY.length; p++) {
            if (ds.dummyArcEnd[o] == ds.nodeX[p]);{
            ds.dummyKoorY[o] = (int) ds.nodeX[p];
        }
        }
        }
        
        //Skriver ut dessa för att se om de är lika långa
        System.out.println(ds.dummyKoorX.length);
        System.out.println(ds.dummyKoorY.length);
        
        //Nu ska jag föröska räkna ut riktnignarna
        
        for (int i = 0; i < ds.dummyArcStart.length; i++) {
        //Innan detta görs måste directionNextArc initieras
        
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) > 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) > 0){
            ds.directionNextArc = "OBS, OKLAR! (Northeast)";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) == 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) > 0){
            ds.directionNextArc = "N";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) < 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) > 0){
            ds.directionNextArc = "OBS, OKLAR! (Northwest)";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) > 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) == 0){
            ds.directionNextArc = "E";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) < 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) == 0){
            ds.directionNextArc = "W";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) > 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) < 0){
            ds.directionNextArc = "OBS, OKLAR! (Southeast)";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) == 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) < 0){
            ds.directionNextArc = "S";
        }
        if ((ds.dummyKoorX[i+1] - ds.dummyKoorX[i]) < 0 && (ds.dummyKoorY[i+1] - ds.dummyKoorY[i]) < 0){
            ds.directionNextArc = "OBS, OKLAR! (Southwest)";
        }
        }
        
        //Här jämförs direction med directionNextArc
        
        for (int i = 0; i < ds.arcRoute.length; i++){

        if (ds.direction.equals("N")){
            if (ds.directionNextArc.equals("N")){
            //Skicka till roboten: "Straight";
            }
            if (ds.directionNextArc.equals("S")){
            //Skicka till roboten: "U-turn";
            }
            if (ds.directionNextArc.equals("W")){
            //Skicka till roboten: "Left";
            }
            if (ds.directionNextArc.equals("E")){
            //Skicka till roboten: "Right";
            }
        }
        if (ds.direction.equals("S")){
            if (ds.directionNextArc.equals("N")){
            //Skicka till roboten: "U-turn";
            }
            if (ds.directionNextArc.equals("S")){
            //Skicka till roboten: "Straight";
            }
            if (ds.directionNextArc.equals("W")){
            //Skicka till roboten: "Right";
            }
            if (ds.directionNextArc.equals("E")){
            //Skicka till roboten: "Left";
            }
        }
        if (ds.direction.equals("W")){
            if (ds.directionNextArc.equals("N")){
            //Skicka till roboten: "Right";
            }
            if (ds.directionNextArc.equals("S")){
            //Skicka till roboten: "Left";
            }
            if (ds.directionNextArc.equals("W")){
            //Skicka till roboten: "Straight";
            }
            if (ds.directionNextArc.equals("E")){
            //Skicka till roboten: "U-turn";
            }
        }
        if (ds.direction.equals("E")){
            if (ds.directionNextArc.equals("N")){
            //Skicka till roboten: "Left";
            }
            if (ds.directionNextArc.equals("S")){
            //Skicka till roboten: "Right";
            }
            if (ds.directionNextArc.equals("W")){
            //Skicka till roboten: "U-turn";
            }
            if (ds.directionNextArc.equals("E")){
            //Skicka till roboten: "Straight";
            }
        }
    }
    }
}