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
            ds.a = 66;
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

//        platsX[2] = 435;
//        platsY[2] = 16;
        ds.min = Integer.MAX_VALUE;
//        double temp = 0;
//        int next_start_node = 0;
//        int closest_postive_node = 345; //tillräckligt stort?
//        int closest_negative_node = 345;
//        double distance_positive = Double.MAX_VALUE;
//        double distance_negative = Double.MAX_VALUE;
//        //Leta fram den positiva och den negativa nod som är närmast varje upphämtningsplats
//        //DET HÄR FUNKAR INTE OM EN UPPHÄMTNINGSPLATS LIGGER PÅ EN SNED LÄNK??
//        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
//            if (Integer.parseInt(ha.messagetype(platser[i])[0]) == 0) { //Kollar om det finns uppdrag på platsen
//                System.out.println("Skippis");
//            } else {
//                for (int j = 0; j < ds.nodes; j++) {
//                    //Vilka noder har samma x-koordinat?
//                    if (platsX[2] == ds.nodeX[j]) { //2 borde vara i
//
//                        //Är avstånet till dess y-koordinat positivt eller negativt?
//                        if (ds.nodeY[j] - platsY[2] < 0) {//2 borde vara i
//                            temp = Math.abs(platsY[2] - ds.nodeY[j]);//2 borde vara i
//                            if (temp < distance_negative) {
//                                distance_negative = temp;
//                                closest_negative_node = j + 1;
//                            }
//                        } else {
//                            temp = Math.abs(platsY[i] - ds.nodeY[j]);
//                            if (temp < distance_positive) {
//                                distance_positive = temp;
//                                closest_postive_node = j + 1;
//                            }
//                        }
//                        //Om ingen nod hade samma x-koordinat, vilka noder har då samma y-koordinat?
//                    } else if (platsY[i] == ds.nodeY[j]) {
//                        //Är avstånet till dess x-koordinat positivt eller negativt?
//                        if (ds.nodeX[j] - platsX[i] < 0) {
//                            temp = Math.abs(platsX[i] - ds.nodeX[j]);
//                            if (temp < distance_negative) {
//                                distance_negative = temp;
//                                closest_negative_node = j + 1;
//                            }
//                        } else {
//                            temp = Math.abs(platsX[i] - ds.nodeX[j]);
//                            if (temp < distance_positive) {
//                                distance_positive = temp;
//                                closest_postive_node = j + 1;
//                            }
//                        }
//                    } else {System.out.println("Upphämtningsplatsen ligger på en sned länk! Vad gör vi nu?");}
//                }
//
//                System.out.println("Närmsta positiva nod är: " + closest_postive_node);
//                System.out.println("Närmsta negativa nod är: " + closest_negative_node);
        //Nu ska vi ta reda på vilken länk dessa noder tillhör. 
//                for (int a = 0; a < ds.arcs; a++) {
//                    if (ds.arcStart[a] == closest_postive_node && ds.arcEnd[a] == closest_negative_node
//                            || ds.arcEnd[a] == closest_postive_node && ds.arcStart[a] == closest_negative_node) {
//                        ds.dest_node = ds.arcEnd[a];
//                        ds.firstNode = ds.arcStart[a];
//                        tempis = op.createPlan(curNode, ds.dest_node);
//                        if (tempis < ds.min) { // Vilken plats är närmast? (just nu kollar vi från nod 17 men vi vill kolla från föregående avlämningsplats typ?)
//                            ds.min = ds.routeCost;
//                            ds.closestPlats = platser[i];
//                            System.out.println("Närmsta upphämtningsplats är " + ds.closestPlats);
//                        }
//                    }
//                }
//            }
//        }
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
