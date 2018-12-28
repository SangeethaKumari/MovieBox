package com.android.moviebox;

import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.moviebox.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<List<Movie>>{

    private TextView movieTitleValue;
    private TextView releaseDateValue;
    private TextView averageVotingValueValue;
    private TextView moviePlotSynopsisValue;
    private ImageView moviePosterValue;
    private String movieId;
    private TextView movieReviews;
    private  TextView noMovieTrailer;
    private Button favoriteMovieButton;

    private MovieViewModel movieViewModel;

    MovieAdapter movieAdapter ;
    public static final int MOVIES_DETAILS_LOADER_ID = 1;
    public static final int FETCH_MOVIES_TRAILERS = 2;
    public static final int FETCH_MOVIES_REVIEWS = 3;


    final static String IMAGE_SIZE_MOBILE = "w185";
    final static String IMAGE_BASE_URL =" http://image.tmdb.org/t/p/";

    /** URL for NEWs data from the USGS dataset */
    private static final String URL_BASE = "http://api.themoviedb.org/3/movie";
    // API_KEY to access MovieDb  API's
    private static final String API_KEY = BuildConfig.MovieBoxApiKeyHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

      final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movieId = intent.getStringExtra(getString(R.string.movie_id));

        NetworkInfo networkInfo = getActiveNetworkInfo();
        if( networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(FETCH_MOVIES_REVIEWS, null, this);
          loaderManager.initLoader(FETCH_MOVIES_TRAILERS, null, this);

        }

        populateUI(intent);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        final String isFavoriteMovie = intent.getStringExtra(getString(R.string.favorite_movie));

        favoriteMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movie = new Movie();
                movie.setTitle(intent.getStringExtra(getString(R.string.movie_title)));
                movie.setAverageVoting(intent.getStringExtra(getString(R.string.movie_vote_average)));
                movie.setMovieId(intent.getStringExtra(getString(R.string.movie_id)));
                movie.setReleaseDate(intent.getStringExtra(getString(R.string.movie_release_date)));
                movie.setPosterPath(intent.getStringExtra(getString(R.string.movie_poster)));
                movie.setPlotSynopsis(intent.getStringExtra(getString(R.string.movie_plot_synopsis)));

                if(isFavoriteMovie.equalsIgnoreCase("0")) {
                    //insert a row in database
                    movieViewModel.addFavoriteMovie(movie);
                    Toast.makeText(getApplicationContext(), R.string.add_movie_favorite_successful, Toast.LENGTH_SHORT).show();

                } else{
                    // remove the movie as favorite
                    movieViewModel.deleteFavoriteMovie(movie);
                    Toast.makeText(getApplicationContext(), R.string.ummarked_as_favorite_successful, Toast.LENGTH_SHORT).show();
                }
            finish();

            }
        });

    }

    /**
     * Checks the status of a network interface.
     * @return NetworkInfo object
     */
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo;
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * This method retrieves the values from the Sandwich model and
     * updates teh activity_detail view textView values.
     *
     * @param intent
     */
    private void populateUI(Intent intent) {

        movieTitleValue = findViewById(R.id.movieTitleValue);
        movieTitleValue.setText(intent.getStringExtra(getString(R.string.movie_title)));

        releaseDateValue = findViewById(R.id.movieReleaseDateValue);
        releaseDateValue.setText(intent.getStringExtra(getString(R.string.movie_release_date)));

        averageVotingValueValue = findViewById(R.id.movieVoteAverageValue);
        averageVotingValueValue.setText(intent.getStringExtra(getString(R.string.movie_vote_average)));

        moviePlotSynopsisValue = findViewById(R.id.moviePlotSynopsisValue);
        moviePlotSynopsisValue.setText(intent.getStringExtra(getString(R.string.movie_plot_synopsis)));

        moviePosterValue = findViewById(R.id.moviePosterValue);

        final String IMAGE_FINAL_URL =  IMAGE_BASE_URL+IMAGE_SIZE_MOBILE+intent.getStringExtra(getString(R.string.movie_poster));

        Picasso.with(this)
                .load(IMAGE_FINAL_URL.trim())
                .into(moviePosterValue);

        favoriteMovieButton = findViewById(R.id.movieFavorite);
        String isFavoriteMovie = intent.getStringExtra(getString(R.string.favorite_movie));

        if(isFavoriteMovie.equalsIgnoreCase("1")){
            favoriteMovieButton.setText(getString(R.string.favorite_movie));
            favoriteMovieButton.setBackgroundColor(Color.parseColor(getString(R.string.favorite_movie_color)));
            favoriteMovieButton.setTextColor(Color.WHITE);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(URL_BASE);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(movieId);
        if(i == FETCH_MOVIES_REVIEWS) {
            uriBuilder.appendPath("reviews");
        } else if (i == FETCH_MOVIES_TRAILERS){
            uriBuilder.appendPath("videos");

        }
        // Append query parameter and its value. For example, the section=games
        uriBuilder.appendQueryParameter("api_key",API_KEY);
        return new MoviesLoader(this,uriBuilder.toString(),i);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        int id = loader.getId();
        if(movies != null) {
            if(id == FETCH_MOVIES_REVIEWS) {
                movieReviews = findViewById(R.id.movieReviews);
                if ( movies != null && movies.size()!= 0 ) {
                    String[] movieReviewDetails = movies.get(0).getMovieReviews();
                    int review_count = movieReviewDetails.length;
                    int review_count_label = 0;
                    movieReviews.setText(++review_count_label + " : ");
                    for (int i = 0; i < review_count; i++) {
                        movieReviews.append(movieReviewDetails[i]);
                        movieReviews.append("\n");
                        movieReviews.append("\n");

                        if(i < review_count-1){
                            movieReviews.append(Integer.toString(++review_count_label) + " : ");
                        }
                    }
                } else {
                    movieReviews.setText(getString(R.string.movie_no_review));

                }
            } else if(id == FETCH_MOVIES_TRAILERS){
                //movieTrailers = findViewById(R.id.movieReviews);
                if ( movies != null && movies.size()!= 0 ) {
                    final String[] movieTrailerIds = movies.get(0).getMovieTrailerIds();
                    int trailer_count = movieTrailerIds.length;
                    int review_count_label = 0;
                    noMovieTrailer = findViewById(R.id.no_movie_trailer);
                    if(trailer_count!= 0) {
                        noMovieTrailer.setVisibility(View.GONE);
                        LinearLayout linearLayout = findViewById(R.id.add_trailers_ll);

                        for (int i =0;i< trailer_count;i++) {
                            // Create Button Dynamically
                            Button btnShow = new Button(this);
                            btnShow.setId(i);
                            int trailerCount = i+1;
                            final int index = i;
                            btnShow.setText("Play Trailer: "+trailerCount);
                            //btnShow.append(Integer.toString(++review_count_label));
                            btnShow.setLayoutParams(new LinearLayout.LayoutParams(
                              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            final String trailerUrl = "http://www.youtube.com/watch?v=" + movieTrailerIds[index];
                            btnShow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent
                                            (Intent.ACTION_VIEW,Uri.parse(trailerUrl)));

                                }
                            });

                            // Add Button to LinearLayout
                            if (linearLayout != null) {
                                linearLayout.addView(btnShow);
                            }
                        }
                    } else {
                        noMovieTrailer.setVisibility(View.VISIBLE);
                        noMovieTrailer.setText(getString(R.string.no_movie_trailer));
                    }
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        loader.reset();
    }
}
