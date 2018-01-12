package jow.dis.eseo.fr.myapplication.modele;

/**
 * Created by Claire on 10/01/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info {

    @SerializedName("projects")
    @Expose
    private List<Project> projects = null;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
