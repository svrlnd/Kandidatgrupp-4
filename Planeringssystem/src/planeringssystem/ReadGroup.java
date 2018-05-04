package planeringssystem;

import java.util.Arrays;
import java.util.LinkedList;

public class ReadGroup {

    private DataStore ds;
    private HTTPgrupp hg;
    private String[] dummyList;

    public ReadGroup(DataStore ds, HTTPgrupp hg) {
        this.ds = ds;
        this.hg = hg;
        dummyList = new String[ds.cap];

    }

    public String Read() {

        //OBS!! Detta beror på vilken ordning som meddelandena kommer till hemsidan. 
        //Just nu ligger de i ordning Grupp1, Grupp5 och Grupp4 men vi tycker kanske att det borde vara Grupp1, Grupp4 och Grupp5
        
        for (int i = 0; i < 3; i++) {
            ds.groupList = hg.getmessage()[i].split(";");
            ds.groupDate[i] = ds.groupList[0];
            ds.groupID[i] = ds.groupList[1];
            ds.groupMessage[i] = ds.groupList[2];
        }

        for (int i = 0; i < 3; i++) {
            ds.groupMessageSplit = ds.groupMessage[i].split("!");
            ds.groupPlats[i] = ds.groupMessageSplit[0];
            ds.groupCost[i] = ds.groupMessageSplit[1];
            ds.groupUppdrag[i] = ds.groupMessageSplit[2];
        }

        for (int i = 0; i < 3; i++) {
            dummyList = ds.groupUppdrag[i].split(",");
            if (i == 0) {
                for (int j = 0; j < dummyList.length; j++) {
                    ds.uppdragGroup1.add((dummyList[j]));
                }
            } 
            //Ändra ordning här breoend epå ordning i HTTP
            
            else if (i == 1) {
                for (int j = 0; j < dummyList.length; j++) {
                    ds.uppdragGroup5.add((dummyList[j]));
                }
            } else if (i == 2) {
                for (int j = 0; j < dummyList.length; j++) {
                    ds.uppdragGroup4.add((dummyList[j]));
                }
            }
        }
        
        //Nu vill vi kolla om de andra grupperna har samma upphämtningsplats för att veta vem som ska göra vilket uppdrag
        //Vet inte om dessa beräkningar ska göras här
        for (int i = 0; i < 2; i++) {
            if (ds.groupPlats[i] == ds.groupPlats[2]) {
                //Kolla hur det är med kostnaderna
                if (Integer.parseInt(ds.groupCost[i]) < Integer.parseInt(ds.groupCost[2])) { //Om en annan grupp har lägre kostnad
                    //Då vill vi kolla om det finns ett eller flera uppdrag på den plasten som vi kan ta istället
                    //Om det inte finns andra uppdrag att ta på den plasten så vill vi byta till den näst närmaste platsen 
                    //Borde det göras en ny beräkning då?
                } else if (Integer.parseInt(ds.groupCost[i]) == Integer.parseInt(ds.groupCost[2])) {
                    //Då vill vi jämföra grupp-ID
                    if (Integer.parseInt(ds.groupID[i]) < Integer.parseInt(ds.groupID[2])) {
                        //Då vill att den gruppen ska få det och då måste vi byta uppdrag 
                    }
                }
                // Else: Då vill vi inte göra något för då ska v ta det uppdraget.

            }
        }

        return "hej";
    }

}
