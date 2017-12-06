package it.dsgroup.comunities.main.models;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class Post {
    private String titolo;
    private String autore;
    private String data;

    public Post() {
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
