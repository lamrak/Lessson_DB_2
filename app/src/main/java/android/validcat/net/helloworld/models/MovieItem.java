package android.validcat.net.helloworld.models;

/**
 * Created by Oleksii on 12/5/15.
 */
public class MovieItem {
    public static final String TABLE_MOVIE = "movie_db";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DESCR = "description";
    public static final String FIELD_RATE = "rate";

    int id;
    String title;
    String description;
    float rate;

    public String getTitle() {
        return title;
    }
}
