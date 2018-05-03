package planeringssystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HTTPgrupp {

    HTTPgrupp() {
        
    }

    public void getmessage() {
        try {

            String[] dummyList;
            String[] dummyList2;
            String[] date = new String [100];
            String[] groupID = new String [100];
            String[] msg = new String [100];

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/getmessage.php?messagetype=1"; 

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
                System.out.println(inkommande_text);
            }

            
            dummyList = inkommande_samlat.toString().split("\n");
           
//            System.out.println(Arrays.toString(dummyList));

//            int i = 0;
//            while (i < dummyList.length - 1) {
//                int j = 0;
//                dummyList2 = dummyList[i].split(";"); //En sak jag tänkte på: Hur blir det med 4an???
//
//                while (j < dummyList2.length) {
//                    date[i] = dummyList2[0];
//                    groupID[i] = dummyList2[1]; //Fattar den att detta är två olika noder?
//                    msg[i] = dummyList2[2];
//                    j++;
//                }
//                i++;
//            }
            inkommande.close();
            //System.out.println(inkommande_samlat.toString());
           
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    } //getmessage slut


    public void putmessage(String message) {
        try {


            String url = "http://tnk111.n7.se/putmessage.php?groupid=4&messagetype=1&message=" + message;
            
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            //System.out.println("\nAnropar: " + url);

            int mottagen_status = anslutning.getResponseCode();
            //System.out.println("Statuskod: " + mottagen_status);

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    } //putmessage slut

}
