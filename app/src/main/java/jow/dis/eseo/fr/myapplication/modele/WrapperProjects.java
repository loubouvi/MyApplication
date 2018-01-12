package jow.dis.eseo.fr.myapplication.modele;

/**
 * Created by Claire on 10/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WrapperProjects {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("api")
    @Expose
    private String api;
    @SerializedName("projects")
    @Expose
    private List<Project> projects = null;
    @SerializedName("juries")
    @Expose
    private List<Jury> juries = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Jury> getJuries() {
        return juries;
    }

    public void setJuries(List<Jury> juries) {
        this.juries = juries;
    }

}
