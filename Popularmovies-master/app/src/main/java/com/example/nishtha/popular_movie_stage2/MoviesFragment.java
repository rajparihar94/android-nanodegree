package com.example.nishtha.popular_movie_stage2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nishtha.popular_movie_stage2.Adapters.GridAdapter;
import com.example.nishtha.popular_movie_stage2.Data.MovieContract;
import com.example.nishtha.popular_movie_stage2.Query.FetchMovies;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    GridAdapter madapter;
    String sort_type;
    final int MOVIE_LOADER=0;
    Context mcontext;
    boolean fav;
    String[] projection_movie=new String[]{
            MovieContract.MovieEntry.TABLE_NAME+"."+ MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATINGS,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_IMAGE_PATH
    };
    String[] projection_fav=new String[]{
            MovieContract.Favourite.TABLE_NAME+"."+ MovieContract.Favourite._ID,
            MovieContract.Favourite.COLUMN_ID,
            MovieContract.Favourite.COLUMN_TITLE,
            MovieContract.Favourite.COLUMN_OVERVIEW,
            MovieContract.Favourite.COLUMN_RATINGS,
            MovieContract.Favourite.COLUMN_RELEASE_DATE,
            MovieContract.Favourite.COLUMN_IMAGE_PATH
    };

    public static final int _ID=0;
    public static final int MOVIE_ID=1;
    public static final int MOVIE_TITLE=2;
    public static final int MOVIE_OVERVIEW=3;
    public static final int MOVIE_RATINGS=4;
    public static final int MOVIE_RELEASE=5;
    public static final int MOVIE_PATH=6;
    int no_fav_movie=0;

    public MoviesFragment(){
        sort_type="popularity";
        mcontext=getContext();
        fav=false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
         void onItemSelected(Movie movie);
    }

    @Override
    public void onStart() {
        super.onStart();
        no_fav_movie=getContext().getContentResolver()
                .query(MovieContract.Favourite.CONTENT_URI,null,null,null,null).getCount();
        if((fav && no_fav_movie==0)){
            Toast.makeText(getContext(),"Favorite list is empty",Toast.LENGTH_LONG).show();
            updateMovie(sort_type);
        }else if(!fav){
            updateMovie(sort_type);
        }
    }

    private void updateMovie(String sort_type){
        fav=false;
        if(Utility.isNetworkAvailable(getContext(),getActivity())) {
            FetchMovies populateMovie = new FetchMovies(getContext());
            populateMovie.execute(sort_type);
            getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
        }else {
            Toast.makeText(getContext(),"Connection failed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_type,menu);
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
            return true;
        }
        if(item.getItemId()==R.id.fav){
            fav=true;
            loadFavouriteMovie();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFavouriteMovie(){
        no_fav_movie=getContext().getContentResolver()
                .query(MovieContract.Favourite.CONTENT_URI,null,null,null,null).getCount();
        if(no_fav_movie==0){
            Toast.makeText(getContext(),"Favorite list is empty",Toast.LENGTH_LONG).show();
        }else
            getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        madapter=new GridAdapter(getActivity(),null,0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grids);
        gridView.setAdapter(madapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor=(Cursor)parent.getItemAtPosition(position);
                Movie movie=new Movie(cursor);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(movie);
                }

            }
        });
        return  rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader;
        Uri content_uri;
        if(fav){
            loader = new CursorLoader(getActivity(),
                    MovieContract.Favourite.CONTENT_URI,
                    projection_fav,
                    null,
                    null,
                    null);
        }else{
            content_uri=MovieContract.MovieEntry.CONTENT_URI;
            loader=new CursorLoader(getActivity(),content_uri,projection_movie,null,null,null);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        madapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        madapter.swapCursor(null);
    }
}
