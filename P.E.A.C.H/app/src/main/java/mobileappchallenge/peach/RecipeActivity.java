package mobileappchallenge.peach;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;

import mobileappchallenge.peach.FunctionClass;

public class RecipeActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this)
                .load("http://images.media-allrecipes.com/userphotos/720x405/237426.jpg")
                .into(imageView);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        Boolean isSearch = bundle.getBoolean("isSearch");


        if (isSearch == true) {
            String name = bundle.getString("name");
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            List<String> items = databaseAccess.getRecipeFromSearch(name);

            TextView t;
            //name



            //id
            String id = items.get(0);


            //calories
            t=(TextView) findViewById(R.id.textView7);
            t.setText(items.get(2));

            //carbs
            t=(TextView) findViewById(R.id.textView9);
            t.setText(items.get(3));
            //sodium
            t=(TextView) findViewById(R.id.textView15);
            t.setText(items.get(4));
            //fat
            t=(TextView) findViewById(R.id.textView11);
            t.setText(items.get(5));
            ///protein
            t=(TextView) findViewById(R.id.textView13);
            t.setText(items.get(6));
            //fiber
            t=(TextView) findViewById(R.id.textView17);
            t.setText(items.get(7));
            //cooktime
            t=(TextView) findViewById(R.id.textView5);
            t.setText(items.get(8)+"m");
            //Instructions
            t=(TextView) findViewById(R.id.textView19);
            Log.d("wi",items.get(10));
            t.setText(items.get(10));


            //image

            ImageView ib = (ImageView) findViewById(R.id.imageView);

            Picasso.with(this)
                    .load(items.get(11))
                    .into(ib);




            //////Ingrediants

          //s  ListView lv;
            //final List<String> it = databaseAccess.getIngredients(items.get(0));
            //lv = (ListView) findViewById(R.id.listView);
           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it);
           // lv.setAdapter(adapter);



            this.listView = (ListView) findViewById(R.id.listView);

            List<String> quotes = databaseAccess.getIngredients(id);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
            this.listView.setAdapter(adapter);


           // ListView lv;
           // final List<String> it = databaseAccess.getIngredients(items.get(0));
            //lv = (ListView) findViewById(R.id.listView);
           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it);
            //lv.setAdapter(adapter);








            databaseAccess.close();





        }
        else if (isSearch == false)
        {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();








            databaseAccess.close();

        }



    }

}
