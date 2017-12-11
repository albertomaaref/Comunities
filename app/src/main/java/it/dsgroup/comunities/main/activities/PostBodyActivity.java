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
import it.dsgroup.comunities.main.models.Comunity;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.InternalStorage;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class PostBodyActivity extends AppCompatActivity implements TaskCompletetion {

    private Comunity comunity;
    private TextView tTitoloBis;
    private TextView tAutoreBis;
    private TextView tDataBis;
    private TextView tBoddy;
    private TaskCompletetion delgation;
    private ProgressDialog progressDialog;
    private String nomeGruppoAttuale;
    private String body="";
    private String  idPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_body);

        tTitoloBis = findViewById(R.id.tTitoloBis);
        tAutoreBis = findViewById(R.id.tAutoreBis);
        tDataBis = findViewById(R.id.tDataBis);
        tBoddy = (TextView) findViewById(R.id.tCorpoPost);
        delgation = this;


        Intent i = getIntent();
        idPost= (String) i.getStringExtra("idPost");
        comunity = (Comunity) InternalStorage.readObject(getApplicationContext(),"comunity");
        nomeGruppoAttuale = (String) InternalStorage.readObject(getApplicationContext(),"previousGroup");
        //Toast.makeText(getApplicationContext(),idPost,Toast.LENGTH_LONG).show();

        // faccio la chiamata rest per prelevare il body del post
        restCallForBodyPost(delgation,idPost);




    }

    public void restCallForBodyPost(final TaskCompletetion delegato, String idPost){
        progressDialog = new ProgressDialog(PostBodyActivity.this);
        progressDialog.show();
        String url = "gruppi/"+nomeGruppoAttuale+"/posti/"+idPost+".json";
        FireBaseConnection.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = (String) new String(responseBody);
                if (s.equals("null")){
                    delegato.tasksToDoAtCompletionStep("error");
                }
                else {
                    body = JasonParser.getBodyPost(s);
                    delegato.tasksToDoAtCompletionStep("success");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    @Override
    public void tasksToDoAtCompletionStep(String result) {
        progressDialog.dismiss();
        progressDialog.cancel();
        if (result.equals("error")) {
            Toast.makeText(getApplicationContext(),"errore",Toast.LENGTH_LONG).show();
        }
        else {
            //Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
            comunity.getPostById(idPost).setCorpo(body);
            tBoddy.setText(body);
            tDataBis.setText(comunity.getPostById(idPost).getData());
            tTitoloBis.setText(comunity.getPostById(idPost).getTitolo());
            tAutoreBis.setText(comunity.getPostById(idPost).getAutore());


        }

    }
}
