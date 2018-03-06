/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planeringssystem;

/**
 *
 * @author Simon
 */
public class Planeringssystem {
    
    DataStore ds;
    GUI gui;

    Planeringssystem(){
        /*
         * Initialize the DataStore call where all "global" data will be stored
         */
        ds = new DataStore();

        /*
         * Reads in the file with the map. For now it is street.txt. Don´t know where the file will be stored.
         */
        ds.setFileName("streets.txt");
        ds.readNet();
        
        /*
         * För att få fram kartan verkar det som att vi behöver ändra lite i GUI.
        */
        gui = new GUI(ds);
        gui.setVisible(true);

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Planeringssystem x = new Planeringssystem();
        // annda430
    }

}
