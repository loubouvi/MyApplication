package jow.dis.eseo.fr.myapplication.modele;

/**
 * Created by Claire on 10/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supervisor {

    @SerializedName("forename")
    @Expose
    private String forename;
    @SerializedName("surname")
    @Expose
    private String surname;

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
