package be.formation.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.formation.moviedb.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView synopsis;
    private ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle exta = this.getIntent().getExtras();




        title  = (TextView) findViewById(R.id.tv_moviedetails_tiltle);
                releaseDate = (TextView) findViewById(R.id.tv_moviedetails_releasedate);
        rating = (TextView) findViewById(R.id.tv_moviedetails_rating);
                synopsis = (TextView) findViewById(R.id.tv_moviedetails_synopsis);
        thumbnail = (ImageView) findViewById(R.id.iv_moviedetails_thumbnail);

        if (exta != null)
            inflateMovie((Movie) exta.getSerializable("movie"));

    }

    public void inflateMovie(Movie movie)
    {
        Calendar release = movie.getReleaseDate();
        title.setText(movie.getOriginalTitle());
        releaseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(release.getTime()));
        rating.setText(String.valueOf(movie.getRating()));
        synopsis.setText(movie.getSynopsis());
        Picasso.with(thumbnail.getContext()).load(movie.getThumbnail()).centerCrop().fit().into(thumbnail);
    }
}
