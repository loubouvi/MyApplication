package jow.dis.eseo.fr.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import jow.dis.eseo.fr.myapplication.modele.WrapperProjects;
import jow.dis.eseo.fr.myapplication.modele.Project;

public class LIPRJ_Activity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String TAG = LIPRJ_Activity.class.getSimpleName();
    // URL to get contacts JSON
    //private static String url = "https://192.168.4.10/www/pfe/webservice.php?q=LOGON&user=woodwric&pass=odoxH6dwrjix";
    public static final String URL_SERVER = "https://192.168.4.10/www/pfe/webservice.php?q=";
    private String token;
    private String message_result;
    private String message_error;
    private WrapperProjects wp;
    static Gson gson;
    static LIPRJ_Activity instance;
    private ProjectAdapter projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token = (String) getIntent().getExtras().getSerializable("TOKEN");
        final String user_string = (String) getIntent().getExtras().getSerializable("USERNAME");
        //new getToken().execute();
        projectAdapter = new ProjectAdapter(this);
        new JSONTaskLiprj().execute(URL_SERVER+"LIPRJ&user="+user_string+"&token="+token);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        Log.e("Result **************",wp.getResult());


        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        recycler.setAdapter(projectAdapter);
    }


    public void AlertMessage(String message){
        AlertDialog.Builder construitAlert = new AlertDialog.Builder(LIPRJ_Activity.this);
        construitAlert.setMessage("Votre problème de connexion est dû : \n"+message);
        construitAlert.setCancelable(true);

        construitAlert.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = construitAlert.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public class JSONTaskLiprj extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground (String ... params){
            HttpsURLConnection connexion = null;
            BufferedReader reader = null;

            try {
                ConnexionActivity ca = ConnexionActivity.getInstance();
                SSLContext context = ca.Certificat();
                URL url = new URL(params[0]);
                connexion = (HttpsURLConnection) url.openConnection();
                connexion.setSSLSocketFactory(context.getSocketFactory());
                connexion.connect();
                InputStream stream = connexion.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line="";
                while((line=reader.readLine()) !=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                Log.e("json LIPRJ *********", finalJson);
                JSONObject finalObject = new JSONObject(finalJson);

                Gson gson = new Gson();
                wp = gson.fromJson(finalJson, WrapperProjects.class);
                Log.e("gson object", wp.getProjects().get(0).getTitle());

                message_result=wp.getResult();
                if (message_result.equals("OK")){
                    //token = finalObject.getString("token");
                    // get all projects
                    projectAdapter.setProjects(wp.getProjects());
                    Log.e("projects", wp.getProjects().get(0).getDescrip());
                }
                if (message_result.equals("KO")){
                    message_error = finalObject.getString("error");
                }
                return message_result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connexion != null) {
                    connexion.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
           /* message_result=wp.getResult();
            if (message_result.equals("OK")){
                //token = finalObject.getString("token");
                // get all projects
                projectAdapter.setProjects(wp.getProjects());
                Log.e("projects", wp.getProjects().get(0).getDescrip());
            }
            if (message_result.equals("KO")){
                //message_error = finalObject.getString("error");
            }*/
        }
    }

    public static LIPRJ_Activity getInstance(){
        return instance;
    }

    public void clickItem(Project project) {
        Intent intent = new Intent(this, DetailsProjectActivity.class);
        intent.putExtra("project_title", project.getTitle());
        intent.putExtra("project_description", project.getDescrip());
        intent.putExtra("project_confid", project.getConfid());
        intent.putExtra("project_supervisor", project.getSupervisor().getForename()+" "+project.getSupervisor().getSurname());
        intent.putExtra("project_poster", project.getPoster());
        startActivity(intent);
    }

    /*
   private class getToken extends AsyncTask<Void, Void, Void> {

       ProgressDialog proDialog;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
// Showing progress loading dialog
           proDialog = new ProgressDialog(LIPRJ_Activity.this);
           proDialog.setMessage("Please wait...");
           proDialog.setCancelable(false);
           proDialog.show();
       }

       @Override
       protected Void doInBackground(Void... voids) {
           // Creating service handler class instance
          // WebRequest webreq = new WebRequest();
          // WebService ws = new WebService();
// Making a request to url and getting response
          // String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GETRequest);
           // String jsonStr = getAuthenticate("woodwric", "odoxH6dwrjix");
           //Log.d("Response: ", "> " + jsonStr);
           return null;
       }

       @Override
       protected void onPostExecute(Void requestresult) {
           super.onPostExecute(requestresult);
        // Dismiss the progress dialog
           if (proDialog.isShowing())
               proDialog.dismiss();

       }
   }

    public SSLContext Certificat(){

        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = getResources().openRawResource(getResources().getIdentifier("inter", "raw", getPackageName()));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                //System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
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

            return context;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }*/
/*
    private InputStream sendRequest(URL url) throws Exception {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput;
            caInput = getResources().openRawResource(getResources().getIdentifier("inter","raw", getPackageName()));
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

    public String getAuthenticate(String username, String password) {
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
    }
*/
}
