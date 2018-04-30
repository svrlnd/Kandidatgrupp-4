package planeringssystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HTTPanrop {

    private static int passengers;
    private static int id;
    private static String plats;
    String[] dummyList;
    String[] dummyList1;
    String[] dummyList2;
    String[] dummyList3;
    String[] dummyList4;
    String[] platser;
    String[] platsX;
    String[] platsY;
    String[] hejhej;
    String[] uppdragsIDArray;
    String[] destinationPlatserArray;
    String[] destinationUppdragArray;
    String[] passengersArray;
    String[] samakningArray;
    String[] pointsArray;
    String[] platserArray;
    String[] destinationUppdragX;
    String[] destinationUppdragY;
    int arraySize;

    public HTTPanrop() {
        //Constructor
        arraySize = 100;
        //La in 100 på alla nu för at vara på den säkra sidan, blir kanske konstigt med null.
        dummyList2 = new String[arraySize];
        dummyList3 = new String[arraySize];
        dummyList4 = new String[arraySize];
        hejhej = new String[arraySize];
        uppdragsIDArray = new String[arraySize];
        destinationPlatserArray = new String[arraySize];
        destinationUppdragArray = new String[arraySize];
        passengersArray = new String[arraySize];
        samakningArray = new String[arraySize];
        pointsArray = new String[arraySize];
        platserArray = new String[arraySize];
        destinationUppdragX = new String[arraySize];
        destinationUppdragY = new String[arraySize];
        platser = new String[arraySize];
        platsX = new String[arraySize];
        platsY = new String[arraySize];

        int[] distance = new int[arraySize];
        //Här används robotens nuvarande posistion senare. 
        int tempX = 10;
        int tempY = 20;
    }

    public int getPassengers() {
        return passengers;
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
            //System.out.println(Arrays.toString(dummyList));

            //Detta görs just nu i main
//            for (int i = 0; i < Integer.parseInt(dummyList[0]); i++) {
//                dummyList3 = dummyList[i + 1].toString().split(";");
//                platser[i] = dummyList3[0];
//                hejhej[i] = dummyList3[1];
//            }
//
//            for (int j = 0; j < Integer.parseInt(dummyList[0]); j++) {
//                dummyList4 = hejhej[j].toString().split(",");
//                platsX[j] = dummyList4[0];
//                platsY[j] = dummyList4[1];
//            }
//            System.out.println("platser " + Arrays.toString(platser));
//            System.out.println("X " + Arrays.toString(platsX));
//            System.out.println("Y " + Arrays.toString(platsY));

            inkommande.close();

        } catch (Exception e) {
            System.out.print("catch listaplatser: " + e.toString());
        }
        return dummyList;
    } //listaplatser slut

    public void messagetype(int scenario) { //aterstall
        StringBuffer inkommande_samlat = new StringBuffer();
        
        try {

            HTTPanrop http = new HTTPanrop();

            // Det som är avkommenterat tror jag inte behövs /A

            String url = "http://tnk111.n7.se/aterstall.php?scenario=" + scenario;

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);            

        } catch (Exception e) {
            System.out.print("catch i aterstall: " + e.toString());
        }
    }//aterstall slut

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

//            for (int i = 0; i < Integer.parseInt(dummyList[0]); i++) {
//                dummyList1 = dummyList[i+1].toString().split(";");
//                uppdragsIDArray[i] = dummyList1[0];
//                destinationUppdragArray[i] = dummyList1[1]; //Fattar den att detta är två olika noder?
//                passengersArray[i] = dummyList1[2];
//                samakningArray[i] = dummyList1[3];
//                pointsArray[i] = dummyList1[4];
//            }
//            
//            //System.out.println(Arrays.toString(destinationUppdragArray));
//
//            for (int j = 0; j < Integer.parseInt(dummyList[0]); j++) {
//                dummyList2 = destinationUppdragArray[j].toString().split(",");
//                destinationUppdragX[j] = dummyList2[0];
//                destinationUppdragY[j] = dummyList2[1];
//            }
//
////            System.out.println("X " + Arrays.toString(destinationUppdragX));
////            System.out.println("Y " + Arrays.toString(destinationUppdragY));
            
            inkommande.close();
            //System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return dummyList;
    }//listaUppdrag slut

    public void messagetype(String plats, int id, int passagerare, int grupp) { // taUppdrag
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/tauppdrag.php?plats=" + plats + "&id=" + id + "&passagerare=" + passagerare + "&grupp=" + grupp; //, "http://tnk111.n7.se/aterstall.php?scenario=1"

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

            dummyList = inkommande_text.split("\\n");
            //System.out.println(Arrays.toString(dummyList));

            inkommande.close();
            //System.out.println(inkommande_samlat.toString());


        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }// tauppdrag slut
}
