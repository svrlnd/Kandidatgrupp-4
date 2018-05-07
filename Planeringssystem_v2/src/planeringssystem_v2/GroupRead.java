package planeringssystem_v2;

import java.util.LinkedList;

/**
 *
 * @author Simon
 */
public class GroupRead implements Runnable {

    private DataStore ds;
    private HTTPgrupp hg;
    private String[] dummyList;
    int[] tempos;
    String temp1;
    String temp2;
    String temp3;
    int temp4;
    String[] groupList;
    String[] groupDate;
    String[] groupID;
    String[] groupMessage;
    String[] groupMessageSplit;
    String[] groupPlats;
    String[] groupCost;
    String[] groupUppdrag;
    String[] groupUppdragSplit;
    LinkedList<String> uppdragGroup1;
    LinkedList<String> uppdragGroup4;
    LinkedList<String> uppdragGroup5;
    String msg;

    public GroupRead(DataStore ds) {
        this.ds = ds;
        dummyList = new String[ds.cap];
        tempos = new int[3];
        temp1 = "";
        temp2 = "";
        temp3 = "";
        temp4 = 0;
        groupList = new String[3];
        groupDate = new String[3];
        groupID = new String[3];
        groupMessage = new String[3];
        groupMessageSplit = new String[3];
        groupPlats = new String[3];
        groupCost = new String[3];
        groupUppdrag = new String[3];
        groupUppdragSplit = new String[3];
        uppdragGroup1 = new LinkedList<String>();
        uppdragGroup4 = new LinkedList<String>();
        uppdragGroup5 = new LinkedList<String>();
        hg = new HTTPgrupp();
        msg = "";

    }

    @Override
    public void run() {
        try {
            while (true) {
                //Dela upp meddelandet från HTTPgrupp
                for (int i = 0; i < 3; i++) {
                    groupList = hg.getmessage()[i].split(";");
                    tempos[i] = Integer.parseInt(groupList[1]);
                    groupDate[i] = groupList[0];
                    groupID[i] = groupList[1];
                    groupMessage[i] = groupList[2];
                }

                //Swoopa i rätt ordning för att veta att det alltid blir rätt
                for (int i = 0; i < 2; i++) {
                    if (tempos[i] > tempos[i + 1]) {
                        //Swoppa alla dom platserna i alla arrayer
                        //Swoppa groupDate
                        temp1 = groupDate[i];
                        groupDate[i] = groupDate[i + 1];
                        groupDate[i + 1] = temp1;
                        //Swoppa groupID
                        temp2 = groupID[i];
                        groupID[i] = groupID[i + 1];
                        groupID[i + 1] = temp2;
                        //Swoppa groupMessage
                        temp3 = groupMessage[i];
                        groupMessage[i] = groupMessage[i + 1];
                        groupMessage[i + 1] = temp3;
                        //Swoppa tempos
                        temp4 = tempos[i];
                        tempos[i] = tempos[i + 1];
                        tempos[i + 1] = temp4;
                        if (tempos[i] > tempos[i + 1]) {
                            //Swoppa groupDate
                            temp1 = groupDate[i];
                            groupDate[i] = groupDate[i + 1];
                            groupDate[i + 1] = temp1;
                            //Swoppa groupID
                            temp2 = groupID[i];
                            groupID[i] = groupID[i + 1];
                            groupID[i + 1] = temp2;
                            //Swoppa groupMessage
                            temp3 = groupMessage[i];
                            groupMessage[i] = groupMessage[i + 1];
                            groupMessage[i + 1] = temp3;
                            //Swoppa tempos
                            temp4 = tempos[i];
                            tempos[i] = tempos[i + 1];
                            tempos[i + 1] = temp4;
                        }
                        if (i > 0 && tempos[i - 1] > tempos[i]) {
                            //Swoppa groupDate
                            temp1 = groupDate[i - 1];
                            groupDate[i - 1] = groupDate[i];
                            groupDate[i] = temp1;
                            //Swoppa groupID
                            temp2 = groupID[i - 1];
                            groupID[i - 1] = groupID[i];
                            groupID[i] = temp2;
                            //Swoppa groupMessage
                            temp3 = groupMessage[i - 1];
                            groupMessage[i - 1] = groupMessage[i];
                            groupMessage[i] = temp3;
                            //Swoppa tempos
                            temp4 = tempos[i - 1];
                            tempos[i - 1] = tempos[i];
                            tempos[i] = temp4;
                        }
                    }
                }

                //Splitta meddelandet
                for (int i = 0; i < 3; i++) {
                    groupMessageSplit = groupMessage[i].split("!");
                    groupPlats[i] = groupMessageSplit[0];
                    groupCost[i] = groupMessageSplit[1];
                    groupUppdrag[i] = groupMessageSplit[2];
                }

                //Kolla vilka uppdrag respektive grupp ska göra
                for (int i = 0; i < 3; i++) {
                    dummyList = groupUppdrag[i].split(",");
                    if (i == 0) {
                        for (int j = 0; j < dummyList.length; j++) {
                            uppdragGroup1.add((dummyList[j]));
                        }
                    } //Ändra ordning här breoend epå ordning i HTTP
                    else if (i == 1) {
                        for (int j = 0; j < dummyList.length; j++) {
                            uppdragGroup4.add((dummyList[j]));
                        }
                    } else if (i == 2) {
                        for (int j = 0; j < dummyList.length; j++) {
                            uppdragGroup5.add((dummyList[j]));
                        }
                    }
                }

                System.out.println("Grupp 1 Uppdrag" + uppdragGroup1);
                System.out.println("Grupp 4 Uppdrag" + uppdragGroup4);
                System.out.println("Grupp 5 Uppdrag" + uppdragGroup5);
                
                msg = ds.valdPlats + "!" + ds.distanceCP + "!" + ds.uppdrag;
                
                hg.putmessage(msg);
                
                Thread.sleep(1000);
            }

        } catch (Exception e) {

        }
    }
}
