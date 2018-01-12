package jow.dis.eseo.fr.myapplication.modele;

/**
 * Created by Claire on 10/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Project {

    @SerializedName("projectId")
    @Expose
    private String projectId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("descrip")
    @Expose
    private String descrip;
    @SerializedName("projets")
    @Expose
    private String poster;
    @SerializedName("supervisor")
    @Expose
    private Supervisor supervisor;
    @SerializedName("confid")
    @Expose
    private String confid;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getConfid() {
        return confid;
    }

    public void setConfid(String confid) {
        this.confid = confid;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
