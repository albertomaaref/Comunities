package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.Gruppi;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class LoginActivity extends AppCompatActivity implements TaskCompletetion {

    private EditText username;
    private EditText password;
    private ProgressDialog progressDialog;
    private Gruppi gruppiUtenteattivo;
    private Button bLogin;
    private TaskCompletetion delegato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        bLogin = (Button) findViewById(R.id.bLogin);

        // listener per il bottone del login
        View.OnClickListener temporary = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restCall(delegato);
            }
        };
        bLogin.setOnClickListener(temporary);

        gruppiUtenteattivo = new Gruppi();

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
                // codice per andare all'attività della visualizzazione dei gruppi
                Toast.makeText(getApplicationContext(),"posso andare alla seconda attività",Toast.LENGTH_LONG).show();
            }
            else {
                // nel caso la password è errata
                Toast.makeText(getApplicationContext()," password errata",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void restCall (final TaskCompletetion delegation){
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();

        FireBaseConnection.get("utenti/"+username.getText().toString()+".json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                String password = JasonParser.getPassword(s);
                tasksToDoAtCompletionStep(password);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String s = new String(responseBody);
                tasksToDoAtCompletionStep("error");
            }
        });

    }
}
