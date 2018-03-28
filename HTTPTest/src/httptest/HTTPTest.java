package httptest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HTTPTest {

    private int passengers;

    public int getPassengers() {
        return passengers;
    }

    public static void
            main(String[] args) {
        try {

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
            int temY = 20;

            HTTPTest http = new HTTPTest();
            String url = "http://tnk111.n7.se/listauppdrag.php?plats=A";  //"http://tnk111.n7.se/tauppdrag.php?plats=A&id=1&passagerare=8&grupp=4" , "http://tnk111.n7.se/aterstall.php?scenario=1"
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

            //Specifika fallet: http://tnk111.n7.se/listauppdrag.php?plats=A
            dummyList = inkommande_samlat.toString().split("\n");

            int i = 0;
            while (i < dummyList.length - 1) {
                int j = 0;
                dummyList2 = dummyList[i + 1].split(";"); //En sak jag tänkte på: Hur blir det med 4an???

                while (j < dummyList2.length) {
                    uppdragsIDArray[i] = dummyList2[0];
                    destinationUppdragArray[i] = dummyList2[1]; //Fattar den att detta är två olika noder?
                    passengersArray[i] = dummyList2[2];
                    samakningArray[i] = dummyList2[3];
                    pointsArray[i] = dummyList2[4];
                    j++;
                }
                i++;
            }

            // Beräknar avståndet till varje uppdrag
            for (int k = 0; k < destinationUppdragArray.length; k++) {
                String[] dummyString = new String[2];
                dummyString = destinationUppdragArray[k].split(",");
                int dummyIntX = Integer.parseInt(dummyString[0]);
                int dummyIntY = Integer.parseInt(dummyString[1]);
                // distance[k] = Math.sqrt(Math.pow((dummyIntX - tempX), 2) + Math.pow((dummyIntY - tempY), 2)); 
                
            }

        
           

        //Blaha blaha?
//            Specifika fallet: http://tnk111.n7.se/listaplatser.php
//            dummyList = inkommande_samlat.toString().split("\n");
//            
//            int j = 0;
//            while (j <= (dummyList.length-1)) {
//                dummyList2 = dummyList[j].toString().split(";");
//                platserArray[j] = dummyList2[0];
//                destinationUppdragArray[j] = dummyList2[0];
//                j++;
//            }
        inkommande.close();

//            System.out.println(inkommande_samlat.toString());
//    System.out.println(Arrays.toString(uppdragsIDArray));
    }
    catch (Exception e

    
        ) {
            System.out.print(e.toString());
    }
}
}
