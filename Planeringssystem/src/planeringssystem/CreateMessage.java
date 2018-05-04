package planeringssystem;

public class CreateMessage {

    private DataStore ds;
    private ClosestPlats cp;
    String message;

    public CreateMessage(DataStore ds, ClosestPlats cp) {
        this.ds = ds;
        this.cp = cp;
    }

    public String createMessage(String plats,String kostnad,String uppdrag) {//Detta är till gruppen
        
        message = plats + "!" + kostnad + "!" + uppdrag;

        return message;
    }

//    Här ska meddelandet till AGVn göras, just nu avmarkerat.
    public String createMessageAGV(){ //Detta är till AGVn
        
        message = "#" + ds.enable + ds.ordernummer + ds.antal_passagerare + ds.korinstruktion + ds.kontroll + " " + " " + ds.spegling + '$';
        
        //Här ska vi spegla (verkar göras i robotread)
        
        return message;
    }
    
}
