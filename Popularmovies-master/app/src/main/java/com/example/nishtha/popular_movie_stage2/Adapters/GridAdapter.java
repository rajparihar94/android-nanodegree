package com.example.nishtha.popular_movie_stage2.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.nishtha.popular_movie_stage2.MoviesFragment;
import com.example.nishtha.popular_movie_stage2.R;
import com.squareup.picasso.Picasso;

/**
 * Created by nishtha on 7/4/16.
 */
public class GridAdapter extends CursorAdapter {

    public GridAdapter(Context context ,Cursor cursor , int flags){
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String url=cursor.getString(MoviesFragment.MOVIE_PATH);
        ImageView image=(ImageView)view.findViewById(R.id.image1);
        Picasso.with(context).load(url).into(image);
    }
}
