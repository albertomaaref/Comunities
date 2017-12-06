package it.dsgroup.comunities.main.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import it.dsgroup.comunities.main.models.Post;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class JasonParser {



    public static String getPassword(String url){
        String password = "";
        try {
            JSONObject jsonObject = new JSONObject(url);
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                if (key.toLowerCase().equals("password")){
                    password = jsonObject.getString(key);
                    break;
                }

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return password;
    }

    public static ArrayList<String> getGruppi (String url){
        ArrayList<String> lista = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(url);
            Iterator keys = jsonObject.keys();

            while (keys.hasNext()){
                String key = (String) keys.next();
                JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                Iterator chiavi = jsonObject1.keys();
                while (chiavi.hasNext()){
                    String chiave = (String) chiavi.next();
                    if (chiave.toLowerCase().equals("id"));
                    lista.add(jsonObject1.getString(chiave).toString());
                }
            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return lista;
    }


    //https://comunities-bc5e8.firebaseio.com/gruppi/acmilan/posti
    public static ArrayList<Post> getPosti (String url){
        ArrayList<Post> posts = new ArrayList<>();

        try {
            JSONObject gruppo = new JSONObject(url);
            Iterator keys = gruppo.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                JSONObject postiPubblicati = gruppo.getJSONObject(key);
                Iterator chiavi = postiPubblicati.keys();

                while (chiavi.hasNext()){
                    String chiave = (String) chiavi.next();
                    JSONObject singoloPost= postiPubblicati.getJSONObject(chiave);
                    Iterator clefs = singoloPost.keys();
                    Post post = new Post();
                    while (clefs.hasNext()){
                        String clef = (String) clefs.next();
                        if (clef.toLowerCase().equals("titolo")) post.setTitolo(singoloPost.getString(clef));
                        if (clef.toLowerCase().equals("autore")) post.setAutore(singoloPost.getString(clef));
                        if (clef.toLowerCase().equals("data")) post.setData(singoloPost.getString(clef));
                    }
                    posts.add(post);

                }



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
