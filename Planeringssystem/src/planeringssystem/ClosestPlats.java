package planeringssystem;

public class ClosestPlats {

    private DataStore ds;
    private HTTPanrop ha;
    private OptPlan op;
    String[] platsLista;
    String[] platsXY;
    String[] platser;
    String[] koordinater;
    int[] platsX;
    int[] platsY;
    int tempis;

    public ClosestPlats(DataStore ds, HTTPanrop ha, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.op = op;
    }

    public String getClosestPlats(int curNode) {
                
        platsLista = new String[Integer.parseInt(ha.messagetype()[0])];
        platsXY = new String[Integer.parseInt(ha.messagetype()[0])];
        platser = new String[Integer.parseInt(ha.messagetype()[0])];
        koordinater = new String[Integer.parseInt(ha.messagetype()[0])];
        platsX = new int[Integer.parseInt(ha.messagetype()[0]) + 1];
        platsY = new int[Integer.parseInt(ha.messagetype()[0]) + 1];

        //lägg alla upphämtningsplatser i "platser", samt deras koordinater (x,y) i "koordinater".
        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
            platsLista = ha.messagetype()[i + 1].split(";");
            platser[i] = platsLista[0];
            koordinater[i] = platsLista[1];
        }

        //Dela upp koordinaterna i varsin array ("platsX" och "platsY") för X- resp. Y-koordinaterna.
        for (int j = 0; j < Integer.parseInt(ha.messagetype()[0]); j++) {
            platsXY = koordinater[j].split(",");
            platsX[j] = Integer.parseInt(platsXY[0]);
            platsY[j] = Integer.parseInt(platsXY[1]);
        }
//        platsX[2] = 435;
//        platsY[2] = 16;

        ds.min = Integer.MAX_VALUE;
        double temp = 0;
        int next_start_node = 0;
        int closest_postive_node = 345; //tillräckligt stort?
        int closest_negative_node = 345;
        double distance_positive = Double.MAX_VALUE;
        double distance_negative = Double.MAX_VALUE;
        //Leta fram den positiva och den negativa nod som är närmast varje upphämtningsplats
        //DET HÄR FUNKAR INTE OM EN UPPHÄMTNINGSPLATS LIGGER PÅ EN SNED LÄNK??
        for (int i = 0; i < Integer.parseInt(ha.messagetype()[0]); i++) {
            if (Integer.parseInt(ha.messagetype(platser[i])[0]) == 0) { //Kollar om det finns uppdrag på platsen
                System.out.println("Skippis");
            } else {
                for (int j = 0; j < ds.nodes; j++) {
                    //Vilka noder har samma x-koordinat?
                    if (platsX[2] == ds.nodeX[j]) { //2 borde vara i

                        //Är avstånet till dess y-koordinat positivt eller negativt?
                        if (ds.nodeY[j] - platsY[2] < 0) {//2 borde vara i
                            temp = Math.abs(platsY[2] - ds.nodeY[j]);//2 borde vara i
                            if (temp < distance_negative) {
                                distance_negative = temp;
                                closest_negative_node = j + 1;
                            }
                        } else {
                            temp = Math.abs(platsY[i] - ds.nodeY[j]);
                            if (temp < distance_positive) {
                                distance_positive = temp;
                                closest_postive_node = j + 1;
                            }
                        }
                        //Om ingen nod hade samma x-koordinat, vilka noder har då samma y-koordinat?
                    } else if (platsY[i] == ds.nodeY[j]) {
                        //Är avstånet till dess x-koordinat positivt eller negativt?
                        if (ds.nodeX[j] - platsX[i] < 0) {
                            temp = Math.abs(platsX[i] - ds.nodeX[j]);
                            if (temp < distance_negative) {
                                distance_negative = temp;
                                closest_negative_node = j + 1;
                            }
                        } else {
                            temp = Math.abs(platsX[i] - ds.nodeX[j]);
                            if (temp < distance_positive) {
                                distance_positive = temp;
                                closest_postive_node = j + 1;
                            }
                        }
                    } else {System.out.println("Upphämtningsplatsen ligger på en sned länk! Vad gör vi nu?");}
                }

                System.out.println("Närmsta positiva nod är: " + closest_postive_node);
                System.out.println("Närmsta negativa nod är: " + closest_negative_node);
                //Nu ska vi ta reda på vilken länk dessa noder tillhör. 
                for (int a = 0; a < ds.arcs; a++) {
                    if (ds.arcStart[a] == closest_postive_node && ds.arcEnd[a] == closest_negative_node
                            || ds.arcEnd[a] == closest_postive_node && ds.arcStart[a] == closest_negative_node) {
                        ds.dest_node = ds.arcEnd[a];
                        ds.firstNode = ds.arcStart[a];
                        tempis = op.createPlan(17, ds.dest_node);
                        if (tempis < ds.min) { // Vilken plats är närmast? (just nu kollar vi från nod 17 men vi vill kolla från föregående avlämningsplats typ?)
                            ds.min = ds.routeCost;
                            ds.closestPlats = platser[i];
                            System.out.println("Närmsta upphämtningsplats är " + ds.closestPlats);
                        }
                    }
                }
            }
        }

        
        //Mät avstånd från startnod (AGVns position) till varje upphämtningsplats (dest_node) som har uppdrag med op.createPlan
        
        
        return ds.closestPlats;
    }
}
