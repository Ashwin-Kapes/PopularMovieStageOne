package com.example.android.popularmoviestageone;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmoviestageone.adapters.MoviesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    public Movie[] mMovie;
    private String mSort = "";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mSort.isEmpty()) {
            mSort = "popular";
        }
        getMovies(mSort);
    }

    private void getMovies(String sort) {
        String apiKey = getString(R.string.api_key);

        String movieDbUrl = "";
        String movieDbUrlRating = getString(R.string.rating_api) + apiKey;
        String movieDbUrlPopular = getString(R.string.popular_api) + apiKey;
        if (sort.equals("popular")) {
            movieDbUrl = movieDbUrlPopular;
        }
        if (sort.equals("rating")) {
            movieDbUrl = movieDbUrlRating;
        }

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(movieDbUrl)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAbouterror();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            try {
                                mMovie = setMovies(jsonData);
                                Log.e("JSONDATA","MainResponse: "+ jsonData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAbouterror();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, getString(R.string.exception), e);
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, R.string.network_unavailable_message, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        boolean isNetworkAvailable = false;

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = true;
        }

        return isNetworkAvailable;

    }

    private void updateDisplay() {
        GridView listView = (GridView) findViewById(R.id.grid_view);
        com.example.android.popularmoviestageone.adapters.MoviesAdapter adapter = new com.example.android.popularmoviestageone.adapters.MoviesAdapter(this, mMovie);
        listView.setAdapter(adapter);
        listView.invalidateViews();
    }

    private Movie[] setMovies(String jsonData) throws JSONException {
        JSONObject movieJson = new JSONObject(jsonData);
        JSONArray moviesArray = movieJson.getJSONArray("results");
        Movie[] movies = new Movie[moviesArray.length()];
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject jsonMovie = moviesArray.getJSONObject(i);
            Movie movie = new Movie();

            movie.setTitle(jsonMovie.getString("original_title"));
            movie.setPosterPath(jsonMovie.getString("poster_path"));
            movie.setSummary(jsonMovie.getString("overview"));
            movie.setReleaseDate(jsonMovie.getString("release_date"));
            movie.setBackgroundPath(jsonMovie.getString("poster_path"));
            movie.setUserRating(jsonMovie.getString("vote_average"));
            movies[i] = movie;
        }
        return movies;
    }


    private void alertUserAbouterror() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            if (!mSort.equals("popular")) {
                mSort = "popular";
                setContentView(R.layout.activity_main);
                getMovies(mSort);
            }

        }
        if (id == R.id.action_rating) {
            if (!mSort.equals("rating")) {
                mSort = "rating";
                setContentView(R.layout.activity_main);
                getMovies(mSort);
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
