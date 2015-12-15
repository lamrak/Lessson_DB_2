package android.validcat.net.helloworld.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.validcat.net.helloworld.models.MovieItem;

/**
 * Created by Oleksii on 12/5/15.
 */
public class MovieOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "movie_db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_GENRE = "genre";

    private Context context;
    private static String createMovieTable =
            "CREATE TABLE " + MovieItem.TABLE_MOVIE + " (" +
                    MovieItem.FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MovieItem.FIELD_TITLE + " TEXT NOT NULL," +
                    MovieItem.FIELD_DESCR + " TEXT NOT NULL," +
                    MovieItem.FIELD_POSTER + " TEXT NOT NULL," +
                    MovieItem.FIELD_RATE + " INT NOT NULL" +
            ");";

    public MovieOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMovieTable);
        db.execSQL("CREATE TABLE "+ TABLE_GENRE +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MovieItem.TABLE_MOVIE);
        db.execSQL("DROP TABLE " + TABLE_GENRE);
        onCreate(db);
    }
}
