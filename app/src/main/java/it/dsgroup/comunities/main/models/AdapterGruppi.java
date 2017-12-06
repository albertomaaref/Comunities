package it.dsgroup.comunities.main.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.dsgroup.comunities.R;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class AdapterGruppi extends RecyclerView.Adapter<AdapterGruppi.GruppiHolder> {


    private Gruppi gruppi;

    public AdapterGruppi(ArrayList<String> groups) {
        this.gruppi = new Gruppi();
        this.gruppi.setGruppi(groups);


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
    public void onBindViewHolder(GruppiHolder holder, int position) {
        String s = gruppi.getGruppi().get(position);
        holder.textGruppo.setText(s);

    }

    @Override
    public int getItemCount() {
        return gruppi.getCountGruppi();
    }




}
