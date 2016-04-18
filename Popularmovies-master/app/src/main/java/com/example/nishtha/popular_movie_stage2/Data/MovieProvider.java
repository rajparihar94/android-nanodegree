package com.example.nishtha.popular_movie_stage2.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nishtha on 4/4/16.
 */
public class MovieProvider extends ContentProvider{
    final static int MOVIE=100;
    final static int MOVIE_WITH_ID=101;
    final static int FAVOURITE=200;
    final static int FAVOURITE_WITH_ID=201;
    MovieDbHelper movieDbHelper;
    private static final UriMatcher matcher=buildUriMatcher();
    private static SQLiteQueryBuilder sqLiteQueryBuilderForMovie;
    private static SQLiteQueryBuilder sqLiteQueryBuilderForFavourite;
    static {
        sqLiteQueryBuilderForFavourite=new SQLiteQueryBuilder();
        sqLiteQueryBuilderForMovie=new SQLiteQueryBuilder();
        sqLiteQueryBuilderForMovie.setTables(MovieContract.MovieEntry.TABLE_NAME);
        sqLiteQueryBuilderForFavourite.setTables(MovieContract.Favourite.TABLE_NAME);
    }
    //movie.id=?
    private static final String queryMovieById=
            MovieContract.MovieEntry.TABLE_NAME+"."+ MovieContract.MovieEntry.COLUMN_ID+"=?";
    //favourite.id=?
    private static final String queryFavouriteById=
            MovieContract.Favourite.TABLE_NAME+"."+MovieContract.Favourite.COLUMN_ID+"=?";
    @Override
    public boolean onCreate() {
        movieDbHelper=new MovieDbHelper(getContext());
        Log.d("hello","DB has been created");
        return true;
    }

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE,MOVIE);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE+"/#",MOVIE_WITH_ID);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_FAV,FAVOURITE);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_FAV+"/#",FAVOURITE_WITH_ID);
        return matcher;
    }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match=matcher.match(uri);
        Cursor retCursor;
        Log.d("hello","match is : "+ match);
        switch (match){
            case MOVIE :
                retCursor = movieDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAVOURITE :
                Log.d("hello",MovieContract.Favourite.TABLE_NAME+" in table");
                retCursor = movieDbHelper.getReadableDatabase().query(
                        MovieContract.Favourite.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                break;
            case MOVIE_WITH_ID : {
                retCursor=getMovieWithId(uri,projection,sortOrder);
                break;
            }
            case FAVOURITE_WITH_ID : {
                retCursor=getFavouriteById(uri,projection,sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("unknown uri");
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d("hello","notified "+uri.toString());
        return retCursor;
    }

    private Cursor getMovieWithId(Uri uri,String[] projection,String sortOrder){
        int movie_id=MovieContract.MovieEntry.getMovieID(uri);
        return sqLiteQueryBuilderForMovie.query(movieDbHelper.getReadableDatabase(),
                projection,
                queryMovieById,
                new String[]{Integer.toString(movie_id)},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getFavouriteById(Uri uri,String[] projection,String sortOrder){
        int movie_id=MovieContract.Favourite.getMovieID(uri);
        return sqLiteQueryBuilderForFavourite.query(movieDbHelper.getReadableDatabase(),
                projection,
                queryFavouriteById,
                new String[]{Integer.toString(movie_id)},
                null,
                null,
                sortOrder
        );
    }
    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match=matcher.match(uri);
        switch (match){
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case FAVOURITE:
                return MovieContract.Favourite.CONTENT_TYPE;
            case FAVOURITE_WITH_ID:
                return MovieContract.Favourite.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        final int match=matcher.match(uri);
        Uri returnuri;
        long _id;
        switch (match){
            case MOVIE :
                _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(_id>0) {
                    returnuri = MovieContract.MovieEntry.buildUriWithId(_id);
                    Log.d("hello",_id+" in insert");
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case FAVOURITE :
                _id=db.insert(MovieContract.Favourite.TABLE_NAME,null,values);
                if(_id>0) {
                    returnuri = MovieContract.Favourite.buildUriWithId(_id);
                    Log.d("hello",_id+" in insert");
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri");
        }
        getContext().getContentResolver().notifyChange(returnuri, null);
        return returnuri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        final int match=matcher.match(uri);
        int rowsDeleted;
        if(selection==null){
            selection="1";
        }
        switch (match){
            case MOVIE :
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOVIE_WITH_ID:
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.COLUMN_ID+"=?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case FAVOURITE :
                rowsDeleted=db.delete(MovieContract.Favourite.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE_WITH_ID:
                Log.d("hello",String.valueOf(ContentUris.parseId(uri)) + "deleted movie's id");
                rowsDeleted=db.delete(MovieContract.Favourite.TABLE_NAME,
                        MovieContract.Favourite.COLUMN_ID + "=?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("unknown uri");
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=movieDbHelper.getWritableDatabase();
        final int match=matcher.match(uri);
        int rowsUpdated;
        if(selection==null){
            selection="1";
        }
        switch (match){
            case MOVIE :
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FAVOURITE :
                rowsUpdated=db.update(MovieContract.Favourite.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri");
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = matcher.match(uri);
        switch (match) {
            case MOVIE: {
                db.beginTransaction();
                int retCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            retCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                Log.d("hello","inserted "+retCount);
                getContext().getContentResolver().notifyChange(uri, null);
                return retCount;
            }
            case FAVOURITE: {
                db.beginTransaction();
                int retCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.Favourite.TABLE_NAME, null, value);
                        if (_id != -1) {
                            retCount++;
                        }
                    }

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return retCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
