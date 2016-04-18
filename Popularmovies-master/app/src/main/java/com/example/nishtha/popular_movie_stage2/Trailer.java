package com.example.nishtha.popular_movie_stage2;

/**
 * Created by nishtha on 12/4/16.
 */
public class Trailer {
    String name,key,id;
   public Trailer(){

    }
    Trailer(String name,String key,String id){
        this.name=name;
        this.key=key;
        this.id=id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

