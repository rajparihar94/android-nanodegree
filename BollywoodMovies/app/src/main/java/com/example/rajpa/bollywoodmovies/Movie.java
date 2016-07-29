package com.example.rajpa.bollywoodmovies;

/**
 * Created by rajpa on 21-Jul-16.
 */
public class Movie {

    final String BASE_URL = "https://s3-ap-southeast-1.amazonaws.com/cinemalytics/movie/";
    String image_url,final_url;

    public String getImage_url() {
        return final_url;
    }

    public void setImage_base_url(String image_base_url) {
        this.image_url = image_base_url;
        final_url = BASE_URL+image_base_url;
    }
}
