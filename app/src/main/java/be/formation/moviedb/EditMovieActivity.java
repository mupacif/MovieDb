package be.formation.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Url;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.formation.moviedb.model.Movie;

public class EditMovieActivity extends AppCompatActivity implements Validator.ValidationListener{
    DatabaseManager databaseManager;
    @NotEmpty
    private EditText originalTitleET;
    @NotEmpty
    private EditText synopsisET;

    private DatePicker releaseDateDP;

    @Url
    @NotEmpty
    private EditText thumbnailET;

    private RatingBar ratingRB;
    private Button saveBT;

    private Validator validator;

    private Movie movie;

    private Button deleteBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        databaseManager = (DatabaseManager)getApplication();
        originalTitleET = (EditText) findViewById(R.id.et_editmovie_originaltitle);
        synopsisET = (EditText) findViewById(R.id.et_editmovie_synopsis);
        releaseDateDP = (DatePicker) findViewById(R.id.dp_editmovie_releasedate);
        thumbnailET = (EditText) findViewById(R.id.et_editmovie_thumbnail);
        ratingRB = (RatingBar) findViewById(R.id.rb_editmovie_rating);
        saveBT = (Button) findViewById(R.id.bt_editmovie_save);
        deleteBt = (Button)findViewById(R.id.bt_editmovie_delete);

        validator = new Validator(this);
        validator.setValidationListener(this);

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movie!=null)
                databaseManager.delete(movie);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if(extra!=null)
        {
            inflateMovie((Movie)extra.getSerializable("movie"));

        }
    }

    // region validation
    @Override
    public void onValidationSucceeded() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(releaseDateDP.getYear(),releaseDateDP.getMonth(),releaseDateDP.getDayOfMonth());
        Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        this.movie.setOriginalTitle(originalTitleET.getText().toString());
        this.movie.setThumbnail(thumbnailET.getText().toString());
        this.movie.setRating(ratingRB.getRating());
        this.movie.setSynopsis(synopsisET.getText().toString());
        this.movie.setReleaseDate(calendar);



            Log.i("Test","year:"+calendar);
            databaseManager.update(movie);



        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this,"One or more field are invalid", Toast.LENGTH_SHORT).show();
    }
    //endregion


    /**
     * Inflate the edit activity layout with fresh date coming from the sqlite database
     * @param movie the movie to be displayed and edited
     */
    public void inflateMovie(Movie movie)
    {
        Calendar c = Calendar.getInstance();

        Calendar release = movie.getReleaseDate();

        c.setTimeInMillis(release.getTimeInMillis());
        setTitle("Edit movie");
        originalTitleET.setText(movie.getOriginalTitle());
        releaseDateDP.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        ratingRB.setRating(movie.getRating());
        synopsisET.setText(movie.getSynopsis());
        thumbnailET.setText(movie.getThumbnail());

        this.movie = movie;
    }
}
