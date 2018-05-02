package planeringssystem;

import java.util.Arrays;

public class UppdragsInfo {

    private DataStore ds;
    private HTTPanrop ha;
    private String [] dummyList;

    public UppdragsInfo(DataStore ds, HTTPanrop ha) {
        this.ds = ds;
        this.ha = ha;
    }

    //Tog detta från mainen och hoppas att det ska göra samma sak fast i denna void
    
    public void UppdragsInfo(DataStore ds, HTTPanrop ha) {
        //Här ska vi bestämma vilket/vilka uppdrag som vi vill utföra.
        //Vi måste ta det första. Vi måste hålla oss till vår kapacitet
        //Vi måste kontrollera att samåkning tillåts.
        //Skapa arrayer för det vi vill spara 
        System.out.println("Yo "+ds.closestPlats);
        
        ds.uppdragsIDArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.destinationPlatserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.destinationUppdragArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.passengersArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.samakningArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.pointsArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        //platserArray = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.destinationUppdragX = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];
        ds.destinationUppdragY = new String[Integer.parseInt(ha.messagetype(ds.closestPlats)[0])];


        //Lägg rätt information i respektive array
        for (int i = 0; i < Integer.parseInt(ha.messagetype(ds.closestPlats)[0]); i++) {
            dummyList = ha.messagetype(ds.closestPlats)[i + 1].toString().split(";");
            ds.uppdragsIDArray[i] = dummyList[0];
            ds.destinationUppdragArray[i] = dummyList[1]; //Fattar den att detta är två olika noder?
            ds.passengersArray[i] = dummyList[2];
            ds.samakningArray[i] = dummyList[3];
            ds.pointsArray[i] = dummyList[4];
            System.out.println(ds.destinationUppdragArray[i]);
        }
    }

}
