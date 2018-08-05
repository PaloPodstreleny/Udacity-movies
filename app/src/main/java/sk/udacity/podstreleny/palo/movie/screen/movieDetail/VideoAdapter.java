package sk.udacity.podstreleny.palo.movie.screen.movieDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.udacity.podstreleny.palo.movie.R;
import sk.udacity.podstreleny.palo.movie.db.entity.Video;
import sk.udacity.podstreleny.palo.movie.util.MovieUrlUtil;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    private List<Video> videoList;
    private Context context;

    public VideoAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(videoList == null){
            return 0;
        }
        return videoList.size();
    }

    public void setVideoList(List<Video> videoList){
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.parentCLayout)
        LinearLayout constraintLayout;

        @BindView(R.id.videoName)
        TextView videoName;

        @BindView(R.id.movieType)
        TextView movieType;


        public VideoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final String path = videoList.get(getAdapterPosition()).getWatchKey();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MovieUrlUtil.BASE_VIDEO_URL +path ));
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(intent);
            }
        }

        public void bind(int position) {
            videoName.setText(videoList.get(position).getVideoName());
            movieType.setText(videoList.get(position).getVideoType());
        }

    }
}
