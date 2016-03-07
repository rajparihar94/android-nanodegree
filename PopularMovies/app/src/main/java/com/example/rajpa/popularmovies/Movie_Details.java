package com.example.rajpa.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Movie_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setContentView(R.layout.activity_movie__details);
        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Title : "+getIntent().getExtras().getString("name"));
        TextView overview=(TextView)findViewById(R.id.overview);
        overview.setText("Overview : "+getIntent().getExtras().getString("overview"));
        TextView release_date=(TextView)findViewById(R.id.release);
        release_date.setText("Release Date : " + getIntent().getExtras().getString("release_date"));
        TextView ratings=(TextView)findViewById(R.id.ratings);
        ratings.setText("Vote_avg : " + getIntent().getExtras().getDouble("ratings"));
        ImageView poster=(ImageView)findViewById(R.id.poster);
        String final_url="http://image.tmdb.org/t/p/w780/"+getIntent().getExtras().getString("url");
        Picasso.with(this).load(final_url).resize(700,780).into(poster);
    }

}
