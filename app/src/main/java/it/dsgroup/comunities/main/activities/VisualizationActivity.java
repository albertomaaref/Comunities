package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.AdapterGruppi;
import it.dsgroup.comunities.main.models.Comunity;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.InternalStorage;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class VisualizationActivity extends AppCompatActivity implements TaskCompletetion{

    private AdapterGruppi adapterGruppi;
    private String utenteAttivo;
    private TextView prova;
    private TaskCompletetion delegato;
    private ProgressDialog progressDialog;
    private Comunity comunity;
    private RecyclerView recyclerView;
    private LinearLayoutManager lm;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        Intent i = getIntent();
        utenteAttivo = i.getStringExtra("utenteAttivo");
        //comunity = new Comunity();
        delegato = this;
        // controllo se in locale c'è già la comunity dell'utente attivo
        comunity = (Comunity) InternalStorage.readObject(getApplicationContext(),"comunity");
        if (comunity == null){
            comunity = new Comunity();
            restCallGruppi(delegato);
        }
        else {
            setRecyclerView();
        }



    }

    public void restCallGruppi (final TaskCompletetion delegation){
        progressDialog = new ProgressDialog(VisualizationActivity.this);
        progressDialog.show();

        FireBaseConnection.get("utenti/"+utenteAttivo+"/gruppi.json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                if (s.equals("null")){
                    delegation.tasksToDoAtCompletionStep("error");

                }
                else {
                    comunity.setGruppi(JasonParser.getGruppi(s));
                    delegation.tasksToDoAtCompletionStep("success");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegation.tasksToDoAtCompletionStep("error");
            }
        });

    }

    @Override
    public void tasksToDoAtCompletionStep(String result) {
        progressDialog.dismiss();
        progressDialog.cancel();

        if (result.toLowerCase().equals("error")){
            Toast.makeText(getApplicationContext(),"error nel caricamento dei comunity",Toast.LENGTH_LONG).show();
        }
        else {
            //Toast.makeText(getApplicationContext(),"SONO QUI",Toast.LENGTH_LONG).show();

            // salvo la comunity in locale
            InternalStorage.writeObject(getApplicationContext(),"comunity",comunity);
            // setto la recycler
            setRecyclerView();

        }

    }

    public void setRecyclerView (){
        recyclerView = findViewById(R.id.recyclerGruppi);
        lm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lm);
        adapterGruppi = new AdapterGruppi(comunity.getGruppi(),this);
        recyclerView.setAdapter(adapterGruppi);
    }
}
