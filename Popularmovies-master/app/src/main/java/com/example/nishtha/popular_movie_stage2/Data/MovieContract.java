package com.example.nishtha.popular_movie_stage2.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by nishtha on 4/4/16.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY="com.example.nishtha.popular_movie_stage2";
    public static final String PATH_MOVIE="movie";
    public static final String PATH_FAV="favourite";
    public static final Uri BASE_URI= Uri.parse("content://"+CONTENT_AUTHORITY);


    public static class MovieEntry implements BaseColumns {
        public final static Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_IMAGE_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATINGS = "vote_average";

        public static Uri buildUriWithId(long id){
            Log.d("hello",ContentUris.withAppendedId(CONTENT_URI,id).toString()+" in contract");
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static int getMovieID(Uri uri){
            Log.d("hello",uri.getPathSegments().get(1)+"id");
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }


    public static class Favourite implements BaseColumns{
        public final static Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_FAV).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_IMAGE_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATINGS = "vote_average";

        public static Uri buildUriWithId(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static int getMovieID(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }
}
