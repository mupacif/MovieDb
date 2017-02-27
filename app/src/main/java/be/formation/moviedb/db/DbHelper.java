package be.formation.moviedb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mupac_000 on 24-02-17.
 */

public class DbHelper  extends SQLiteOpenHelper{
    public static final String DB_NAME = "Movie.db";
    public static final int DB_VERSION = 3;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(MovieDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MovieDAO.UPGRADE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
