package udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class descriptionFragment extends Fragment {

    public descriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        View rootview = inflater.inflate(R.layout.fragment_description, container, false);

        ImageView imageView =  (ImageView)  rootview.findViewById(R.id.imageview);

        Intent intent = getActivity().getIntent();

        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+intent.getStringExtra("imageurl")).resize(450,450).into(imageView);


        TextView title = (TextView)rootview.findViewById(R.id.title);
        title.setText(intent.getStringExtra("title"));


        TextView rating = (TextView)rootview.findViewById(R.id.rating);
        rating.setText(Double.toString(intent.getDoubleExtra("rating", 7)));


        TextView release = (TextView)rootview.findViewById(R.id.release);
        release.setText(intent.getStringExtra("release"));


        TextView synopsis = (TextView)rootview.findViewById(R.id.synopsis);
        synopsis.setText(intent.getStringExtra("synopsis"));




        return rootview;



    }
}
