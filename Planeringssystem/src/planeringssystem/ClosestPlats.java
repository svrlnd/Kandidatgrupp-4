package planeringssystem;

import java.util.Arrays;

public class ClosestPlats {

    private DataStore ds;
    private HTTPanrop ha;
    private OptPlan op;
    int tempis;

    public ClosestPlats(DataStore ds, HTTPanrop ha, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
    }

    public String getClosestPlats() {

        if (ds.counterFirstInstructions == 0) {
            ds.a = 40;
            //Om plats: platsens första nod
            //Om uppdrag: uppdragets första nod
            ds.counterFirstInstructions = ds.counterFirstInstructions + 1;

        } else {
            ds.a = ds.dummyArcEnd.getLast();
        }

        ds.platsLista = new String[Integer.parseInt(ha.messagetype()[0])];
        ds.startSlutNoder = new String[Integer.parseInt(ha.messagetype()[0])];
        ds.platser = new String[Integer.parseInt(ha.messagetype()[0])];
        ds.noder = new String[Integer.parseInt(ha.messagetype()[0])];
        ds.startnod = new int[Integer.parseInt(ha.messagetype()[0])];
        ds.slutnod = new int[Integer.parseInt(ha.messagetype()[0])];

        //lägg alla upphämtningsplatser i "platser", samt deras NODER (start, slut) som alltså representerar en länk.
        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
            ds.platsLista = ha.messagetype()[i + 1].split(";");
            ds.platser[i] = ds.platsLista[0];
            ds.noder[i] = ds.platsLista[1];
        }
        System.out.println(Arrays.toString(ds.platser));
        //Dela upp noderna i varsin array ("startnod" och "slutnod").
        for (int j = 0; j < Integer.parseInt(ha.messagetype()[0]); j++) {
            ds.startSlutNoder = ds.noder[j].split(",");
            ds.startnod[j] = Integer.parseInt(ds.startSlutNoder[0]) - 1;
            ds.slutnod[j] = Integer.parseInt(ds.startSlutNoder[1]) - 1;
        }

        System.out.println(Arrays.toString(ds.startnod));
        System.out.println(Arrays.toString(ds.slutnod));

        ds.min = Integer.MAX_VALUE;

        for (int i = 0; i < ds.platser.length; i++) {
            System.out.println("Curnode: " + ds.a + " Startnod: " + ds.startnod[i]);
            tempis = op.getCost(ds.a, ds.startnod[i]); //HÄR ÄR NÅGOT KNAS, FÅR TA EN KIK PÅ DET NÄSTA GÅNG
            System.out.println("Tempis är " + tempis);
            if (tempis < ds.min) { // Vilken plats är närmast? (just nu kollar vi från nod 17 men vi vill kolla från föregående avlämningsplats typ?)
                ds.min = tempis;
                ds.closestPlats = ds.platser[i];
                System.out.println("Närmsta upphämtningsplats är " + ds.closestPlats);
                ds.dest_node = ds.startnod[i];
                ds.lastNode = ds.slutnod[i];
            }   
        }
        
        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats (dest_node) som har uppdrag med op.createPlan
        return ds.closestPlats;
    }
}
