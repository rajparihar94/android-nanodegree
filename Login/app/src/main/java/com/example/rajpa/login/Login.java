package com.example.rajpa.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * Check if we successfully logged in before.
         * If we did, redirect to home page
         */
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        if (sharedPreferences.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText phone_no = (EditText)findViewById(R.id.phone_no);

                if(phone_no.getText().toString().length() > 9){

                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("logged","logged");
                    editor.commit();

                    Intent intent = new Intent(Login.this,Home.class);
                    startActivity(intent);
                }
            }
        });

    }
}
