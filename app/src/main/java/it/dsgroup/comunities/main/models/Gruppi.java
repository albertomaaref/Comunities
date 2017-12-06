package it.dsgroup.comunities.main.models;

import java.util.ArrayList;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class Gruppi {
    private ArrayList<String> gruppi;

    public Gruppi() {
        this.gruppi = new ArrayList<>();
    }

    public ArrayList<String> getGruppi() {
        return gruppi;
    }

    public void setGruppi(ArrayList<String> gruppi) {
        this.gruppi = gruppi;
    }
}
