/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agv_kommunikation;

/**
 *
 * @author Burz
 */
public class AGV_kommunikation {

    Transmit tr;
    Receive rcv;
    Thread t1;
    Thread t2;
    
    AGV_kommunikation(){
        
        tr = new Transmit();
        t1 = new Thread(tr);
        
        rcv = new Receive();
        t2 = new Thread(rcv);
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AGV_kommunikation x = new AGV_kommunikation();
        
    }
    
}
