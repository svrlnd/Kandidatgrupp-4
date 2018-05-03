package planeringssystem;

import java.util.Arrays;

public class ReadGroup {

    private DataStore ds;
    private HTTPgrupp hg;

    public ReadGroup(DataStore ds, HTTPgrupp hg) {
        this.ds = ds;
        this.hg = hg;
    }

    public String Read() {

        //Det kanske är lite riskabelt att skriva 3 här
        ds.groupList = new String[3];
        ds.groupDate = new String[3];
        ds.groupID = new String[3];
        ds.groupMessage = new String[3];
        ds.groupMessageSplit = new String[3];
        ds.groupPlats = new String[3];
        ds.groupCost = new String[3];
        ds.groupUppdrag = new String[3];

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

        //Nu vill vi kolla om de andra grupperna har samma upphämtningsplats
        for (int i = 0; i < 2; i++) {
            if (ds.groupPlats[i] == ds.groupPlats[2]) {
                //Vad vill vi göra då?
            }
        }

        return "hej";
    }

}
