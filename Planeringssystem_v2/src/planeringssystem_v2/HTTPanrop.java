package planeringssystem_v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;



public class HTTPanrop {
    
    //private static int passengers;
    String[] dummyList;
    int arraySize;
    
    public HTTPanrop() {
        //Constructor
        arraySize = 100;
        //passengers = 0;
    }

    public String[] messagetype() { // listaplatser
        dummyList = new String[arraySize];
        try {

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/listaplatser.php";

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            //System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            //System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");
            }

            dummyList = inkommande_samlat.toString().split("\n");

            inkommande.close();

        } catch (Exception e) {
            System.out.print("catch listaplatser: " + e.toString());
        }
        return dummyList;
    } //listaplatser slut
    
    
    public String[] messagetype(String plats) { // listaUppdrag
        dummyList = new String[arraySize];
        try {

            HTTPanrop http = new HTTPanrop();
            String url = "http://tnk111.n7.se/listauppdrag.php?plats=" + plats;
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            //System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            //System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");
            }

            dummyList = inkommande_samlat.toString().split("\n");

            inkommande.close();

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return dummyList;
    }//listaUppdrag slut
    
    
    public String messagetype(String plats, int id, int passagerare) { // taUppdrag
        try {

            //String[] dummyList;
            //HTTPanrop http = new HTTPanrop();
            String url = "http://tnk111.n7.se/tauppdrag.php?plats=" + plats + "&id=" + id + "&passagerare=" + passagerare + "&grupp=4"; //, "http://tnk111.n7.se/aterstall.php?scenario=1"

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            //System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;

            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");

            }

            //dummyList = inkommande_text.split("\\n");
            inkommande.close();
            System.out.println(inkommande_samlat.toString());
            //return inkommande_samlat.toString(); // beviljas eller nekas
            

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return "";
    }// tauppdrag slut
    
    
}
