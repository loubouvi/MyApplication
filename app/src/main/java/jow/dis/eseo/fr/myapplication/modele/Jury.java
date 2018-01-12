package jow.dis.eseo.fr.myapplication.modele;

/**
 * Created by Claire on 10/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jury {

    @SerializedName("idJury")
    @Expose
    private String idJury;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getIdJury() {
        return idJury;
    }

    public void setIdJury(String idJury) {
        this.idJury = idJury;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}