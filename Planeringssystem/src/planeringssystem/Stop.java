package planeringssystem;

public class Stop {

    DataStore ds;
    HTTPanrop ha;
    OptPlan op;

    public Stop(DataStore ds, HTTPanrop ha, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
    }

    public void pickup() { // HÄR KANKSE VI BORDE KALLA PÅ SIMONS GREJ SOM KOLLAR SAMÅKNING OCKSÅ?
        // - kallar på tauppdrag: ha.messagetype(String plats, int id, int passagerare, int grupp) 
        ha.messagetype(ds.closestPlats, Integer.parseInt(ds.uppdragsIDArray[0]), Integer.parseInt(ds.passengersArray[0]));//Ta första uppdraget

        // - minskar kapaciteten: ds.cap = ds.cap - (antal passagerare vi tar upp)
        ds.cap = ds.cap - (Integer.parseInt(ds.passengersArray[0]) /* + de som vill samåka*/);

        // - påbörjar rutt till uppdragets avlämningsplats: uppdaterar dest_node och last_node samt 
        //kallar på op.createPlan och op.createInstructions
        op.createPlan(Integer.parseInt(ds.destinationUppdragStart[0]), Integer.parseInt(ds.destinationUppdragSlut[0]));
        op.createInstructions();
    }

    public void dropoff() { 
        // - ökar kapaciteten
        ds.cap = ds.cap /* + de som ska hoppa av just här */ ;
        
        // - påbörjar rutt till närmsta upphämtningsplats
        

    }
}
