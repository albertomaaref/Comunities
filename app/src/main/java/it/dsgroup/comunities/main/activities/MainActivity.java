package it.dsgroup.comunities.main.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import it.dsgroup.comunities.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private PreferenceManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String utenteAttivo = prefs.getString("utenteAttivo","");

        if(utenteAttivo.equals("")){
            // vado all'activity della login
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);

        }

        else {
            // vado alla activity della visualizzazione dei gruppi
            Intent i = new Intent(getApplicationContext(),VisualizationActivity.class);
            i.putExtra("utenteAttivo",utenteAttivo);
            startActivity(i);
        }
    }
}
