package com.example.nishtha.popular_movie_stage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements MoviesFragment.Callback {


    boolean tablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.movie_detail_container) != null) {
            tablet = true;
            Log.d("hello",tablet+" ");
        } else {
            tablet = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onItemSelected(Movie movie) {
        if(!tablet){
            Intent intent=new Intent(this,Detail_Movie.class);
            intent.putExtra("movie",movie);
            startActivity(intent);
        }else{
            Bundle arguments = new Bundle();
            arguments.putParcelable("movie", movie);
            Detail_MovieFragment fragment = new Detail_MovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }
}
