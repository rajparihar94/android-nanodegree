package com.example.pratik.bts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static String URL = Constants.URL_PATH+"index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = BTS_Volley.getInstance(getApplicationContext()).getRequestQueue();

        final EditText editTextUserName;
        final EditText editTextPassword;
        Button b1;

        editTextUserName=(EditText)findViewById(R.id.editText_enrollment_toLogin);
        editTextPassword=(EditText)findViewById(R.id.editText_password_toLogin);
        b1=(Button) findViewById(R.id.login_button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enrollment = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                if(enrollment.equals("")||password.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    login(enrollment, password);
                }
            }
        });
    }

    public void login(final String enrollment, final String password){
        try {
            StringRequest r = new StringRequest(Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("url : ",url);
                            Log.e("User","  enroll:- "+ enrollment +" pass:- "+ password);
                            try {
                                int jsonStart = response.indexOf("{");
                                int jsonEnd = response.lastIndexOf("}");

                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    response = response.substring(jsonStart, jsonEnd + 1);
                                } else {
                                    // deal with the absence of JSON content here
                                }

                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("Data coming : ",jsonObject.toString());
                                switch (jsonObject.getString("status")) {
                                    case "success":

                                        Toast.makeText(LoginActivity.this, " Registration Done", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), After_login.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "fail":
                                        Toast.makeText(LoginActivity.this, jsonObject.getString("status")+" Fail", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.v("Connection url ",url);
                            Toast.makeText(LoginActivity.this, "Something went wrong: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            )   {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put(Constants.ATTRIBUTE_STUDENT_ENROLLMENT, enrollment);
                    m.put(Constants.ATTRIBUTE_STUDENT_PASSWORD, password);
                    m.put(Constants.CLIENT_REQUEST,Constants.REQUEST_LOGIN);
                    return m;
                }
            };


            requestQueue.add(r);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something went wrong: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
