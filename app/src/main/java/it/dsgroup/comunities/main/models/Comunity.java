package it.dsgroup.comunities.main.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class Comunity implements Serializable {
    private ArrayList<String> gruppi;

    public Comunity() {
        this.gruppi = new ArrayList<>();
    }

    public ArrayList<String> getGruppi() {
        return gruppi;
    }

    public void setGruppi(ArrayList<String> gruppi) {
        this.gruppi = gruppi;
    }

    public int getCountGruppi (){
        return this.gruppi.size();
    }
}
