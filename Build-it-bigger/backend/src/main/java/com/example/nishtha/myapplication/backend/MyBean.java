package com.example.nishtha.myapplication.backend;

import com.example.JokeProvider;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myJoke,result;
    JokeProvider jokeProvider = new JokeProvider();

    public String getData() {
       return result;
    }

    public void setData(String name) {
        result = name;
    }

    public String getMyJoke(){
        return myJoke;
    }

    public void setJoke(){
        myJoke = jokeProvider.getRandomJoke();
    }
}