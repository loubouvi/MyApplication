package jow.dis.eseo.fr.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import jow.dis.eseo.fr.myapplication.modele.Project;


public class DetailsProjectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView title;
    private TextView description;
    private TextView confid;
    private TextView poster;
    private TextView supervisor;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_project);
        int clickedProject = 0;
        Intent intent = getIntent();
        //Bundle data = getIntent().getExtras();
        //project = (Project) data("project_extra");
        title = findViewById(R.id.project_title);
        description = findViewById(R.id.project_descrip);
        confid = findViewById(R.id.project_confidential);
        poster = findViewById(R.id.project_poster);
        supervisor = findViewById(R.id.project_superviseur);
        title.setText((String) getIntent().getExtras().getSerializable("project_title"));
        description.setText((String) getIntent().getExtras().getSerializable("project_description"));
        confid.setText((String) getIntent().getExtras().getSerializable("project_confid"));

        supervisor.setText((String) getIntent().getExtras().getSerializable("project_supervisor"));

        if ("true".equals(getIntent().getExtras().getSerializable("project_poster"))) {
            poster.setText("Poster déposé");
        } else {
            poster.setText("Poster non déposé");
        }


      /*  Toolbar toolbar2 = findViewById(R.id.toolbar);
        ImageView exit;
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsProjectActivity.this, ConnexionActivity.class);
                startActivity(intent);
            }
        });
*/
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
       /*@SuppressLint("WrongViewCast") RecyclerView recList = (RecyclerView) findViewById(R.id.project_membre_equipe);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        aa = new FilmRoleAdapter(this, clickedFilm);
        recList.setAdapter(aa);*/



    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.welcome) {
            //Affichage de la liste déroulante de BAlade
            Intent intent = new Intent(DetailsProjectActivity.this, ConnexionActivity.class);
            startActivity(intent);
        } else if (id == R.id.projet) {
            Intent intent = new Intent(DetailsProjectActivity.this, ConnexionActivity.class);
            startActivity(intent);
        }else if (id == R.id.jury) {
            Intent intent = new Intent(DetailsProjectActivity.this, ConnexionActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
