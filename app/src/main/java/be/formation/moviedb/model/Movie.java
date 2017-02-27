package be.formation.moviedb.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mupac_000 on 24-02-17.
 */

public class Movie implements Serializable{
    private long id;
    private String originalTitle;
    private String thumbnail;
    private String synopsis;
    private float rating;
    private Calendar releaseDate;

    // region constructors
    public Movie() {
    }

    public Movie(String originalTitle, String thumbnail, String synopsis, float rating, Calendar releaseDate) {
        this.originalTitle = originalTitle;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public Movie(long id, String originalTitle, String thumbnail, String synopsis, float rating,Calendar releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
    // endregion

    // region getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    // endregion
}
