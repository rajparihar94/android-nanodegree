package com.example.rajpa.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajpa on 02-Mar-16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    LayoutInflater inflater;
    Context context;

    public MovieAdapter(Context context, int id, ArrayList<Movie> images) {
        super(context, id, images);
        this.context =context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        final Movie movie = getItem(position);
        Log.d("Popular Movie" , "ValueOf position:- " + String.valueOf(position)) ;

        if(convertView == null)
        {
            convertView =  inflater.inflate(R.layout.grid_item_movies,parent,false);
            Log.d("Popular Movie" , "ConvertView = Null");
        }

        ImageView item_imageview = (ImageView)convertView.findViewById(R.id.item_imageview);
        Log.d("Popular Movie" , movie.getImage_url());
        Picasso.with(context).load(movie.getImage_url()).into(item_imageview);

    return convertView;
    }



}
