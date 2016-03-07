package com.example.rajpa.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String LOG_TAG = "Popular Movies";
    MovieAdapter madapter;
    ArrayList<Movie> movies;
    String sort_type = "popularity";
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie(sort_type);

    }

    public void updateMovie(String sort_type){

        PopulateMovie populateMovie = new PopulateMovie();
        populateMovie.execute(sort_type);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sort_by_pop){
            sort_type="popularity";
            updateMovie(sort_type);
            return  true;
        }
        if(item.getItemId()==R.id.sort_by_rate){
            sort_type="vote_average";
            updateMovie(sort_type);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movies = new ArrayList<>();
        madapter = new MovieAdapter(getActivity(),R.layout.grid_item_movies, new ArrayList<Movie>());
        View rootView = inflater.inflate(R.layout.fragment_main, container ,false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grids);
        gridView.setAdapter(madapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie_item = (Movie) parent.getItemAtPosition(position);
                Intent i = new Intent(getContext() , Movie_Details.class);
                i.putExtra("name", movie_item.getTitle());
                i.putExtra("url",movie_item.image_url);
                i.putExtra("ratings",movie_item.getRatings());
                i.putExtra("release_date",movie_item.getRelease_date());
                i.putExtra("overview",movie_item.getOverview());
                startActivity(i);
            }
        });

        updateMovie(sort_type);
        return rootView;


    }

    public class PopulateMovie extends AsyncTask<String,Void,Movie[]> {
        //http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=
        HttpURLConnection urlConnection;
        BufferedReader bufferedReader;
        String movies_detail=null;
        @Override
        protected Movie[] doInBackground(String... params) {
            final String BASE_URL="http://api.themoviedb.org/3";
            final String DISCOVER="/discover";
            final String BY_MOVIE="/movie";
            final String SORT_BY="?sort_by="+params[0]+".desc";
            final String SORT_BY_POP="?sort_by=popularity.desc";
            final String SORT_BY_RATINGS="?sort_by=vote_average.desc";
            final String API_KEY="YOUR_API_KEY";
            String path=BASE_URL+DISCOVER+BY_MOVIE+SORT_BY+API_KEY;

            try{
                URL url=new URL(path);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                if(inputStream==null){
                    return  null;
                }
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer=new StringBuffer();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line);
                }
                if(buffer.length()==0){
                    Log.d(LOG_TAG, "No fetched the buffer");
                    return null;
                }
                movies_detail=buffer.toString();
            }catch(Exception e){
                Log.e(LOG_TAG,e.getMessage());
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                try{
                    if(bufferedReader!=null){
                        bufferedReader.close();
                    }
                }catch (Exception e){
                    Log.e(LOG_TAG,"reader didnt close properly");
                }
            }
            return getMovieData(movies_detail);
        }
        private Movie[] getMovieData(String movies_detail) {
            final String RESULTS = "results";
            final String TITLE = "original_title";
            final String OVER_VIEW = "overview";
            final String POSTER_PATH = "poster_path";
            final String RELEASE_DATE = "release_date";
            final String RATINGS = "vote_average";
            Movie[] movies=null;
            try {
                JSONObject movie_json = new JSONObject(movies_detail);
                JSONArray movieArray=movie_json.getJSONArray(RESULTS);
                movies=new Movie[movieArray.length()];
                for(int i=0;i<movieArray.length();i++){
                    JSONObject movieObject=movieArray.getJSONObject(i);
                    Movie temp_movie=new Movie();
                    temp_movie.setTitle(movieObject.getString(TITLE));
                    temp_movie.setImage_base_url(movieObject.getString(POSTER_PATH));
                    temp_movie.setOverview(movieObject.getString(OVER_VIEW));
                    temp_movie.setRatings(movieObject.getDouble(RATINGS));
                    temp_movie.setRelease_date(movieObject.getString(RELEASE_DATE));
                    movies[i]=temp_movie;
                }
            }catch (Exception e){
                Log.e(LOG_TAG,e.getMessage());
            }
            return movies;
        }
        @Override
        protected void onPostExecute(Movie[] all_movies) {
            if(all_movies!=null) {
                movies.clear();
                madapter.clear();
                for(int i=0;i<all_movies.length;i++) {
                    movies.add(all_movies[i]);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    madapter.addAll(movies);

                }
                madapter.notifyDataSetChanged();
            }
        }

    }
}
