package com.android.moviebox;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.moviebox.viewmodel.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<List<Movie>> {

    MovieAdapter movieAdapter ;
    public static final int MOVIES_LOADER_ID = 1;
    public static final int FETCH_MOVIES_DETAILS = 1;
    public static final int FETCH_FAVORITE_MOVIES_DETAILS = 4;



    /** URL for NEWs data from the USGS dataset */
    private static final String URL_BASE = "http://api.themoviedb.org/3/movie";
    // API_KEY to access MovieDb  API's
    private static final String API_KEY = BuildConfig.MovieBoxApiKeyHolder;
    TextView mEmptyStateTextView = null;

    private MovieViewModel movieViewModel;
    String orderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

       /* movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable final List<Movie> movies) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });*/

        NetworkInfo networkInfo = getActiveNetworkInfo();
        if( networkInfo != null && networkInfo.isConnected()){

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIES_LOADER_ID, null, this);
        } else {
            // Set empty state text to display "No internet connection."
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            mEmptyStateTextView.setVisibility(View.VISIBLE);

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
        }
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

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(URL_BASE);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        uriBuilder.appendPath(orderBy);
        // Append query parameter and its value. For example, the section=games
        uriBuilder.appendQueryParameter("api_key",API_KEY);
        if(orderBy.equalsIgnoreCase("favorite")){
            return new MoviesLoader(this,uriBuilder.toString(),FETCH_FAVORITE_MOVIES_DETAILS);
        }
        return new MoviesLoader(this,uriBuilder.toString(),FETCH_MOVIES_DETAILS);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if(movies != null) {
           try {
               movieAdapter = new MovieAdapter(this, movies);
               //set the adapter value
               RecyclerView myrv = (RecyclerView) findViewById(R.id.rv_movie);
               myrv.setLayoutManager(new GridLayoutManager(this, 3));
               myrv.setAdapter(movieAdapter);

               if(orderBy.equalsIgnoreCase("favorite")) {
                    setupMovieViewModel();
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }else{
            // Set empty state text to display "No Movie data found."
            mEmptyStateTextView.setText(R.string.detail_error_message);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        loader.reset();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupMovieViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> favoriteMovies) {
                movieAdapter.setFavoriteMovies(favoriteMovies);
            }
        });
    }
}
