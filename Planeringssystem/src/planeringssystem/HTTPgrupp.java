package planeringssystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HTTPgrupp {

    HTTPgrupp() {
        
    }
/*
    public void getmessage(int messagetype) {
        try {

            String[] dummyList;

            HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/getmessage.php?messagetype=" + messagetype; 

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
    } //getmessage slut
*/
    public void putmessage(int messagetype, String message) {
        try {

            //String[] dummyList;

            //HTTPanrop http = new HTTPanrop();

            String url = "http://tnk111.n7.se/putmessage.php?groupid=4&messagetype=" + messagetype + "&message=" + message;

            
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
/*
            dummyList = inkommande_text.split("\\n");
            System.out.println(Arrays.toString(dummyList));
*/
            inkommande.close();
            System.out.println(inkommande_samlat.toString()); 


        } catch (Exception e) {
            System.out.print(e.toString());
        }
    } //putmessage slut

}
