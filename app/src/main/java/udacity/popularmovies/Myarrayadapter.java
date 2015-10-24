package udacity.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by e on 22-09-2015.
 */
public class Myarrayadapter extends BaseAdapter
{
private final Context context;
private final ArrayList<Movieinfo> movieinfoArrayList;

public Myarrayadapter(Context c,ArrayList<Movieinfo> m)
{



    context = c;
    movieinfoArrayList = m;

}


    @Override
    public int getCount() {
        return movieinfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieinfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(450,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //picaso statment

        //image.tmdb.org/t/p/w154//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movieinfoArrayList.get(position).getImageurl()).resize(450,450).into(imageView);

        return imageView;

    }
}
