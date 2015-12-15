package android.validcat.net.helloworld;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.validcat.net.helloworld.db.MovieDataManager;
import android.validcat.net.helloworld.models.MovieItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String PREFS_NAME = "movie_prefs";
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        CheckBox checkFavorite = (CheckBox) findViewById(R.id.check_favorite);

        // Restore preferences
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFavorite = settings.getBoolean("isFavorite", false);
        checkFavorite.setChecked(isFavorite);

        checkFavorite.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("isFavorite", isChecked);
                        editor.apply();
                    }
                }
        );

//        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new CustomListAdapter());
    }

    private void initData() {
        MovieFetcherAsync task = new MovieFetcherAsync();
        task.execute("http://www.omdbapi.com/?t=Hunger+Games&y=2015&plot=short&r=json");
    }


    public class MovieFetcherAsync extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String json = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line + "\n");

                if (buffer.length() == 0)
                    return null;
                json = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping to parse it.
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return json;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null)
                return;

            try {
                JSONObject json = new JSONObject(result);
                // new MobvieItem -> db

                MovieItem item = MovieItem.getItemFromJson(json);

                if (!TextUtils.isEmpty(item.title))
                    ((TextView) findViewById(R.id.tv_title)).setText(item.title);
                if (!TextUtils.isEmpty(item.poster))
                    Picasso.with(getApplicationContext())
                            .load(item.poster)
                            .into((ImageView) findViewById(R.id.iv_poster));

                MovieDataManager db = new MovieDataManager(getBaseContext());
                try {
                    item.id = (int) db.save(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MovieItem itemFromDB = db.get(1);

                if (item.equals(itemFromDB)) {
                    Log.d(LOG_TAG, "YESS!");
                } else {
                    Log.e(LOG_TAG, "Oh no :(");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG, result);
        }
    }

//    private class CustomListAdapter extends RecyclerView.Adapter {
//        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter",
//                "Saturn", "Uranus", "Neptune", "Ceres", "Pluto", "Haumea", "Makemake", "Eris",
//                "Orcus", "Ixion", "Salacia", "Varuna", "Quaoar", "Sedna", "Moon", "Io", "Europa",
//                "Ganymede", "Rhea"};
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v =
//                    LayoutInflater.from(
//                            parent.getContext()).inflate(
//                            R.layout.item_for_adapter, parent, false);
//            return new MyViewHolder(v);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ((MyViewHolder)holder).planet.setText(planets[position]);
//        }
//
//        @Override
//        public int getItemCount() {
//            return planets.length;
//        }
//
//        public class MyViewHolder extends RecyclerView.ViewHolder {
//            public TextView planet;
//
//            public MyViewHolder(View view) {
//                super(view);
//                planet = (TextView) view.findViewById(R.id.tv_planet);
//            }
//        }
//    }
}
