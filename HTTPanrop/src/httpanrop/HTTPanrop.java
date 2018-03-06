package httpanrop;

/**
 *
 * @author itn
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPanrop {

    public static void
            main(String[] args) {
        try {
            HTTPanrop http = new HTTPanrop();
            String url
                    = "http://tnk111.n7.se/tauppdrag.php?plats=A&id=1&passagerare=4&grupp=4";
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();
            System.out.println(
                    "\nAnropar: "
                    + url);
            int mottagen_status = anslutning.getResponseCode();
            System.out.println(
                    "Statuskod: "
                    + mottagen_status);
            BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));
            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();
            while ((inkommande_text = inkommande.readLine()) != null) {
                inkommande_samlat.append(inkommande_text+"\n");
            }
            inkommande.close();
            System.out.println(inkommande_samlat.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
