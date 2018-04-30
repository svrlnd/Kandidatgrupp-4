
package planeringssystem;

public class CreateMessage {
    
    private DataStore ds;
    private ClosestPlats cp;
    String message;
    
    public CreateMessage (DataStore ds, ClosestPlats cp){
        this.ds = ds;
        this.cp = cp;
    }
    
    public String createMessage(){//Detta är till gruppen
        
                    if (5 >= ds.cap){ //Integer.parseInt(passengersArray [0])
                //Meddela företagsgrupp att närmsta upphämtningsplats är closestPlats
                //och att avståndet dit är tempis + resterande routeCost
                //och att vi tänker ta första uppdraget.
                
                //TA BORT DENN AVMARKERING SEN! /GABRIELLA
                //message = cp.getClosestPlats(curNode) + "#" + ds.min + ds.routeCost + "#" + uppdragsIDArray[0];
                
            }
            else{
                //Här ska vi ta fler uppdrag från listan av de som vill samåka
//                if{
                    //ska med en loop kontrollera om någon vill samåka. 
                    //av de som vill samåka, ska vi hitta den som avviker från rutten så lite som möjligt mha createplan
//                }
            }
        return message;
    }
    
}
