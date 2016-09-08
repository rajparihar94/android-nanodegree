package com.example.nishtha.telljokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by nishtha on 13/8/16.
 */
public class TellJoke extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_display);
        TextView joke = (TextView)findViewById(R.id.joke);
        Intent i = getIntent();
        String funnyJoke = i.getStringExtra("joke");
        joke.setText(funnyJoke);
    }
}
