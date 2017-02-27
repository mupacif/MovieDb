package be.formation.moviedb;

import android.app.Application;
import android.database.Cursor;

import be.formation.moviedb.db.DbHelper;
import be.formation.moviedb.db.MovieDAO;
import be.formation.moviedb.model.Movie;

/**
 * Created by mupac_000 on 24-02-17.
 */

public class DatabaseManager extends Application {
    private MovieDAO movieDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        movieDAO = new MovieDAO(this);
    }

    public void insert(Movie m)
    {
        movieDAO.openWritable();
        movieDAO.insert(m);
        movieDAO.close();
    }

    public void update(Movie m)
    {
        movieDAO.openWritable();
        movieDAO.update(m);
        movieDAO.close();
    }

    public void delete(Movie m)
    {
        movieDAO.openWritable();
        movieDAO.delete(m);
        movieDAO.close();
    }

    public Cursor getAllMovies()
    {
        movieDAO.openReadable();
        Cursor movies = movieDAO.getAllMovies();
        movieDAO.close();
        return movies;
    }

    public Movie getMovie(long id)
    {

        movieDAO.openReadable();
        Movie movie = movieDAO.getMovieById(id);
        movieDAO.close();
        return movie;

    }
}
