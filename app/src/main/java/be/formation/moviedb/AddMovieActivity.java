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
import com.mobsandgeeks.saripaar.annotation.Past;
import com.mobsandgeeks.saripaar.annotation.Url;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.formation.moviedb.model.Movie;

public class AddMovieActivity extends AppCompatActivity implements Validator.ValidationListener{

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

    Validator validator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);


        databaseManager = (DatabaseManager)getApplication();
        originalTitleET = (EditText) findViewById(R.id.et_addmovie_originaltitle);
                synopsisET = (EditText) findViewById(R.id.et_addmovie_synopsis);
        releaseDateDP = (DatePicker) findViewById(R.id.dp_addmovie_releasedate);
                thumbnailET = (EditText) findViewById(R.id.et_addmove_thumbnail);
        ratingRB = (RatingBar) findViewById(R.id.rb_addmovie_rating);
                saveBT = (Button) findViewById(R.id.bt_addmovie_save);

        validator = new Validator(this);
        validator.setValidationListener(this);

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });


    }

    // region validation
    @Override
    public void onValidationSucceeded() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(releaseDateDP.getYear(),releaseDateDP.getMonth(),releaseDateDP.getDayOfMonth());
        Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        Movie movie = new Movie(originalTitleET.getText().toString(),
                thumbnailET.getText().toString(),
                synopsisET.getText().toString(),
                ratingRB.getRating(),
                calendar);



            Log.i("Test",""+calendar.getTime().getYear());
            databaseManager.insert(movie);




        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
            Toast.makeText(this,"One or more field are invalid", Toast.LENGTH_SHORT).show();
    }
    //endregion


}
