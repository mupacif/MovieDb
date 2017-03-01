package be.formation.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import be.formation.moviedb.db.MovieDAO;

public class MainActivity extends AppCompatActivity {

    Button add;
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    DatabaseManager databaseManager;

    // region onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



      databaseManager = (DatabaseManager) getApplication();

       simpleCursorAdapter = new SimpleCursorAdapter(
               this,
               android.R.layout.simple_list_item_1,
               databaseManager.getAllMovies(),
               new String[]{MovieDAO.COL_TITLE},
                       new int[]{android.R.id.text1}
       );

        listView = (ListView) findViewById(R.id.lv_main_listMovie);
        listView.setAdapter(simpleCursorAdapter);


        add = (Button)findViewById(R.id.bt_main_addMovie);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddMovieActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this,MovieDetailsActivity.class);
                intent.putExtra("movie",databaseManager.getMovie(id));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this,EditMovieActivity.class);
                intent.putExtra("movie",databaseManager.getMovie(id));
                startActivity(intent);
                return true;
            }

        });


    }

    // endregion

    @Override
    protected void onStart() {
        super.onStart();
    Log.i("MainActivity","onstart");



    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("MainActivity","onresume");

//        simpleCursorAdapter.notifyDataSetChanged();

        simpleCursorAdapter.swapCursor(databaseManager.getAllMovies());

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
