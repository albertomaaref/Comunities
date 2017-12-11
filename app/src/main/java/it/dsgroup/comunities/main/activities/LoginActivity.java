package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.Comunity;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class LoginActivity extends AppCompatActivity implements TaskCompletetion {

    private EditText username;
    private EditText password;
    private ProgressDialog progressDialog;
    private Comunity comunityUtenteattivo;
    private Button bLogin;
    private TaskCompletetion delegato;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        bLogin = (Button) findViewById(R.id.bLogin);
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        delegato = this;
        // listener per il bottone del login
        View.OnClickListener temporary = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restCall(delegato);
            }
        };
        bLogin.setOnClickListener(temporary);

        comunityUtenteattivo = new Comunity();

        // fare il rest call per richamare la password



    }


    @Override
    public void tasksToDoAtCompletionStep(String result) {
        progressDialog.dismiss();
        progressDialog.cancel();
        if (result.toLowerCase().equals("error")){
            Toast.makeText(getApplicationContext(),"Utente inesistente",Toast.LENGTH_LONG).show();
        }

        else {
            if (result.equals(password.getText().toString())){
                // codice per andare all'attività della visualizzazione dei gruppi e salvo in locale questo utente
                //Toast.makeText(getApplicationContext(),"posso andare alla seconda attività",Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("utenteAttivo",username.getText().toString());
                editor.commit();
                Intent i = new Intent(getApplicationContext(),VisualizationActivity.class);
                i.putExtra("utenteAttivo",username.getText().toString());
                startActivity(i);
            }
            else {
                // nel caso la password è errata
                Toast.makeText(getApplicationContext()," password errata",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void restCall (final TaskCompletetion delegation){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.show();

        FireBaseConnection.get("utenti/"+username.getText().toString()+".json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                int as = s.length();
                if (s.equals("null")){
                    delegation.tasksToDoAtCompletionStep("error");
                }
                else {
                    String password = JasonParser.getPassword(s);
                    delegation.tasksToDoAtCompletionStep(password);
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String s = new String(responseBody);
                tasksToDoAtCompletionStep("error");
            }
        });

    }
}
