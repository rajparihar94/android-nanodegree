package com.example.nishtha.popular_movie_stage2;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nishtha on 21/2/16.
 */
public class Movie implements Parcelable {
    String title,overview,release_date;
    public String image_url;
    final String BASE_URL="http://image.tmdb.org/t/p/w342/";
    double ratings;
    int id;
    String final_url;
    Movie(){

    }
    Movie(Cursor cursor){
        this.title=cursor.getString(MoviesFragment.MOVIE_TITLE);
        this.id=cursor.getInt(MoviesFragment.MOVIE_ID);
        this.overview=cursor.getString(MoviesFragment.MOVIE_OVERVIEW);
        this.release_date=cursor.getString(MoviesFragment.MOVIE_RELEASE);
        this.final_url=cursor.getString(MoviesFragment.MOVIE_PATH);
        this.ratings=Double.parseDouble(cursor.getString(MoviesFragment.MOVIE_RATINGS));
    }
    Movie(Parcel in){
        String[] data=new String[4];
        in.readStringArray(data);
        this.title=data[0];
        this.overview=data[1];
        this.final_url=data[2];
        this.release_date=data[3];
        this.ratings=in.readDouble();
        this.id=in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.title,this.overview,this.final_url,this.release_date
        });
        dest.writeDouble(new Double(this.ratings));
        dest.writeInt(new Integer(this.id));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage_url() {
        return final_url;
    }

    public void setImage_base_url(String image_base_url) {
        this.image_url = image_base_url;
        final_url=BASE_URL+image_base_url;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
