package be.formation.moviedb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.formation.moviedb.model.Movie;

/**
 * Created by mupac_000 on 24-02-17.
 */

public class MovieDAO {
    public static final String TABLE_NAME = "movie";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_SYNOPSIS = "synopsis";
    public static final String COL_RATING = "rating";
    public static final String COL_TUMBNAIL = "tumbnailUrl";
    public static final String COL_RELEASEDATE = "releaseDate";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                            +"("+ COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                            +COL_TITLE+" TEXT NOT NULL ,"
                            +COL_SYNOPSIS+" TEXT NOT NULL ,"
                            +COL_RELEASEDATE+" INTEGER ,"
                            +COL_RATING+" REAL ,"
                            +COL_TUMBNAIL+" TEXT"
                            +");";
    public static final String UPGRADE_TABLE = "DROP TABLE "+TABLE_NAME;

    Context context;
    SQLiteDatabase db;
    DbHelper dh;

    public MovieDAO(Context context) {
        this.context = context;
    }

    public MovieDAO openWritable()
    {
        dh = new DbHelper(context);
        db = dh.getWritableDatabase();
        return this;
    }

    public MovieDAO openReadable()
    {
        dh = new DbHelper(context);
        db = dh.getReadableDatabase();
        return this;
    }

    public long insert(Movie movie)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,movie.getOriginalTitle());
        cv.put(COL_SYNOPSIS,movie.getSynopsis());
        cv.put(COL_RATING,movie.getRating());
        cv.put(COL_RELEASEDATE,movie.getReleaseDate().getTimeInMillis());
        cv.put(COL_TUMBNAIL,movie.getThumbnail());
        return db.insert(TABLE_NAME,null,cv);
    }

    public long update(Movie movie)
    {

        Log.i("MovieDb","updtate el"+ movie.getId());
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,movie.getOriginalTitle());
        cv.put(COL_SYNOPSIS,movie.getSynopsis());
        cv.put(COL_RATING,movie.getRating());
        cv.put(COL_RELEASEDATE,movie.getReleaseDate().getTimeInMillis());
        cv.put(COL_TUMBNAIL,movie.getThumbnail());
        return db.update(TABLE_NAME,cv,COL_ID+"="+ movie.getId(), null);
    }


    public Cursor getAllMovies()
    {
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            return c;
        }else
            return null;
    }

    public Cursor getMovieCursorById(long id)
    {
        Cursor c = db.query(TABLE_NAME,null,COL_ID+"="+id,null,null,null,null);
        if(c.getCount() > 0)
        {
            c.moveToFirst();
            return c;
        }else
            return null;
    }

    public Movie getMovieById(long id){
        Cursor c= getMovieCursorById(id);
        Movie movie;
        if(c.getCount()>0)
        {
            movie = getMovieByCursor(c);

            return movie;
        }

        return null;

    }

    public Movie getMovieByCursor(Cursor c)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(c.getLong(c.getColumnIndex(COL_RELEASEDATE)));
        Movie movie = new Movie(
                c.getLong(c.getColumnIndex(COL_ID)),
                c.getString(c.getColumnIndex(COL_TITLE)),
                c.getString(c.getColumnIndex(COL_TUMBNAIL)),
                c.getString(c.getColumnIndex(COL_SYNOPSIS)),
                c.getFloat(c.getColumnIndex(COL_RATING)),
                calendar
        );
        return movie;
    }

    public List<Movie> getMovies()
    {
        ArrayList<Movie> movieList = new ArrayList<>();
        Cursor c = getAllMovies();
        if(c!=null)

            do {
                movieList.add(getMovieByCursor(c));
            }while (c.moveToNext());
        return movieList;
    }

    public void delete(Movie movie)
    {
        db.delete(TABLE_NAME, COL_ID+"="+movie.getId(),null);
    }

    public void close()
    {
        db.close();
        dh.close();
    }
}
