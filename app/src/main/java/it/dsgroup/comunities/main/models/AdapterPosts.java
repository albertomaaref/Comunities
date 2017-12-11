package it.dsgroup.comunities.main.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.dsgroup.comunities.R;
import it.dsgroup.comunities.main.activities.PostBodyActivity;

/**
 * Created by utente9.academy on 11/12/2017.
 */

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.PostsHolder> {

    private Context context;
    private Comunity comunity;


    public AdapterPosts (Context context, Comunity comunity){
        super();
        this.context = context;
        this.comunity = comunity;
    }

    public static class  PostsHolder extends RecyclerView.ViewHolder {

        private TextView titolo;
        private TextView autore;
        private TextView data;
        private CardView cardView;

        public PostsHolder(View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.tTitolo);
            autore = itemView.findViewById(R.id.tAutore);
            data = itemView.findViewById(R.id.tData);
            cardView = itemView.findViewById(R.id.postCard);


        }
    }

    @Override
    public PostsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card,parent,false);
        PostsHolder postsHolder = new PostsHolder(view);
        return postsHolder;
    }

    @Override
    public void onBindViewHolder(PostsHolder holder, int position) {
        Post post = comunity.getPosti().get(position);
        holder.titolo.setText(post.getTitolo().toString());
        holder.autore.setText(post.getAutore().toString());
        holder.data.setText(post.getData().toString());
        holder.cardView.setOnClickListener(temporary);



    }

    @Override
    public int getItemCount() {
        return comunity.getPosti().size();
    }

    View.OnClickListener temporary = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           TextView textView = v.findViewById(R.id.tTitolo);

           //Toast.makeText(context,textView.getText(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, PostBodyActivity.class);
            String idPost="";
            idPost=comunity.getPostByTitolo(textView.getText().toString()).getId();
            i.putExtra("idPost",idPost);
            context.startActivity(i);
        }
    };


}
