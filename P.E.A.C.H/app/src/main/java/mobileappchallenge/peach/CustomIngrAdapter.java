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

public class CustomIngrAdapter extends ArrayAdapter<IngrObject> {

    public CustomIngrAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomIngrAdapter (Context context, int resource, List<IngrObject> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ingredient_entry, null);
        }

        IngrObject p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.IngrName);
            TextView tt2 = (TextView) v.findViewById(R.id.IngrAmount);

            if (tt1 != null) {
                tt1.setText(p.name);
            }

            if (tt2 != null) {
                String ingrAmountText = String.valueOf(p.amount);
                if (p.mUnit != null)
                    ingrAmountText += " " + p.mUnit;
                tt2.setText(ingrAmountText);
            }

        }

        return v;
    }

}
