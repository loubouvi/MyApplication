package jow.dis.eseo.fr.myapplication;

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

import jow.dis.eseo.fr.myapplication.modele.Project;
import jow.dis.eseo.fr.myapplication.modele.WrapperProjects;

public class MYPRJActivity extends AppCompatActivity {
    public static final String URL_SERVER = "https://192.168.4.10/www/pfe/webservice.php?q=";
    private String token;
    private String message_result;
    private String message_error;
    private WrapperProjects wp;
    static Gson gson;
    static MYPRJActivity instance;
    private ProjectAdapterMYPRJ projectAdapterMYPRJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = (String) getIntent().getExtras().getSerializable("TOKEN");
        final String user_string = (String) getIntent().getExtras().getSerializable("USERNAME");
        //new getToken().execute();
        projectAdapterMYPRJ = new ProjectAdapterMYPRJ(this);
        new JSONTaskMYPRJ().execute(URL_SERVER+"MYPRJ&user="+user_string+"&token="+token);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        Log.e("Result **************",wp.getResult());

        if(wp.getProjects()==null){
            Log.e("juries", "jury null");


        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

       recycler.setAdapter(projectAdapterMYPRJ);
    }

    public class JSONTaskMYPRJ extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
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
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                Log.e("json MYPRJ *********", finalJson);
                JSONObject finalObject = new JSONObject(finalJson);

                Gson gson = new Gson();
                wp = gson.fromJson(finalJson, WrapperProjects.class);

                //Log.e("gson object", wp.getProjects().get(0).getTitle());
                if(wp.getProjects()==null){
                    Log.e("juries", "jury null");

                }
                message_result = wp.getResult();
                if (message_result.equals("OK")) {
                    //token = finalObject.getString("token");
                    // get all projects
                    projectAdapterMYPRJ.setProjects(wp.getProjects());
                    Log.e("projects", wp.getProjects().get(0).getDescrip());
                }
                if (message_result.equals("KO")) {
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


}
