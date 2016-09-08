package com.example.nishtha.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nishtha.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by nishtha on 13/8/16.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String>{
    private static MyApi myApiService = null;
    public AsyncResponse delegate=null;
    String joke;
    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
//
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    .setRootUrl("http://10.42.0.1:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-140313.appspot.com/_ah/api/");
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getMyJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        Log.d("hello",joke);
        this.joke=joke;
        delegate.processFinish(joke);
    }
}
