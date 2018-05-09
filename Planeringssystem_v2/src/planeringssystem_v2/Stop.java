package planeringssystem_v2;

public class Stop {

    DataStore ds;
    HTTPanrop ha;
    OptPlan op;
    String bevNek;

    public Stop(DataStore ds, HTTPanrop ha, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
    }

    public void pickup() { // HÄR KANKSE VI BORDE KALLA PÅ SIMONS GREJ SOM KOLLAR SAMÅKNING OCKSÅ?
        // - kallar på tauppdrag: ha.messagetype(String plats, int id, int passagerare, int grupp) 
        bevNek = ha.messagetype(ds.valdPlats, Integer.parseInt(ds.uppdragsIDArray[0]), Integer.parseInt(ds.passengersArray[0]));//Ta första uppdraget

        if (bevNek.equals("beviljas")) {
            //Vi har tagit ett uppdrag och kan åka och lämna kunderna, dvs det är tillåtet att starta nästa 
            // - påbörja rutt till uppdragets avlämningsplats: uppdaterar dest_node och last_node samt 
            //kallar på op.createPlan och op.createInstructions
            
            ds.first_node = ds.dummyArcStart.getLast();
            ds.a = ds.dummyArcEnd.getLast();
            ds.dest_node = Integer.parseInt(ds.destinationUppdragStart[Integer.parseInt(ds.uppdrag.get(0)) - 1]);
            ds.last_node = Integer.parseInt(ds.destinationUppdragSlut[Integer.parseInt(ds.uppdrag.get(0)) - 1]);
                    
            op.createPlan(ds.a, ds.dest_node);
            op.createInstructions();
            // - minskar kapaciteten: ds.cap = ds.cap - (antal passagerare vi tar upp)
            ds.cap = ds.cap - ds.currentPassengers1 + ds.currentPassengers2;
            ds.antal_passagerare = (char) (ds.currentPassengers1 + ds.currentPassengers2);

        } else {// uppdraget var redan taget och vi får ta ett nytt, antingen nästa uppdrag i listan eller hitta en ny plats. 
            //Listauppdrag på platsen
            System.out.println("Nu är vi i elsen i pickup som kallas från robotread");

        }
    }

    public void dropoff() {
        // - ökar kapaciteten

        if (ds.currentPassengers1 == 0) {
            ds.cap += ds.currentPassengers2;
            ds.currentPassengers2 = 0;

        } else {
            ds.cap += ds.currentPassengers1;
            ds.currentPassengers1 = 0;

        }
        // - påbörja rutt till närmsta upphämtningsplats

    }
}
