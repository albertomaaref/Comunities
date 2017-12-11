package it.dsgroup.comunities.main.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.activities.DetailActivity;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class AdapterGruppi extends RecyclerView.Adapter<AdapterGruppi.GruppiHolder> {

    private Context context;
    private Comunity comunity;

    public AdapterGruppi(ArrayList<String> groups, Context context) {
        this.comunity = new Comunity();
        this.comunity.setGruppi(groups);
        this.context = context;


    }

    public static class GruppiHolder extends RecyclerView.ViewHolder{
        private Button textGruppo;
        public GruppiHolder(View itemView) {
            super(itemView);
            textGruppo =  itemView.findViewById(R.id.bTextGroup);
        }
    }

    @Override
    public GruppiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        GruppiHolder gh = new GruppiHolder(v);
        return gh;
    }

    @Override
    public void onBindViewHolder(final GruppiHolder holder, int position) {
        final String s = comunity.getGruppi().get(position);
        holder.textGruppo.setText(s);
        holder.textGruppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("nomeGruppo",s);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return comunity.getCountGruppi();
    }




}
