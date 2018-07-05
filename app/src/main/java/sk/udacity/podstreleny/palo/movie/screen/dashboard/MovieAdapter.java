package sk.udacity.podstreleny.palo.movie.screen.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.model.Movie;
import sk.udacity.podstreleny.palo.movie.screen.movieDetail.MovieDetail;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        } else {
            return movies.size();
        }
    }

    public void swapData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_title)
        TextView mTitleTextView;

        @BindView(R.id.movie_photo)
        ImageView mMovieImage;

        @BindView(R.id.rating_tv)
        TextView mRating;

        MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mMovieImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieDetail.class);
            intent.putExtra(Intent.EXTRA_TEXT,movies.get(getAdapterPosition()));
            context.startActivity(intent);
        }

        public void bind(int position) {
            Movie movie = movies.get(position);
            mTitleTextView.setText(movie.getTitle());
            mRating.setText(String.valueOf(movie.getVote_average()));
            Glide.with(context).load(IMAGE_BASE_URL + movie.getPoster_path())
                    .into(mMovieImage);

        }
    }

}
