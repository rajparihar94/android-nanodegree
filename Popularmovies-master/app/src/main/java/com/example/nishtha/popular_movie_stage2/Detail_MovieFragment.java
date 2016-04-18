package com.example.nishtha.popular_movie_stage2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishtha.popular_movie_stage2.Adapters.Review_adapter;
import com.example.nishtha.popular_movie_stage2.Adapters.Trailer_adapter;
import com.example.nishtha.popular_movie_stage2.Data.MovieContract;
import com.example.nishtha.popular_movie_stage2.Query.FetchReview;
import com.example.nishtha.popular_movie_stage2.Query.FetchTrailer;
import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class Detail_MovieFragment extends Fragment {
    @Bind(R.id.title) TextView title;
    @Bind(R.id.overview)TextView overview;
    @Bind(R.id.release)TextView release_date;
    @Bind(R.id.ratings)TextView ratings;
    @Bind(R.id.poster)ImageView poster;
    @Bind(R.id.fav_button)ImageButton button;
    LinearListView trailer_list,review_list;
    Movie clickedMovie=null;
    Trailer_adapter trailerAdapter;
    ArrayList<Trailer> trailersl;
    Review_adapter reviewAdapter;
    ArrayList<Review> reviews;
    boolean fav;

    public Detail_MovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("hello", "on start");
        if(clickedMovie!=null&&Utility.isNetworkAvailable(getContext(),getActivity())) {
            new FetchTrailer(this).execute(Integer.toString(clickedMovie.getId()));
            new FetchReview(this).execute(Integer.toString(clickedMovie.getId()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_movie,container,false);
        ButterKnife.bind(this, view);
        trailer_list=(LinearListView)view.findViewById(R.id.detail_trailers);
        review_list=(LinearListView)view.findViewById(R.id.detail_reviews);
        Bundle arguments=getArguments();
        clickedMovie=arguments.getParcelable("movie");
        if(clickedMovie!=null) {
            title.setText(clickedMovie.getTitle());
            overview.setText(clickedMovie.getOverview());
            release_date.setText("Release_date : " + clickedMovie.getRelease_date());
            ratings.setText("Ratings :" + Double.toString(clickedMovie.getRatings()));
            Picasso.with(getContext()).load(clickedMovie.getImage_url()).resize(700,780).into(poster);
        }
        button.setSelected(fav);
        button.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_image));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav = !fav;
                if (clickedMovie == null)
                    return;
                button.setSelected(fav);
                setMovieFavoured(fav);
                if (fav) {
                    Toast.makeText(getContext(),
                            "Movie has been added to the favourite list", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),
                            "Movie has been removed from the favourite list", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("hello","on view created");
        fav=false;
        int present=0;
        present=getContext().getContentResolver().
               query(MovieContract.Favourite.buildUriWithId(clickedMovie.getId()),null,null,null,null).getCount();
        Log.d("hello",present+" times movie is there ");
        if(present>0){
            fav=true;
        }else
        {
            Log.d("hello","clicked movie is not there in the table");
        }
    }

    public void setMovieFavoured(boolean fav){
        if(fav){
            ContentValues values=new ContentValues();
            values.put(MovieContract.Favourite.COLUMN_TITLE,clickedMovie.getTitle());
            values.put(MovieContract.Favourite.COLUMN_OVERVIEW,clickedMovie.getOverview());
            values.put(MovieContract.Favourite.COLUMN_RATINGS,clickedMovie.getRatings());
            values.put(MovieContract.Favourite.COLUMN_RELEASE_DATE,clickedMovie.getRelease_date());
            values.put(MovieContract.Favourite.COLUMN_ID, clickedMovie.getId());
            values.put(MovieContract.Favourite.COLUMN_IMAGE_PATH, clickedMovie.getImage_url());
            getContext().getContentResolver().insert(MovieContract.Favourite.CONTENT_URI, values);

        }else {
            getContext().getContentResolver().delete
                    (MovieContract.Favourite.buildUriWithId(clickedMovie.getId()),null,null);
        }
    }

    public void setTrailerAdapter(Trailer[] trailers){
        trailersl=new ArrayList<>(Arrays.asList(trailers));
        trailerAdapter=new Trailer_adapter(getContext(),trailersl);
        trailer_list.setAdapter(trailerAdapter);
        trailer_list.setOnItemClickListener(new LinearListView.OnItemClickListener() {
           @Override
           public void onItemClick(LinearListView parent, View view, int position, long id) {
               Intent intent=new Intent(Intent.ACTION_VIEW);
                Trailer temp=trailerAdapter.getItem(position);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + temp.getKey()));
                startActivity(intent);
           }
       });
    }

    public void setReviewAdapter(Review[] array_review){
        reviews=new ArrayList<>(Arrays.asList(array_review));
        reviewAdapter=new Review_adapter(getContext(),reviews);
        review_list.setAdapter(reviewAdapter);
    }

}
