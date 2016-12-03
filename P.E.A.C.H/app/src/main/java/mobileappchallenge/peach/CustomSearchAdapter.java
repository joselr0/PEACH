package mobileappchallenge.peach;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.util.Log;

import java.util.List;

/**
 * Created by Teribane on 4/27/2016.
 */

public class CustomSearchAdapter extends ArrayAdapter<SearchObject> {

    public CustomSearchAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomSearchAdapter(Context context, int resource, List<SearchObject> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.search_entry, null);
        }

        SearchObject p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.SearchrecipeName);
            TextView tt2 = (TextView) v.findViewById(R.id.SearchcookTime);
            TextView tt3 = (TextView) v.findViewById(R.id.SearchcalorieAmount);

            if (tt1 != null) {
                tt1.setText(p.name);
            }

           if (tt2 != null) {
                tt2.setText("Cooktime: " + p.cooktime + " min");
            }

            if (tt3 != null) {
                tt3.setText("Calories: " + p.calories);
            }
        }

        return v;
    }

}
