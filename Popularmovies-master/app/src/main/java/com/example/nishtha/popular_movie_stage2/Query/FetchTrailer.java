package com.example.nishtha.popular_movie_stage2.Query;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.nishtha.popular_movie_stage2.Detail_MovieFragment;
import com.example.nishtha.popular_movie_stage2.Trailer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nishtha on 12/4/16.
 */
public class FetchTrailer extends AsyncTask<String,Void,Trailer[]> {

    HttpURLConnection urlConnection;
    BufferedReader reader;
    String trailerjsonstring;
    Trailer[] trailers;
    Detail_MovieFragment fragment;

    public FetchTrailer(Detail_MovieFragment fragment){
        this.fragment=fragment;
    }

    @Override
    protected Trailer[] doInBackground(String... params) {
        String base_url="http://api.themoviedb.org/3/movie/";
        String api_key="api_key";
        Uri path=Uri.parse(base_url).buildUpon().
                appendPath(params[0]).
                appendPath("videos").
                appendQueryParameter(api_key, "3604ff88128ffea1029f13f41ac56dfa").
                build();
        try{
            Log.d("hello",path.toString());
            URL url=new URL(path.toString());
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
            trailerjsonstring = buffer.toString();
            Log.d("hello",trailerjsonstring);
            return getTrailer(trailerjsonstring);
        }catch (Exception e){
            Log.e("hello", e.getMessage());
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                Log.e("hello","reader didnt close properly");
            }
        }
        return null;
    }

    private Trailer[] getTrailer(String trailerjsonstring){
        try {
            JSONObject trailer_json=new JSONObject(trailerjsonstring);
            JSONArray trailer_array=trailer_json.getJSONArray("results");
            Log.d("hello",trailer_array.length()+" in trailer");
            trailers=new Trailer[trailer_array.length()];
            for(int i=0;i<trailer_array.length();i++){
                JSONObject temp_trailer_object=trailer_array.getJSONObject(i);
                Trailer temp_Traile=new Trailer();
                temp_Traile.setId(temp_trailer_object.getString("id"));
                temp_Traile.setKey(temp_trailer_object.getString("key"));
                temp_Traile.setName(temp_trailer_object.getString("name"));
                trailers[i]=(temp_Traile);
              //  Log.d("hello",trailers[i].getName());
            }
        }catch (Exception e){
            Log.d("hello",e.getMessage());
        }
        return trailers;
    }
    @Override
    protected void onPostExecute(Trailer[] trailers) {
        //super.onPostExecute(trailers);
        fragment.setTrailerAdapter(trailers);
    }
}
