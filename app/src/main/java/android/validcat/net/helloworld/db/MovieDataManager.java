package android.validcat.net.helloworld.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.validcat.net.helloworld.interfaces.IDataManager;
import android.validcat.net.helloworld.models.MovieItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksii on 12/5/15.
 */
public class MovieDataManager implements IDataManager<MovieItem> {
    // Database fields
    private MovieOpenHelper dbHelper;

    public MovieDataManager(Context context) {
        dbHelper = new MovieOpenHelper(context);
    }

    @Override
    public long save(MovieItem movieItem) throws IOException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MovieItem.FIELD_TITLE, movieItem.getTitle());
        cv.put(MovieItem.FIELD_DESCR, movieItem.description);
        cv.put(MovieItem.FIELD_RATE, movieItem.rate);
        cv.put(MovieItem.FIELD_POSTER, movieItem.poster);

        long _id = db.insert(MovieItem.TABLE_MOVIE, null, cv);
        db.close();

        return _id;
    }

    @Override
    public boolean delete(MovieItem movieItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] whereArgs = {String.valueOf(movieItem.id)};
        int rows = db.delete(MovieItem.TABLE_MOVIE, MovieItem.FIELD_ID,
                whereArgs);

        db.close();
        return rows > 0;
    }

    @Override
    public MovieItem get(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(MovieItem.TABLE_MOVIE,
                MovieItem.projection,
                " id = ? ",
                whereArgs,
                null,
                null,
                null);

        MovieItem item = null;
        if (c.moveToFirst()) {
            item = MovieItem.getItemFromCursor(c);
        }

        return item;
    }

    @Override
    public List<MovieItem> getAll() {
        return null;
    }
}
