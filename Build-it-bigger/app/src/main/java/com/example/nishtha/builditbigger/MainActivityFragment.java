package com.example.nishtha.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nishtha.telljokes.TellJoke;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AsyncResponse {

    EndpointsAsyncTask endpoint;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button b = (Button) root.findViewById(R.id.jokeButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endpoint = new EndpointsAsyncTask();
                endpoint.delegate = MainActivityFragment.this;
                endpoint.execute();
            }
        });
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        mAdView.loadAd(adRequest);
        return root;
    }



    @Override
    public void processFinish(String joke) {
//        JokeProvider jokeProvider = new JokeProvider();
        Intent i = new Intent(getContext(), TellJoke.class);
        i.putExtra("joke", joke);
        startActivity(i);
    }
}
