package com.example.pratik.bts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver_detail_show extends AppCompatActivity {

    private List<Driver> driverList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DriverAdapter dAdapter;

    private RequestQueue requestQueue;
    private static String URL = Constants.URL_PATH+"index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = BTS_Volley.getInstance(getApplicationContext()).getRequestQueue();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        dAdapter = new DriverAdapter(driverList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // set the adapter
        recyclerView.setAdapter(dAdapter);
        fetch_driver_data();

    }

    public void fetch_driver_data(){
        try {
            StringRequest r = new StringRequest(Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("url : ",url);
                            //Log.e("User","  enroll:- "+ enrollment +" pass:- "+ password);
                            try {
                                /*int jsonStart = response.indexOf("{");
                                int jsonEnd = response.lastIndexOf("}");

                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    response = response.substring(jsonStart, jsonEnd + 1);
                                } else {
                                    // deal with the absence of JSON content here
                                }
*/
                                JSONArray jsonObject = new JSONArray(response);
                                int len = jsonObject.length();
                                Log.e("Values length : ", len+"");
                                for(int i = 0; i < len; i++){
                                    String bus_no = String.valueOf(jsonObject.getJSONObject(i).getString("bus_no"));
                                    String driver_name = String.valueOf(jsonObject.getJSONObject(i).getString("driver_name"));
                                    String driver_no = String.valueOf(jsonObject.getJSONObject(i).getString("driver_no"));
                                    Driver driver;
                                    driver = new Driver(bus_no, driver_name, driver_no);
                                    driverList.add(driver);
                                }
                                dAdapter.notifyDataSetChanged();

                                //Log.e("Json value driver_name: ", jsonObject.getString("driver_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.v("Connection url ",url);
                            Toast.makeText(Driver_detail_show.this, "Something went wrong: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            )   {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put(Constants.CLIENT_REQUEST,Constants.REQUEST_DRIVER_DETAIL_SHOW);
                    return m;
                }
            };


            requestQueue.add(r);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something went wrong: " + ex, Toast.LENGTH_LONG).show();
        }
    }
}
