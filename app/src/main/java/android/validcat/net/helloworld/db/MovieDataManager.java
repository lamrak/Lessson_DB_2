package android.validcat.net.helloworld.db;

import android.content.ContentValues;
import android.content.Context;
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
        db.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(MovieItem.FIELD_TITLE, movieItem.getTitle());
        //...

        long _id = db.insert(MovieItem.TABLE_MOVIE, null, cv);

        if (_id != -1)
            db.setTransactionSuccessful();
        else throw new IOException();
        db.close();

        return _id;
    }

    @Override
    public boolean delete(MovieItem movieItem) {
        return false;
    }

    @Override
    public MovieItem get(int id) {
        return null;
    }

    @Override
    public List<MovieItem> getAll() {
        return null;
    }
}
