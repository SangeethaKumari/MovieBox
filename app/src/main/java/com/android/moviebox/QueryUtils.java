package com.android.moviebox;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sangeetha_gsk on 10/27/18.
 */

public class QueryUtils {

    private static final String LOG_TAG = "QueryUtils";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Movie> moviesList = extractFeatureFromJson(jsonResponse);

        // Return the list of movies
        return moviesList;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error while connection,url connection response code: "
                        + urlConnection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem retrieving the movies JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movie objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Movie> extractFeatureFromJson(String moviesJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding movie to the moviesList
        List<Movie> moviesList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(moviesJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of movies
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");
            // For each movie in the moviesArray, create an {@link Movie} object
            for (int i = 0; i < moviesArray.length(); i++) {
                // Get a single movie at position i within the list of movies
                JSONObject currentMovie = moviesArray.getJSONObject(i);

                // Extract the value for the key called "movie title"
                String movieTitle = currentMovie.getString("title");
                movieTitle = isValueEmpty(movieTitle);
                // Extract the value for the key called "overview"
                String movieOverview = currentMovie.getString("overview");
                movieOverview = isValueEmpty(movieOverview);

                // Extract the value for the key called "release_date"
                String releaseDate = currentMovie.getString("release_date");

                String formattedDate = convertDate(releaseDate);

                // Extract the value for the key called "vote_average"
                String averageVote = currentMovie.getString("vote_average");
                averageVote = isValueEmpty(averageVote);

                // Extract the value for the key called "poster_path"
                String posterPath = currentMovie.getString("poster_path");

                // Extract the value for the key called "backdrop_path"
                String backdropPath = currentMovie.getString("backdrop_path");

                // Create a new {@link Movie} object with the magnitude, location, time,
                // and url from the JSON response.
                Movie movies = new Movie(movieTitle,formattedDate,posterPath, backdropPath, averageVote,movieOverview);
                // Add the new {@link Movie} to the list of movies.
                moviesList.add(movies);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movies JSON results", e);
        }
        // Return the list of movies
        return moviesList;
    }

    /**
     * This method checks whether the value is empty or null.
     * Return N/A if the value is empty or null.
     * @param dataValue
     * @return
     */
    private static String isValueEmpty(String dataValue) {
        String defaultValue = "N/A";
        if(dataValue==null || dataValue.isEmpty()){
            return  defaultValue;
        }
        return dataValue;
    }

    /**
     * This method parses the releaseDate
     * @param releaseDate
     * @return the date in the pattern d MMM yyyy.
     */
    private static String convertDate(String releaseDate){
        Date date1 = null;
        if(releaseDate == null || releaseDate.equals("")){
            return "N/A";
        }
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            date1 = sdfSource.parse(releaseDate);
        }
        catch (ParseException e)
        {
            Log.e("QueryUtils", "Problem parsing the date", e);
        }
        SimpleDateFormat sdfDestination = new SimpleDateFormat("d MMM yyyy");
        String formattedDate = sdfDestination.format(date1);
        return formattedDate;

    }
    
}
