package com.example.nishtha.popular_movie_stage2.Query;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.nishtha.popular_movie_stage2.Detail_MovieFragment;
import com.example.nishtha.popular_movie_stage2.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nishtha on 13/4/16.
 */
public class FetchReview extends AsyncTask<String,Void,Review[]>{

    HttpURLConnection urlConnection;
    BufferedReader reader;
    String reviewjson;
    Review[] reviews;
    Detail_MovieFragment fragment;


    public FetchReview(Detail_MovieFragment fragment){
        this.fragment=fragment;
    }
    @Override
    protected Review[] doInBackground(String... params) {
        String base_url="http://api.themoviedb.org/3/movie/" + params[0] + "/reviews";
        String api_key="api_key";
        Uri path=Uri.parse(base_url).buildUpon().
                appendQueryParameter(api_key, "YOUR_API_KEY").build();
        //Log.d("hello","json path"+path.toString());
        try{
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
            reviewjson = buffer.toString();
            Log.d("hello","review json "+reviewjson);
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
        try{
            return getReviews(reviewjson);
        }catch(Exception e){
            Log.d("hello",e.getMessage()+" cant parse");
        }
        return null;
    }

    private Review[] getReviews(String reviewsjson) throws JSONException{
        try {
            JSONObject review_json=new JSONObject(reviewsjson);
            JSONArray review_array=review_json.getJSONArray("results");
            Log.d("hello",review_array.length()+" reviews numbers");
            reviews=new Review[review_array.length()];
            for(int i=0;i<review_array.length();i++){
                JSONObject temp_review_obj=review_array.getJSONObject(i);
                Review temp_review=new Review();
                temp_review.setId(temp_review_obj.getString("id"));
                temp_review.setAuthor(temp_review_obj.getString("author"));
                temp_review.setContent(temp_review_obj.getString("content"));
                temp_review.setUrl(temp_review_obj.getString("url"));
                reviews[i]=temp_review;
            }
        }catch (Exception e){
            Log.d("hello","NJKDSJNCDS  "+e.getMessage()+" here");
            reviews=new Review[1];
            reviews[0]=new Review(null,"no review",null,null);
        }
        return reviews;
    }
    @Override
    protected void onPostExecute(Review[] reviews) {
        super.onPostExecute(reviews);
        Log.d("hello","in post");
        fragment.setReviewAdapter(reviews);
    }
}
