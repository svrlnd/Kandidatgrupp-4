package planeringssystem_v2;

import java.util.Arrays;
import java.util.LinkedList;

public class Stop {

    DataStore ds;
    HTTPanrop ha;
    OptPlan op;
    ClosestPlats cp;
    String bevNek;
    String bevNek1;
    private LinkedList<Integer> tempDUStart;
    private LinkedList<Integer> tempDUSlut;
    private LinkedList<String> uppdrag;

    public Stop(DataStore ds, HTTPanrop ha, OptPlan op, ClosestPlats cp) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
        this.cp = cp;
        tempDUStart = new LinkedList<Integer>();
        tempDUSlut = new LinkedList<Integer>();
        uppdrag = new LinkedList<String>();
    }

    public void pickup() { // HÄR KANKSE VI BORDE KALLA PÅ SIMONS GREJ SOM KOLLAR SAMÅKNING OCKSÅ?
        // - kallar på tauppdrag: ha.messagetype(String plats, int id, int passagerare, int grupp) 
        //Spara destinationUppdragStart och destainationUppdragSlut
        System.out.println("duStart: " + Arrays.toString(ds.destinationUppdragStart));
        tempDUStart.addFirst(Integer.parseInt(ds.destinationUppdragStart[0])-1);
        tempDUSlut.addFirst(Integer.parseInt(ds.destinationUppdragSlut[0])-1);
                        System.out.println("PICKUP");

        if (ds.s != -1) {
            tempDUStart.add(1, Integer.parseInt(ds.destinationUppdragStart[ds.s])-1);
            tempDUSlut.add(1, Integer.parseInt(ds.destinationUppdragSlut[ds.s])-1);
        }
        
        uppdrag.clear();
        uppdrag.addFirst(ds.uppdrag.getFirst());
        if (ds.s != -1) {
            uppdrag.add(1, ds.uppdrag.get(1));
        }
        
//        for (int i = 0; i < ds.uppdrag.size(); i++) {
//            uppdrag.add(ds.uppdrag.get(i));
//                }

        System.out.println("hej uppdeag " + uppdrag + "UPPDRAG.size: " + uppdrag.size());
        System.out.println("hej ds.passengersarray " + Arrays.toString(ds.passengersArray) + "ds.passengersArray.size: " + ds.passengersArray.length);
        bevNek = ha.messagetype(ds.valdPlats, Integer.parseInt(uppdrag.get(0)), Integer.parseInt(ds.passengersArray[0]));//Ta första uppdraget
        if (ds.s != -1) {
            bevNek1 = ha.messagetype(ds.valdPlats, Integer.parseInt(uppdrag.get(ds.s)), Integer.parseInt(ds.passengersArray[ds.s]));
        }
        ds.distanceDO = 0;
        
        System.out.println("PICKUP 1");
        System.out.println("BEVNEK: " + bevNek + "bevneK: " + bevNek1);
        System.out.println("PICKUP 2");
        
        if ((bevNek.equals("beviljas\n") && ds.s == -1) || (bevNek.equals("beviljas\n") && bevNek1.equals("beviljas\n"))) {
            
            System.out.println("riktning" + ds.direction);
            //Vi har tagit ett uppdrag och kan åka och lämna kunderna, dvs det är tillåtet att starta nästa 
            // - påbörja rutt till uppdragets avlämningsplats: uppdaterar dest_node och last_node samt 
            //kallar på op.createPlan och op.createInstructions

//            ds.first_node = ds.dummyArcStart.getLast();
//            ds.a = ds.dummyArcEnd.getLast();
//            ds.dest_node = Integer.parseInt(ds.destinationUppdragStart[0]);
//            ds.last_node = Integer.parseInt(ds.destinationUppdragSlut[0]);
//
//            op.createPlan(ds.a, ds.dest_node);
//            op.createInstructions();
            // - minskar kapaciteten: ds.cap = ds.cap - (antal passagerare vi tar upp)
            ds.cap = ds.cap - ds.currentPassengers1 + ds.currentPassengers2;
            ds.antal_passagerare = (char) (ds.currentPassengers1 + ds.currentPassengers2);
            
            System.out.println("ds.uppdeag " + uppdrag + "ds.UPPDRAG.size: " + uppdrag.size());
            
            if (uppdrag.size() == 1) { //1. Åk till avlämningsplats, 2. Åk till upphämtningsplats
                ds.first_node = ds.dest_node;
                ds.a = ds.last_node;
                ds.dest_node = tempDUStart.get(0);
                ds.last_node = tempDUSlut.get(0);
                op.createPlan(ds.a, ds.dest_node);
                op.createInstructions();
                ds.distanceDO = op.getCost(ds.a, ds.dest_node);
                ds.distanceDO += op.getCost(tempDUStart.get(0), tempDUSlut.get(0));
                ds.a = tempDUSlut.get(0);
                cp.getClosestPlats();

                System.out.println("PICKUP IF 1");

            } else if (uppdrag.size() == 2) { //1. Åk till avlämningsplats, 2. Åk till avlämningsplats, 3. Åk till upphämtningplats
                System.out.println("ds.a: "+ ds.a);
                System.out.println("tempdustart: "+tempDUStart.get(0));
                System.out.println("arcRoute" + ds.arcRoute);
                ds.first_node = ds.dest_node;
                ds.a = ds.last_node;
                ds.dest_node = tempDUStart.get(0);
                ds.last_node = tempDUSlut.get(0);
                op.createPlan(ds.a, ds.dest_node);
                op.createInstructions();
                ds.distanceDO = op.getCost(ds.a, tempDUStart.get(0));
                ds.distanceDO += op.getCost(tempDUStart.get(0), tempDUSlut.get(0));
                ds.distanceDO += op.getCost(tempDUSlut.get(0), tempDUStart.get(1));
                ds.distanceDO += op.getCost(tempDUStart.get(1), tempDUSlut.get(1));
                ds.a = tempDUSlut.get(1);
                
                cp.getClosestPlats();
            }

        } else {// uppdraget var redan taget och vi får ta ett nytt, antingen nästa uppdrag i listan eller hitta en ny plats. 
            //Listauppdrag på platsen
            System.out.println("Nu är vi i elsen i pickup som kallas från robotread");

        }

    }

    public void dropoff() {

        ds.distanceDO = 0;

        // - ökar kapaciteten
        if (ds.currentPassengers1 == 0) {
            ds.cap += ds.currentPassengers2;
            ds.currentPassengers2 = 0;

        } else {
            ds.cap += ds.currentPassengers1;
            ds.currentPassengers1 = 0;

        }
        // - påbörja rutt till närmsta upphämtningsplats eller avlämningsplats
        if (ds.cap == ds.initial_cap) { //Åk till upphämtningsplats

            if (ds.s == -1) {
                ds.first_node = tempDUStart.get(0);
                ds.a = tempDUSlut.get(0);
                cp.getClosestPlats();
                op.createPlan(ds.a, ds.dest_node);
                op.createInstructions();

                
            } else {
                ds.first_node = tempDUStart.get(1);
                ds.a = tempDUSlut.get(1);
                cp.getClosestPlats();
                op.createPlan(ds.a, ds.dest_node);
                op.createInstructions();
            }

        } else { //Åk till avlämningsplats sen upphämtningsplats (de samåkte, så vi skulle lämna av två uppdrag)

            ds.first_node = tempDUStart.get(0);
            ds.last_node = tempDUSlut.get(1);
            op.createPlan(tempDUSlut.get(0), tempDUStart.get(1));
            op.createInstructions();
            ds.distanceDO = op.getCost(tempDUSlut.get(0), tempDUStart.get(1));
            ds.distanceDO += op.getCost(tempDUStart.get(1), tempDUSlut.get(1));
            ds.a = tempDUSlut.get(1);
            cp.getClosestPlats();
        }
    }
}
