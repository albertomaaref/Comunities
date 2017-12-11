package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.AdapterPosts;
import it.dsgroup.comunities.main.models.Post;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class DetailActivity extends AppCompatActivity implements TaskCompletetion {

    private String nomeGruppo;
    private TextView tNomeGruppo;
    private ProgressDialog progressDialog;
    private ArrayList<Post> posti;
    private TaskCompletetion delegato;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewPosts;
    private AdapterPosts adapterPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toast.makeText(getApplicationContext(),"detail Activity",Toast.LENGTH_SHORT).show();


        recyclerViewPosts = findViewById(R.id.RecyclerPosts);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        Intent i = getIntent();
        nomeGruppo = i.getStringExtra("nomeGruppo");
        tNomeGruppo = findViewById(R.id.tNomeGruppo);
        tNomeGruppo.setText(nomeGruppo);
        posti = new ArrayList<>();
        delegato = this;
        restCallPosts(delegato);


    }

    public void restCallPosts (final TaskCompletetion delegation){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.show();

        FireBaseConnection.get("gruppi/" + nomeGruppo+".json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String (responseBody);
                posti = JasonParser.getPosti(s);
                delegation.tasksToDoAtCompletionStep("success");
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
        if (result.toLowerCase().equals("error")) {
            Toast.makeText(getApplicationContext(),"errore caricamento post",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            adapterPosts = new AdapterPosts(DetailActivity.this,posti);
            recyclerViewPosts.setLayoutManager(layoutManager);
            recyclerViewPosts.setAdapter(adapterPosts);
        }

    }
}
