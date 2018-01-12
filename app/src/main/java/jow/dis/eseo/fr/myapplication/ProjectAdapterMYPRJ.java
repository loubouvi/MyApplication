package jow.dis.eseo.fr.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jow.dis.eseo.fr.myapplication.modele.Project;


public class ProjectAdapterMYPRJ extends
        RecyclerView.Adapter<ProjectAdapterMYPRJ.ProjectViewHolder>{

    private MYPRJActivity myprjActivity;
    private List<Project> projects;
    private List<Integer> positionsExpanded;

    public ProjectAdapterMYPRJ(MYPRJActivity myprjActivity) {
        this.myprjActivity = myprjActivity;
        setProjects(new ArrayList<Project>());
        positionsExpanded = new ArrayList<>();
    }
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public ProjectAdapterMYPRJ.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View filmView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_project_card, parent, false);
        Log.d("MainAdapter","onCreateViewHolder()");
        return new ProjectAdapterMYPRJ.ProjectViewHolder(filmView);
    }


    @Override
    public void onBindViewHolder(ProjectAdapterMYPRJ.ProjectViewHolder holder, final int position) {
        Log.d("MainAdapter","onBindViewHolder()");
        final Project project = projects.get(position);
        holder.projectTitle.setText(project.getTitle());
        holder.projectDescrip.setText(project.getDescrip());
        String superviseur = "Aucun superviseur";
        /*Log.e("Superviseur before ", superviseur);
        if (project.getSupervisor().getSurname()!=null) {
            superviseur = project.getSupervisor().getForename()+ " " + project.getSupervisor().getSurname();
        }*/
        Log.e("Superviseur *******", superviseur);
       /* holder.projectSupervisor.setText("test");
        holder.projectPoster.setText(project.getPoster());
        holder.projectConfid.setText(project.getConfid());*/
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainAdapter","Item 'clicked'");
                myprjActivity.clickItem(project); // fait le lien pour voir les détails du projet
            }
        });
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private TextView projectTitle = null;
        private TextView projectDescrip = null;
        /*private TextView projectSupervisor;
        private TextView projectConfid = null;
        private TextView projectPoster = null;*/

        public ProjectViewHolder(View view) {
            super(view);
            Log.d("MainAdaptater","JuryViewHolder()");
            this.view = view;
            projectTitle = view.findViewById(R.id.project_title);
            projectDescrip = view.findViewById(R.id.project_descrip);
            /*projectSupervisor = view.findViewById(R.id.project_superviseur);
            projectConfid = view.findViewById(R.id.project_confidential);
            projectPoster = view.findViewById(R.id.project_poster);*/
        }
    }
}
