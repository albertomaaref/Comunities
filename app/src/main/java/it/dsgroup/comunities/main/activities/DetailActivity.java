package it.dsgroup.comunities.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.models.AdapterPosts;
import it.dsgroup.comunities.main.models.Comunity;
import it.dsgroup.comunities.main.utilities.DataUtilities;
import it.dsgroup.comunities.main.utilities.FireBaseConnection;
import it.dsgroup.comunities.main.utilities.InternalStorage;
import it.dsgroup.comunities.main.utilities.JasonParser;
import it.dsgroup.comunities.main.utilities.TaskCompletetion;

public class DetailActivity extends AppCompatActivity implements TaskCompletetion,SwipeRefreshLayout.OnRefreshListener {

    private String nomeGruppo;
    private TextView tNomeGruppo;
    private ProgressDialog progressDialog;
    private String previousGroup="";
    private Comunity comunity;
    private TaskCompletetion delegato;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewPosts;
    private AdapterPosts adapterPosts;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toast.makeText(getApplicationContext(),"detail Activity",Toast.LENGTH_SHORT).show();

        swipeRefreshLayout = findViewById(R.id.swipeRefrech);
        recyclerViewPosts = findViewById(R.id.RecyclerPosts);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        Intent i = getIntent();
        nomeGruppo = i.getStringExtra("nomeGruppo");
        tNomeGruppo = findViewById(R.id.tNomeGruppo);
        tNomeGruppo.setText(nomeGruppo);

        delegato = this;
        comunity = (Comunity) InternalStorage.readObject(DetailActivity.this,"comunity");
        previousGroup = (String) InternalStorage.readObject(DetailActivity.this,"previousGroup");
        boolean isEmpty = nomeGruppo.equals(previousGroup);
        if (comunity.getPosti() == null || !isEmpty || comunity.getPosti().size()==0){
            restCallPosts(delegato);
        }
        else {
            setRecyclerPosts();
        }
        // controllo se c'è ultima data di modifica dei post
        if (comunity.getLastModificationDate().equals(null)){
            comunity.setLastModificationDate(Calendar.getInstance().getTime());
        }

        swipeRefreshLayout.setOnRefreshListener(temporary2);



    }

    public void restCallPosts (final TaskCompletetion delegation){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.show();

        FireBaseConnection.get("gruppi/" + nomeGruppo+".json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String (responseBody);
                if (s.equals("null")){
                    delegation.tasksToDoAtCompletionStep("error");
                }
                else {
                    comunity.setPosti(JasonParser.getPosti(s));
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
        if (result.toLowerCase().equals("error")) {
            Toast.makeText(getApplicationContext(),"errore caricamento post, è possibile che questo gruppo non abbia post",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            // salvo la comunity con i post dell'utente attivo in locale
            InternalStorage.writeObject(getApplicationContext(),"comunity",comunity);
            // scrivo il nome del gruppo in locale
            InternalStorage.writeObject(getApplicationContext(),"previousGroup",nomeGruppo);
            setRecyclerPosts();
        }

    }

    @Override
    public void tasksToDoAtCompletionStep(String result, String date) {
        Date parsedDate = DataUtilities.convertStringToDate(date);
        if (parsedDate.after(comunity.getLastModificationDate())){
            restCallPosts(delegato);
        }
    }



    public void setRecyclerPosts() {
        // ulteriore controllo se la chiamata rest mi restituisce un nullo
        if (comunity.getPosti() == null){
            Toast.makeText(getApplicationContext(),"Lista dei post vuota",Toast.LENGTH_SHORT).show();
        }
        else {
            adapterPosts = new AdapterPosts(DetailActivity.this,comunity);
            recyclerViewPosts.setLayoutManager(layoutManager);
            recyclerViewPosts.setAdapter(adapterPosts);
        }

    }

    @Override
    public void onRefresh() {

    }

    SwipeRefreshLayout.OnRefreshListener temporary2 = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updateLista();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    public void updateLista (){
        restCallLastDate(delegato);
    }

    public void restCallLastDate(final TaskCompletetion delegation){
        String url="";
        url="gruppi/"+nomeGruppo+".json";
        FireBaseConnection.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                if (s.equals("null")){
                    delegation.tasksToDoAtCompletionStep("calendare extraction error");
                }
                else {
                    String data = JasonParser.getData(s);
                    delegation.tasksToDoAtCompletionStep("success", data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
