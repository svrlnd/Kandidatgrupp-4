package planeringssystem_v2;

/**
 *
 * @author Simon
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPgrupp {

    String[] dummylist;
    int arraysize;

    public HTTPgrupp() {
        //Constructor
        arraysize = 100;
    }

    public String[] getmessage() { //getmessage
        dummylist = new String[arraysize];

        try {

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
            }

            dummylist = inkommande_samlat.toString().split("\n");

            inkommande.close();
            //System.out.println(inkommande_samlat.toString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return dummylist;
    } //getmessage slut

    public void putmessage(String message) {
        try {

            //VET INTE OM DENNA FUNKAR?
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


