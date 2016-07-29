package com.example.rajpa.popularmovies;

/**
 * Created by rajpa on 07-Mar-16.
 */
public class Movie {

    String title,overview,release_date,image_url;
    final String BASE_URL ="http://image.tmdb.org/t/p/w342/";

    double ratings;
    String final_url;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getRelease_date() { return release_date; }

    public void setRelease_date(String release_date) { this.release_date = release_date; }

    public String getImage_url() { return final_url; }

    public void setImage_base_url(String image_base_url) {

        this.image_url = image_base_url;
        final_url = BASE_URL+image_base_url;
    }

    public double getRatings() { return ratings; }

    public void setRatings(double ratings) { this.ratings =ratings; }


}
