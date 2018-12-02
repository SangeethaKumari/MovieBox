package com.android.moviebox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by sangeetha_gsk on 10/17/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    final static String IMAGE_SIZE_MOBILE = "w185";
    final static String IMAGE_BASE_URL =" http://image.tmdb.org/t/p/";

    private Context mContext;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.mContext = context;
        this.movieList = movieList;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_movie,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {

        final String IMAGE_FINAL_URL =  IMAGE_BASE_URL+IMAGE_SIZE_MOBILE+movieList.get(position).getPosterPath();

        Picasso.with(mContext).load(IMAGE_FINAL_URL.trim()).into(holder.movie_image_id);

        final String movieTitle = mContext.getString(R.string.movie_title);
        final String averageVoting = mContext.getString(R.string.movie_vote_average);
        final String moviePosterPath = mContext.getString(R.string.movie_poster);
        final String releaseDate = mContext.getString(R.string.movie_release_date);
        final String plotSynopsis = mContext.getString(R.string.movie_plot_synopsis);
        final String movieId = mContext.getString(R.string.movie_id);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,MovieActivity.class);
                // passing data to the movie activity
                intent.putExtra(movieTitle,movieList.get(position).getTitle());
                intent.putExtra(moviePosterPath,IMAGE_FINAL_URL);
                intent.putExtra(averageVoting,movieList.get(position).getAverageVoting());
                intent.putExtra(releaseDate,movieList.get(position).getReleaseDate());
                intent.putExtra(plotSynopsis,movieList.get(position).getPlotSynopsis());
                intent.putExtra(movieId,movieList.get(position).getMovieId());

                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    class MovieHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView movie_image_id;
        CardView cardView;

        public MovieHolder(View itemView) {
            super(itemView);
            movie_image_id = itemView.findViewById(R.id.movie_image_id);
            cardView = itemView.findViewById(R.id.cardview_id);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
        }
    }

}
