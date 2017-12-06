package it.dsgroup.comunities.main.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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
}
