package it.dsgroup.comunities.main.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.dsgroup.comunities.R;

/**
 * Created by utente9.academy on 11/12/2017.
 */

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.PostsHolder> {

    private Context context;
    private ArrayList<Post> posts;

    public AdapterPosts (Context context, ArrayList<Post> posts){
        super();
        this.context = context;
        this.posts = posts;
    }

    public static class  PostsHolder extends RecyclerView.ViewHolder {

        private TextView titolo;
        private TextView autore;
        private TextView data;

        public PostsHolder(View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.tTitolo);
            autore = itemView.findViewById(R.id.tAutore);
            data = itemView.findViewById(R.id.tData);
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
        Post post = posts.get(position);
        holder.titolo.setText(post.getTitolo().toString());
        holder.autore.setText(post.getAutore().toString());
        holder.data.setText(post.getData().toString());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
