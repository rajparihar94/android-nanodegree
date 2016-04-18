package com.example.nishtha.popular_movie_stage2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Detail_Movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        if(savedInstanceState==null){
            Detail_MovieFragment fragment=new Detail_MovieFragment();
            Bundle temp=new Bundle();
            temp.putParcelable("movie",getIntent().getParcelableExtra("movie"));
            fragment.setArguments(temp);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.detail_layout,fragment).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
