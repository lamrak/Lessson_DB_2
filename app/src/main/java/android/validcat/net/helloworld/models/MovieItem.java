package android.validcat.net.helloworld.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Oleksii on 12/5/15.
 */
public class MovieItem {
    public static final String TABLE_MOVIE = "movie_db";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DESCR = "description";
    public static final String FIELD_POSTER = "poster";
    public static final String FIELD_RATE = "rate";

    private static final String KEY_JSON_TITLE = "Title";
    private static final String KEY_JSON_POSTER = "Poster";

    public static String[] projection = {
            FIELD_ID,
            FIELD_TITLE,
            FIELD_DESCR,
            FIELD_POSTER,
            FIELD_RATE
    };

    public int id;
    public String title;
    public String description;
    public String poster;
    public float rate;
    public String getTitle() {
        return title;
    }

    public static MovieItem getItemFromCursor(Cursor c) {
        MovieItem item = new MovieItem();
        item.id = c.getInt(c.getColumnIndex(MovieItem.FIELD_ID));
        item.title = c.getString(c.getColumnIndex(MovieItem.FIELD_TITLE));
        item.description = c.getString(c.getColumnIndex(MovieItem.FIELD_DESCR));
        item.poster = c.getString(c.getColumnIndex(MovieItem.FIELD_POSTER));
        item.rate = c.getFloat(c.getColumnIndex(MovieItem.FIELD_RATE));

        return item;
    }

    public static MovieItem getItemFromJson(JSONObject json) throws JSONException {
        MovieItem item = new MovieItem();
        item.title = json.getString(KEY_JSON_TITLE);
        item.poster = json.getString(KEY_JSON_POSTER);
        item.description = "Some Description";
        item.rate = 9F;

        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieItem movieItem = (MovieItem) o;

        if (id != movieItem.id) return false;
        return title.equals(movieItem.title);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        return result;
    }
}
