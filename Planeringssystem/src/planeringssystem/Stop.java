package planeringssystem;

public class Stop {

    DataStore ds;
    HTTPanrop ha;

    public Stop(DataStore ds, HTTPanrop ha) {
        this.ds = ds;
        this.ha = ha;
    }

    public void pickup() {
        // - kallar på ta uppdrag: ha.messagetype(String plats, int id, int passagerare, int grupp)
        ha.messagetype(ds.closestPlats, Integer.parseInt(ds.uppdragsIDArray[0]), Integer.parseInt(ds.passengersArray[0]));//Ta första uppdraget
        
        // - minskar kapaciteten: ds.cap = ds.cap - (antal passagerare vi tar upp)
        // - påbörjar rutt till uppdragets avlämningsplats: uppdaterar dest_node och last_node samt 
        //kallar på op.createPlan och op.createInstructions
    }

    public void dropoff() {
    }
}
