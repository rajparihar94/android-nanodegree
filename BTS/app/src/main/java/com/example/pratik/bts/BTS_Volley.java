package com.example.pratik.bts;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by prati on 22-May-16.
 */
public class BTS_Volley {
    private static BTS_Volley ourInstance;
    private RequestQueue requestQueue;
    private Context context;

    public static BTS_Volley getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new BTS_Volley(context);
        }
        return ourInstance;
    }

    public static BTS_Volley getInstance() {
        return ourInstance;
    }

    private BTS_Volley(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
