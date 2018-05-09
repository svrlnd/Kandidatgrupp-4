package planeringssystem_v2;

import static java.lang.Integer.toString;
import java.util.Arrays;
import java.util.LinkedList;

public class GroupRead implements Runnable {

    private DataStore ds;
    private GUI gui;
    private HTTPgrupp hg;
    private String[] dummyList;
    int[] tempos;
    String temp1;
    String temp2;
    String temp3;
    int temp4;
    LinkedList<Integer> tempis;
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
    LinkedList<Integer> uppdragViInteKanTa;
    String msg;

    public GroupRead(DataStore ds, GUI gui) {
        this.ds = ds;
        this.gui = gui;
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
        uppdragViInteKanTa = new LinkedList<Integer>();
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
//                    System.out.println("GroupList: " + Arrays.toString(groupList));
                    tempos[i] = Integer.parseInt(groupList[1]);
                    groupDate[i] = groupList[0];
                    groupID[i] = groupList[1];
                    groupMessage[i] = groupList[2];
                }
                System.out.println("groupMessage: " + Arrays.toString(groupMessage));

                //Swoopa meddelandet i rätt ordning med en bubblesort
                for (int i = 0; i < tempos.length; i++) {
                    for (int j = 1; j < (tempos.length - i); j++) {
                        if (tempos[j - 1] > tempos[j]) {

                            //Swoopa datumet
                            temp1 = groupDate[j - 1];
                            groupDate[j - 1] = groupDate[j];
                            groupDate[j] = temp1;

                            //Swoppa groupID
                            temp2 = groupID[j - 1];
                            groupID[j - 1] = groupID[j];
                            groupID[j] = temp2;

                            //Swoppa groupMessage
                            temp3 = groupMessage[j - 1];
                            groupMessage[j - 1] = groupMessage[j];
                            groupMessage[j] = temp3;

                            //Swoppa tempos
                            temp4 = tempos[j - 1];
                            tempos[j - 1] = tempos[j];
                            tempos[j] = temp4;

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
                        uppdragGroup1.clear();
                        System.out.println("Hur lång är dummyList om i = 0? " + dummyList.length);
                        for (int j = 0; j < dummyList.length; j++) {
                            uppdragGroup1.add(dummyList[j]);

                        }
                    } //Ändra ordning här breoende på ordning i HTTP
                    else if (i == 1) {
                        uppdragGroup4.clear();
                        System.out.println("Hur lång är dummyList om i = 1? " + dummyList.length);
                        for (int j = 0; j < dummyList.length; j++) {
                            uppdragGroup4.add(dummyList[j]);
                        }
                    } else if (i == 2) {
                        uppdragGroup5.clear();
                        System.out.println("Hur lång är dummyList om i = 2? " + dummyList.length);
                        for (int j = 0; j < dummyList.length; j++) {

                            uppdragGroup5.add(dummyList[j]);
                        }
                    }
                }

//                System.out.println("Grupp 1 Uppdrag" + uppdragGroup1);
//                System.out.println("Grupp 4 Uppdrag" + uppdragGroup4);
//                System.out.println("Grupp 5 Uppdrag" + uppdragGroup5);

                //Ta reda på vad de andra grupperna vill göra
                uppdragViInteKanTa.clear();
                for (int i = 0; i < 3; i++) {
                    System.out.println("i: " + i);
                    if (i != 1) {

                        if (groupPlats[i].equals(groupPlats[1])) { //Kolla om de andra grupperna har samma upphämtningsplats
                            System.out.println("GroupCost varv " + i + " är " + Arrays.toString(groupCost));
                            //Kolla hur det är med kostnaderna
                            if (Integer.parseInt(groupCost[i]) < Integer.parseInt(groupCost[1])) { //Om en annan grupp har lägre kostnad
                                //Vi kommer granterat ha samma första uppdrag så det får vi inte göra
                                System.out.println("Minsta kostnaden är: " + groupCost[i]);
                                //Vi får inte heller ta uppdrag:
                                //if (groupUppdrag[i].length() > 1) {
                                if (i == 0) {
                                    for (int j = 0; j < uppdragGroup1.size(); j++) {
                                        if (!uppdragViInteKanTa.contains(j)) {
                                            uppdragViInteKanTa.add(j);
                                            System.out.println("i = 0, dvs grupp 1 " + j);
                                        }
                                    }
                                } else if (i == 2) {
                                    for (int j = 0; j < uppdragGroup5.size(); j++) {
                                        if (!uppdragViInteKanTa.contains(Integer.parseInt(uppdragGroup5.get(j)))) {
                                            uppdragViInteKanTa.add(Integer.parseInt(uppdragGroup5.get(j)));
                                            System.out.println("i = 2, dvs grupp 5 " + j);
                                        }
                                    }
                                }
                                // }

                            } else if (Integer.parseInt(groupCost[i]) == Integer.parseInt(groupCost[1])) {

                                //Jämför grupp-ID om kostnaden är samma
                                if (Integer.parseInt(groupID[i]) < Integer.parseInt(groupID[1])) {
                                    //Vi får inte heller ta uppdrag:
                                    if (groupUppdrag[i].length() > 1) {
                                        if (i == 0) {
                                            for (int j = 0; j < uppdragGroup1.size(); j++) {
                                                if (!uppdragViInteKanTa.contains(j)) {
                                                    uppdragViInteKanTa.add(j);
                                                }
                                            }
                                        } else if (i == 2) {
                                            for (int j = 0; j < uppdragGroup5.size(); j++) {
                                                if (!uppdragViInteKanTa.contains(j)) {
                                                    uppdragViInteKanTa.add(j);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                tempis = new LinkedList<Integer>();

                for (int k = 0; k < ds.uppdragsIDArray.length; k++) {
                    tempis.add(Integer.parseInt(ds.uppdragsIDArray[k]));
                }

                for (int k = 0; k < tempis.size(); k++) {
                    for (int j = 0; j < uppdragViInteKanTa.size(); j++) {
                        if (tempis.get(k) == uppdragViInteKanTa.get(j)) {
                            tempis.remove(k);
                        }
                    }
                }
                
//            System.out.println("tempis"+tempis);
        

//        for (int m = 0; m < uppdragViInteKanTa.size(); m++) {
//            for (int n = 0; n <) {
//                if (Integer.parseInt(ds.uppdragsIDArray[m]) != tempis[m]) {

//
//                System.out.println("uppdragviintekanta: " + uppdragViInteKanTa);
//                System.out.println("uppdragsIDArrays: " + Arrays.toString(ds.uppdragsIDArray));
//                System.out.println("ds.upppdrag" + ds.uppdrag);

                //Vad gör vi om det inte finns några uppdrag vi vill ta på den platsen
                if (tempis.size() == 0) {
                    //Då ska vi kolla på en ny plats
                }
                
                //        for (int m = 0; m < uppdragViInteKanTa.size(); m++) {
                //            for (int n = 0; n <) {
                //                if (Integer.parseInt(ds.uppdragsIDArray[m]) != tempis[m]) {
                //
                //                    hg.putmessage(cm.createMessage(ds.closestPlats, Integer.toString(op.getCost(ds.a, ds.dest_node)),
                //                            ds.uppdragsIDArray[0] + "," + ds.uppdragsIDArray[ ?  ?  ?]));
                //                }
                //            }
                //        }
 
                
                for (int i = 0; i < tempis.size(); i++) {
                    if (i < 2) { //Eftersom vi bara kan samåka två kunder åt gången

                        ds.uppdrag.clear();
                        ds.uppdrag.add(Integer.toString(tempis.get(i)));
                    }
                }
                System.out.println("ds.uppdrag är : " + ds.uppdrag);
                if (ds.uppdrag.size() == 1) {
                    gui.appendCapacity("Det uppdrag vi säger till gruppen att vi vill ta är : " + ds.uppdrag.get(0));
                    msg = ds.valdPlats + "!" + ds.distanceCP + "!" + ds.uppdrag.get(0);
                    System.out.println("Jag är i ifen");
                } else {
                    gui.appendCapacity("Det uppdrag vi säger till gruppen att vi vill ta är : " + ds.uppdrag.get(0) + " , " + ds.uppdrag.get(1));
                    msg = ds.valdPlats + "!" + ds.distanceCP + "!" + ds.uppdrag.get(0) + "," + ds.uppdrag.get(1);
                    System.out.println("JAg är i elsen");
                }

                hg.putmessage(msg);

                Thread.sleep(1500);
            }

        } catch (InterruptedException e) {

            
    }
}
}
