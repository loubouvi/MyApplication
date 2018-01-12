package jow.dis.eseo.fr.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jow.dis.eseo.fr.myapplication.modele.Jury;
import jow.dis.eseo.fr.myapplication.modele.Project;

/**
 * Created by joann on 11/01/2018.
 */

public class ProjectAdapterLIJUR extends
        RecyclerView.Adapter<ProjectAdapterLIJUR.JuryViewHolder> {
    private ProjectAdapter projectAdapter;
    private LIJURActivity lijurActivity;
    private MYPRJActivity myprjActivity;
    private List<Jury> juryList;
    private List<Integer> positionsExpanded;
    private List<Project> projects;

    public ProjectAdapterLIJUR(LIJURActivity lijurActivity) {
        this.lijurActivity = lijurActivity;
        setJuryList(new ArrayList<Jury>());
        positionsExpanded = new ArrayList<>();
    }

    public void setJuryList(List<Jury> juries) {
        this.juryList = juries;
    }

    @Override
    public int getItemCount() {
        return juryList.size();
    }

    @Override
    public JuryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View juryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_lijur, parent, false);
        Log.d("MainAdapter","onCreateViewHolder()");
        return new JuryViewHolder(juryView);
    }


    @Override
    public void onBindViewHolder(JuryViewHolder holder, final int position) {
        Log.d("MainAdapter","onBindViewHolder()");
        final Jury jury = juryList.get(position);
        holder.juryId.setText(jury.getIdJury());
        holder.juryDate.setText(jury.getDate());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainAdapter","Item 'clicked'");
               // lijurActivity.clickItem(jury); // fait le lien pour voir les détails du projet
            }
        });
    }

    class JuryViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private TextView juryId = null;
        private TextView juryDate = null;


        public JuryViewHolder(View view) {
            super(view);
            Log.d("MainAdapter","JuryViewHolder()");
            this.view = view;
            juryId = view.findViewById(R.id.jury_id);
            juryDate = view.findViewById(R.id.jury_date);
            /*projectSupervisor = view.findViewById(R.id.project_superviseur);
            projectConfid = view.findViewById(R.id.project_confidential);
            projectPoster = view.findViewById(R.id.project_poster);*/
        }
    }

}
