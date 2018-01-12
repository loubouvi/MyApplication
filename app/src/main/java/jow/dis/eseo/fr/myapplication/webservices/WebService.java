package jow.dis.eseo.fr.myapplication.webservices;

import com.google.gson.Gson;

/**
 * Created by Claire on 20/12/2017.
 */

public class WebService  {
    public static final String URL_SERVER = "https://192.168.4.10/www/pfe/webservice.php?q=";

    static Gson gson;

    public WebService() {
        gson = new Gson();
    }
/*
    private static InputStream sendRequest(URL url) throws Exception {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput;
            caInput = LIPRJ_Activity.getResources().openRawResource(LIPRJ_Activity.getResources().getIdentifier("inter","raw", getPackageName()));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Ouverture de la connexion
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Connexion à l'URL
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Si le serveur nous répond avec un code OK
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return con.getInputStream();
            }
        } catch (Exception e) {
            throw new Exception("");
        }
        return null;
    }

    public static String getAuthenticate(String username, String password) {
        String urlLogon = "LOGON&user="+username+"&pass="+password;

        try {
            // Envoi de la requête
            InputStream inputStream = sendRequest(new URL(URL_SERVER+urlLogon));

            // Vérification de l'inputStream
            if(inputStream != null) {
                // Lecture de l'inputStream dans un reader
                InputStreamReader reader = new InputStreamReader(inputStream);

                // Retourne la liste désérialisée par le moteur GSON
                Log.e("WebService", gson.fromJson(reader, String.class));
                return gson.fromJson(reader, String.class);
            }

        } catch (Exception e) {
            Log.e("WebService", "Impossible de rapatrier les données :(");
        }
        return null;
    }*/

}