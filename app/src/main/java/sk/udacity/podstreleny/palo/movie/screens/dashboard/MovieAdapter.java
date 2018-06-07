package sk.udacity.podstreleny.palo.movie.screens.dashboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(movies == null){
            return 0;
        }else {
            return movies.size();
        }
    }

    public void swapData(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;

        MovieViewHolder(View view){
            super(view);
            mTitleTextView = view.findViewById(R.id.movie_title);
        }

        public void bind(int position){
            mTitleTextView.setText(movies.get(position).getTitle());
        }
    }

    interface MovieItemClickListener{
        void onClick(String id);
    }
}
