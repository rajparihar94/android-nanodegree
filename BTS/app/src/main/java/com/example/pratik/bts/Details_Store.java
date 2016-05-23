package com.example.pratik.bts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class Details_Store extends AppCompatActivity {

    EditText editText_Driver_Name,editText_Driver_No,editText_Bus_No;
    Button btn_Store_Detail, btn_Show_Detail;
    private RequestQueue requestQueue;
    private static String URL = Constants.URL_PATH+"index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = BTS_Volley.getInstance(getApplicationContext()).getRequestQueue();

        editText_Bus_No = (EditText)findViewById(R.id.driver_detail_bus_no);
        editText_Driver_Name = (EditText)findViewById(R.id.driver_detail_driver_name);
        editText_Driver_No = (EditText)findViewById(R.id.driver_detail_driver_no);
        btn_Store_Detail = (Button)findViewById(R.id.btn_driver_detail_store);
        btn_Show_Detail = (Button)findViewById(R.id.btn_driver_detail_show);

        btn_Store_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bus_no = editText_Bus_No.getText().toString();
                String driver_name = editText_Driver_Name.getText().toString();
                String driver_no = editText_Driver_No.getText().toString();
                if(bus_no.equals("") || driver_name.equals("") || driver_no.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                } else if(bus_no.length()< 1){

                } else if( driver_name.length() < 2 ){
                    Toast.makeText(getApplicationContext(), "Name too short", Toast.LENGTH_LONG).show();
                } else if( driver_no.length() < 10){
                    Toast.makeText(getApplicationContext(), "Invalid Mobile no.", Toast.LENGTH_LONG).show();
                } else {
                    store_driver_detail(bus_no, driver_name, driver_no);
                }
            }
        });

        btn_Show_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),Driver_detail_show.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void store_driver_detail(final String bus_no, final String driver_name, final String driver_no){
        try {
            StringRequest r = new StringRequest(Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("url : ",url);
                            Log.e("User","  Bus_no:- "+ bus_no +" drive_name:- "+ driver_name +" drive_no:- "+ driver_no);
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
                                        Toast.makeText(Details_Store.this, " Detail Inserted", Toast.LENGTH_SHORT).show();
                                        editText_Bus_No.setText("");
                                        editText_Driver_Name.setText("");
                                        editText_Driver_No.setText("");
                                        break;
                                    case "fail":
                                        Toast.makeText(Details_Store.this, jsonObject.getString("status")+" Fail", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Details_Store.this, "Something went wrong: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            )   {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put(Constants.ATTRIBUTE_DRIVER_BUS_NO, bus_no);
                    m.put(Constants.ATTRIBUTE_DRIVER_DRIVER_NAME, driver_name);
                    m.put(Constants.ATTRIBUTE_DRIVER_DRIVER_NO, driver_no);
                    m.put(Constants.CLIENT_REQUEST,Constants.REQUEST_DRIVER_DETAIL_STORE);
                    return m;
                }
            };


            requestQueue.add(r);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something went wrong: " + ex, Toast.LENGTH_LONG).show();
        }
    }

}
