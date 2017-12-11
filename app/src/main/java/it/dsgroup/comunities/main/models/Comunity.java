package it.dsgroup.comunities.main.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class Comunity implements Serializable {
    private ArrayList<String> gruppi;

    public ArrayList<Post> getPosti() {
        return posti;
    }

    public void setPosti(ArrayList<Post> posti) {
        this.posti = posti;
    }

    private ArrayList<Post> posti;

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

    public Post getPostByTitolo (String titolo){
        for (Post post: this.posti
             ) {
            if (post.getTitolo().equals(titolo)){
                return post;
            }
        }

        return null;
    }

    public Post getPostById (String id){
        for (Post post: this.posti
             ) {
            if (post.getId().equals(id)){
                return post;
            }
        }
        return null;
    }
}
