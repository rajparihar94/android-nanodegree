package com.example.nishtha.popular_movie_stage2.Query;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.nishtha.popular_movie_stage2.Data.MovieContract;
import com.example.nishtha.popular_movie_stage2.Data.MovieContract.MovieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by nishtha on 4/4/16.
 */
public class FetchMovies extends AsyncTask<String,Void,Void> {
    String LOG_TAG="hello";
    HttpURLConnection urlConnection=null;
    BufferedReader reader;
    String moviesJsonStr;
    Context mcontex;
    public FetchMovies(Context context){
        mcontex=context;
    }
    @Override
    protected Void doInBackground(String... params) {
        try{
            final String BASE_URL="http://api.themoviedb.org/3/discover/movie";
            final String SORT_BY="sort_by";
            final String API_KEY="api_key";
            Uri uri=Uri.parse(BASE_URL).buildUpon().appendQueryParameter(SORT_BY,params[0]+".desc").
                    appendQueryParameter(API_KEY,"3604ff88128ffea1029f13f41ac56dfa").build();
            URL url = new URL(uri.toString());
            Log.v("hello", "THE URL IS: " + url);
        // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {

            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

            }
            moviesJsonStr = buffer.toString();
            getMovieData(moviesJsonStr);

        }catch(Exception e){
                Log.e(LOG_TAG,e.getMessage());
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                try{
                    if(reader!=null){
                        reader.close();
                    }
                }catch (Exception e){
                    Log.e(LOG_TAG,"reader didnt close properly");
                }
            }
            return null;
    }
    private void getMovieData(String movies_detail) {
        final String RESULTS = "results";
        final String TITLE = "original_title";
        final String OVER_VIEW = "overview";
        final String POSTER_PATH = "poster_path";
        final String RELEASE_DATE = "release_date";
        final String RATINGS = "vote_average";
        final String ID="id";
        try{
        JSONObject movie_json = new JSONObject(movies_detail);
        JSONArray movieArray=movie_json.getJSONArray(RESULTS);
            Vector<ContentValues> cVector=new Vector<>(movieArray.length());
            int deleted = mcontex.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
            Log.d(LOG_TAG, "Previous data wiping Complete. " +deleted+ " Deleted");
          //  deleted=mcontex.getContentResolver().delete(MovieContract.Favourite.CONTENT_URI,null,null);
            Log.d("hello","delete all the data from the favoirite table " +deleted);
            for(int i=0;i<movieArray.length();i++){
                JSONObject movieObject=movieArray.getJSONObject(i);
                ContentValues value=new ContentValues();
                int id=movieObject.getInt(ID);
                value.put(MovieEntry.COLUMN_ID, id);
                String title=movieObject.getString(TITLE);
                value.put(MovieEntry.COLUMN_TITLE, title);
                String over_view=movieObject.getString(OVER_VIEW);
                value.put(MovieEntry.COLUMN_OVERVIEW, over_view);
                String final_url="http://image.tmdb.org/t/p/w342/"+movieObject.getString(POSTER_PATH);
                value.put(MovieEntry.COLUMN_IMAGE_PATH, final_url);
                String release_date=movieObject.getString(RELEASE_DATE);
                value.put(MovieEntry.COLUMN_RELEASE_DATE,release_date);
                double ratings=movieObject.getDouble(RATINGS);
                value.put(MovieEntry.COLUMN_RATINGS,Double.toString(ratings));
                cVector.add(value);
            }
            int inserted;
            ContentValues[] values=new ContentValues[cVector.size()];
            cVector.toArray(values);
            inserted=mcontex.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI,values);
            Log.d("hello ",inserted+" rows has been inserted");
        }catch (Exception e){
            Log.e(LOG_TAG,e.getMessage());
        }

    }

}
