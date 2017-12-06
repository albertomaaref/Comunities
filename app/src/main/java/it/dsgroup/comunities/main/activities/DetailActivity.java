package it.dsgroup.comunities.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import it.dsgroup.comunities.R;

public class DetailActivity extends AppCompatActivity {

    private String nomeGruppo;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toast.makeText(getApplicationContext(),"detail Activity",Toast.LENGTH_SHORT).show();

        Intent i = getIntent();
        nomeGruppo = i.getStringExtra("nomeGruppo");
        textView = findViewById(R.id.textView);
        textView.setText(nomeGruppo);

    }
}
