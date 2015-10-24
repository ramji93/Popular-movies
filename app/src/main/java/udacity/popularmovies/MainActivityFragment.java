package udacity.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    View rootView;

    GridView gridView;




    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false );

    }


    @Override
    public void onStart() {

        super.onStart();




     Fetchmoviedb fetchmoviedb = new Fetchmoviedb();

        String sortby = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString(getString(R.string.pref_sortby_key),getString(R.string.popularity));


        fetchmoviedb.execute(sortby);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         rootView = (View) inflater.inflate(R.layout.fragment_main, container, false);



        gridView = (GridView) rootView.findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Movieinfo object =(Movieinfo) parent.getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(),description.class);

                intent.putExtra("imageurl",object.getImageurl());

                intent.putExtra("title",object.getMoviename());

                intent.putExtra("synopsis",object.getSynopsis());

                intent.putExtra("rating",object.getRating());

                intent.putExtra("release",object.getReleasedate());

                startActivity(intent);


            }
        });



        return rootView;
    }


    public ArrayList<Movieinfo> storemoviedetails(String jsonstr) throws JSONException
    {

        JSONObject response = new JSONObject(jsonstr);

        JSONArray results = response.getJSONArray("results");

        ArrayList<Movieinfo> moviearray = new ArrayList<Movieinfo>();


        for (int i = 0; i < results.length(); i++)
        {

            JSONObject result = results.getJSONObject(i);

            Movieinfo movieinfo = new Movieinfo();

            movieinfo.setImageurl(result.getString("poster_path"));

            movieinfo.setMoviename(result.getString("original_title"));

            movieinfo.setReleasedate(result.getString("release_date"));

            movieinfo.setRating(result.getDouble("vote_average"));

            movieinfo.setSynopsis(result.getString("overview"));

            moviearray.add(movieinfo);


        }

         return moviearray;

    }


    class Fetchmoviedb extends AsyncTask<String,Void,ArrayList<Movieinfo>> {

        @Override
        protected ArrayList<Movieinfo> doInBackground(String... params) {


            HttpURLConnection httpURLConnection = null;


            URL url = null;

            BufferedReader reader = null;

            String forecastJsonStr;

            try {


                Uri uri = Uri.parse("http://api.themoviedb.org/3/discover/movie").buildUpon().appendQueryParameter("sort_by",params[0])
                        .appendQueryParameter("api_key", "72284c7965cc260c47ca0a2942f8db2f").build();


                url = new URL(uri.toString());


                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();




            }

             catch (IOException e)
              {
                Log.e("doinbackground", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
              } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("doinbackground", "Error closing stream", e);
                    }
                }



              }


            try {
                return  storemoviedetails(forecastJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return  null;

        }


        @Override
        protected void onPostExecute(ArrayList<Movieinfo> movieinfos) {
            super.onPostExecute(movieinfos);



            Myarrayadapter myarrayadapter = new Myarrayadapter(getActivity(),movieinfos);


            gridView.setAdapter(myarrayadapter);


        }
    }
}
