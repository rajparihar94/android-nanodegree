package com.example.rajpa.bollywoodmovies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajpa on 21-Jul-16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    LayoutInflater inflater;
    Context context;

    public MovieAdapter(Context context, int id, ArrayList<Movie> images) {
        super(context, id, images);
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView (int position, View convertView , ViewGroup parent){

        final Movie movie = getItem(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item_movies,parent,false);
        }

        ImageView item_imageView = (ImageView)convertView.findViewById(R.id.item_imageview);
        Picasso.with(context).load(movie.getImage_url()).into(item_imageView);

        return convertView;
    }
}
