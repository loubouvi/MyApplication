package jow.dis.eseo.fr.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class ConnexionActivity extends AppCompatActivity {

    public static final String RACINE_URL = "https://192.168.4.10/www/pfe/webservice.php?q=";
    private String message_result;
    private String message_error;
    private String token;
    static ConnexionActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;

       // final LinearLayout progression = (LinearLayout) findViewById(R.id.progression);

        Button connexion = (Button) findViewById(R.id.email_sign_in_button);
        connexion.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText user = (EditText) findViewById(R.id.email);
                final String user_string = (String) user.getText().toString();
                EditText password = (EditText) findViewById(R.id.password);
                final String password_string = (String) password.getText().toString();
                new JSONTaskLogon().execute(RACINE_URL+"LOGON&user="+user_string+"&pass="+password_string);

                while(message_result==null){
                   // progression.setVisibility(View.VISIBLE);
                }
                if (message_result.equals("KO")){
                    AlertMessage(message_error);
                 //   progression.setVisibility(View.GONE);
                }
                if (message_result.equals("OK")){
                   // progression.setVisibility(View.GONE);
                    Intent directionVersMain = new Intent(ConnexionActivity.this, LIPRJ_Activity.class);
                    directionVersMain.putExtra("TOKEN", token);
                    directionVersMain.putExtra("USERNAME", user_string);
                    startActivity(directionVersMain);
                }
            }
        });
    }

    public void AlertMessage(String message){
        AlertDialog.Builder construitAlert = new AlertDialog.Builder(ConnexionActivity.this);
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

    public class JSONTaskLogon extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground (String ... params){
            HttpsURLConnection connexion = null;
            BufferedReader reader = null;

            try {
                SSLContext context = Certificat();

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
                Log.e("json response *********", finalJson);
                JSONObject finalObject = new JSONObject(finalJson);

                message_result=finalObject.getString("result");
                if (message_result.equals("OK")){
                    token = finalObject.getString("token");
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
        }
    }

    public static ConnexionActivity getInstance(){
        return instance;
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
    }

}