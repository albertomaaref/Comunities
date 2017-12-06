package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.Gruppi;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class VisualizationActivity extends AppCompatActivity implements TaskCompletetion{

    private String utenteAttivo;
    private TextView prova;
    private TaskCompletetion delegato;
    private ProgressDialog progressDialog;
    private Gruppi gruppi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        Intent i = getIntent();
        utenteAttivo = i.getStringExtra("utenteAttivo");
        gruppi = new Gruppi();
        delegato = this;

        restCallGruppi(delegato);
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
                    gruppi.setGruppi(JasonParser.getGruppi(s));
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
            Toast.makeText(getApplicationContext(),"error nel caricamento dei gruppi",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"SONO QUI",Toast.LENGTH_LONG).show();
        }

    }
}
