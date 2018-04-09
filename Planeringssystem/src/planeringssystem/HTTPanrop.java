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

    public HTTPanrop() {
        //COnstructor
        String[] dummyList = new String[100]; //La in 100 på alla nu för at vara på den säkra sidan, blir kanske konstigt med null.
            String[] dummyList2 = new String[100];
            String[] uppdragsIDArray = new String[100];
            String[] destinationPlatserArray = new String[100];
            String[] destinationUppdragArray = new String[100];
            String[] passengersArray = new String[100];
            String[] samakningArray = new String[100];
            String[] pointsArray = new String[100];
            String[] platserArray = new String[100];

            int[] distance = new int[100];
            //Här används robotens nuvarande posistion senare. 
            int tempX = 10;
            int tempY = 20;
    }

    public int getPassengers() {
        return passengers;
    }

    public void messagetype() { // listaplatser
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/listauppdrag.php";  //"http://tnk111.n7.se/tauppdrag.php?plats=A&id=1&passagerare=8&grupp=4" , "http://tnk111.n7.se/aterstall.php?scenario=1"

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");

            }

            dummyList = inkommande_text.split("\\n");
            System.out.println(Arrays.toString(dummyList));

            inkommande.close();
            System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    } //listaplatser slut
    
    public void messagetype(int scenario){ //aterstall
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/listauppdrag.php?scenario=" + scenario;  //"http://tnk111.n7.se/tauppdrag.php?plats=A&id=1&passagerare=8&grupp=4" , "http://tnk111.n7.se/aterstall.php?scenario=1"

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");

            }

            dummyList = inkommande_text.split("\\n");
            System.out.println(Arrays.toString(dummyList));

            inkommande.close();
            System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }//listauppdrag slut
    
    public void messagetype(String plats) { // listaUppdrag
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/listauppdrag.php?plats=" + plats;  //"http://tnk111.n7.se/tauppdrag.php?plats=A&id=1&passagerare=8&grupp=4" , "http://tnk111.n7.se/aterstall.php?scenario=1"

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");

            }

            dummyList = inkommande_text.split("\\n");
            System.out.println(Arrays.toString(dummyList));

            inkommande.close();
            System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }//listauppdrag slut

    public void messagetype(String plats, int id, int passagerare, int grupp) { // taUppdrag
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/tauppdrag.php?plats=" + plats + "&id=" + id + "&passagerare=" + passagerare+ "&grupp=" + grupp; //, "http://tnk111.n7.se/aterstall.php?scenario=1"

            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            System.out.println("Statuskod: " + mottagen_status);

            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();

            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text + "\n");

            }

            dummyList = inkommande_text.split("\\n");
            System.out.println(Arrays.toString(dummyList));

            inkommande.close();
            System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }// tauppdrag slut
}
