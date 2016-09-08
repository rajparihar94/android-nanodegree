package com.example.nishtha.builditbigger;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by nishtha on 15/8/16.
 */
public class JokeRetriveTest extends InstrumentationTestCase implements AsyncResponse{

    CountDownLatch signal;
    String joke;
    EndpointsAsyncTask endpoint;

    public JokeRetriveTest() {

    }

    public void testJoke() {
        try {
            Log.d("hello","heyyy i m done");
            signal = new CountDownLatch(1);
            endpoint = new EndpointsAsyncTask();
            endpoint.delegate = this;
            endpoint.execute();
            signal.await(30, TimeUnit.SECONDS);
            Log.d("hello","this is joke"+joke);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void processFinish(String joke) {
        this.joke=joke;
        Log.d("hello",signal.getCount()+" this is count");
        signal.countDown();
    }

}
