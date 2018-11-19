package com.android.moviebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    private TextView movieTitleValue;
    private TextView releaseDateValue;
    private TextView averageVotingValueValue;
    private TextView moviePlotSynopsisValue;
    private ImageView moviePosterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        populateUI(intent);
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

        Picasso.with(this)
                .load(intent.getStringExtra(getString(R.string.movie_poster)).trim())
                .into(moviePosterValue);

    }

}
