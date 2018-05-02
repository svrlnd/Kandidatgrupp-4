package planeringssystem;

public class CreateMessage {

    private DataStore ds;
    private ClosestPlats cp;
    String message;

    public CreateMessage(DataStore ds, ClosestPlats cp) {
        this.ds = ds;
        this.cp = cp;
    }

    public String createMessage() {//Detta är till gruppen
        
        if (ds.cap <= 5) { //Integer.parseInt(passengersArray [0])
            //Meddela företagsgrupp att närmsta upphämtningsplats är closestPlats
            //och att avståndet dit är tempis + resterande routeCost
            //och att vi tänker ta första uppdraget.
            
            
            
            message = ds.closestPlats + "#" + (ds.min /* + ds.routeCost OBS! detta ska vara kvarvarande kostnad på nuvarande rutt*/) + "#" + ds.uppdragsIDArray[0];
        } else {
            //Här ska vi ta fler uppdrag från listan av de som vill samåka
//                if{
            //ska med en loop kontrollera om någon vill samåka. 
            //av de som vill samåka, ska vi hitta den som avviker från rutten så lite som möjligt mha createplan
//                }

            message = cp.getClosestPlats() + "#" + ds.min + ds.routeCost + "#" + ds.uppdragsIDArray[0];
            System.out.println(message);
        }
        return message;
    }

//    Här ska meddelandet till AGVn göras, just nu avmarkerat.
    public String createMessageAGV(){ //Detta är till AGVn
        
        message = "#" + ds.enable + ds.ordernummer + ds.antal_passagerare + ds.korinstruktion + ds.kontroll + " " + " " + spegling + '$';
        
        //Här ska vi spegla (verkar göras i robotread)
        
        return message;
    }
    
}
