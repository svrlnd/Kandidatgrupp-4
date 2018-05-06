package planeringssystem;

public class UppdragsInfo {

    private DataStore ds;
    private HTTPanrop ha;
    private HTTPgrupp hg;
    private CreateMessage cm;
    private OptPlan op;
    private String[] dummyList;
    private String[] dummyList2;
    private int len;
    private int minst;
    private int temp;
    String message;
    int temp_cap;
    int min_cost;
    int temp_cost;
    int s;

    public UppdragsInfo(DataStore ds, HTTPanrop ha, HTTPgrupp hg, CreateMessage cm, OptPlan op) {
        this.ds = ds;
        this.ha = ha;
        this.hg = hg;
        this.cm = cm;
        this.op = op;
    }

    //Tog detta från mainen och hoppas att det ska göra samma sak fast i denna void
    public void UppdragsInfo(DataStore ds, HTTPanrop ha, HTTPgrupp hg, CreateMessage cm) {
        //Här ska vi bestämma vilket/vilka uppdrag som vi vill utföra.
        //Vi måste ta det första. Vi måste hålla oss till vår kapacitet
        //Vi måste kontrollera att samåkning tillåts.
        //Skapa arrayer för det vi vill spara 
        System.out.println("Yo " + ds.closestPlats);

        len = Integer.parseInt(ha.messagetype(ds.closestPlats)[0]);
        minst = Integer.MAX_VALUE;

        ds.uppdragsIDArray = new String[len];
        ds.destinationPlatserArray = new String[len];
        ds.destinationUppdragArray = new String[len];
        ds.passengersArray = new String[len];
        ds.samakningArray = new String[len];
        ds.pointsArray = new String[len];
        //platserArray = new String[len];
        ds.destinationUppdragStart = new String[len];
        ds.destinationUppdragSlut = new String[len];
        temp_cap = ds.initial_cap;

        //Lägg rätt information i respektive array
        for (int i = 0; i < len; i++) {
            dummyList = ha.messagetype(ds.closestPlats)[i + 1].toString().split(";");
            ds.uppdragsIDArray[i] = dummyList[0];
            ds.destinationUppdragArray[i] = dummyList[1]; //Fattar den att detta är två olika noder?
            ds.passengersArray[i] = dummyList[2];
            ds.samakningArray[i] = dummyList[3];
            ds.pointsArray[i] = dummyList[4];
            dummyList2 = ds.destinationUppdragArray[i].split(",");
            ds.destinationUppdragStart[i] = dummyList2[0];
            ds.destinationUppdragSlut[i] = dummyList2[1];
        }

        min_cost = Integer.MAX_VALUE;
        s = -1;

        if (temp_cap <= Integer.parseInt(ds.passengersArray[0])) {
            temp_cap = 0;
        } else {
            temp_cap -= Integer.parseInt(ds.passengersArray[0]); //Räknar kvarvarande kapacitet
        }

        if (Integer.parseInt(ds.samakningArray[0]) == 0 || temp_cap == 0) {
            hg.putmessage(cm.createMessage(ds.closestPlats,
                    Integer.toString(op.getCost(ds.a, ds.dest_node)),
                    ds.uppdragsIDArray[0]));
        } else {
            for (int i = 1; i < len; i++) {
                if (Integer.parseInt(ds.samakningArray[i]) == 1) {

                    temp_cost = op.getCost(Integer.parseInt(ds.destinationUppdragStart[0]), Integer.
                            parseInt(ds.destinationUppdragStart[i]));

                    if (temp_cost < min_cost) {
                        min_cost = temp_cost;
                        s = i; //Sparar vilket uppdrag som är närmast.
                    }
                }
            }
        }
        if (s != -1) {
            hg.putmessage(cm.createMessage(ds.closestPlats,
                    Integer.toString(op.getCost(ds.a, ds.dest_node)),
                    ds.uppdragsIDArray[0] + "," + ds.uppdragsIDArray[s]));
        }
    }
}
