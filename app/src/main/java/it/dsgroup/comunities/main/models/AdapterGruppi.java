package it.dsgroup.comunities.main.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.activities.DetailActivity;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class AdapterGruppi extends RecyclerView.Adapter<AdapterGruppi.GruppiHolder> {

    private Context context;
    private Gruppi gruppi;

    public AdapterGruppi(ArrayList<String> groups, Context context) {
        this.gruppi = new Gruppi();
        this.gruppi.setGruppi(groups);
        this.context = context;


    }

    public static class GruppiHolder extends RecyclerView.ViewHolder{
        private TextView textGruppo;
        public GruppiHolder(View itemView) {
            super(itemView);
            textGruppo = (TextView) itemView.findViewById(R.id.bTextGroup);
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
        final String s = gruppi.getGruppi().get(position);
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
        return gruppi.getCountGruppi();
    }




}
