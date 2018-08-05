package sk.udacity.podstreleny.palo.movie.screen.movieDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.db.entity.Review;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private List<Review> reviews;

    public ReviewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public void setData(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.parentReview)
        ConstraintLayout parentReview;

        @BindView(R.id.author)
        TextView author;

        @BindView(R.id.review)
        TextView review;

        @BindView(R.id.read_more)
        Button button;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            button.setOnClickListener(this);
            parentReview.setOnClickListener(this);

        }

        public void bind(int position) {
            author.setText(reviews.get(position).getAuthor());
            review.setText(reviews.get(position).getContent());
        }

        @Override
        public void onClick(View view) {
            Uri webpage = Uri.parse(MovieUrlUtil.BASE_REVIEW_URL + reviews.get(getAdapterPosition()).getId());
            Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(intent);
            }

        }
    }


}