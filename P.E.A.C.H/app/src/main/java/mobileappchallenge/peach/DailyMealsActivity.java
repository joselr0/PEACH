package mobileappchallenge.peach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class DailyMealsActivity extends AppCompatActivity {

    ImageView img;
    ImageView img2;
    Bitmap bitmap;
    ProgressDialog pDialog;
    TextView txt;
    int year;
    int month;
    int day;
    String strMonth;
    String strDay;
    String date;
    private ListView listView;
    List<String> dates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_meals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        Boolean isCalendar = bundle.getBoolean("isCalendar");
        if (isCalendar == true) {
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            day = bundle.getInt("day");

            month += 1;

            if (month < 10){
                strMonth = "0" + Integer.toString(month);
            }
            else{
                strMonth = Integer.toString(month);
            }

            if (day < 10){
                strDay = "0" + Integer.toString(day);
            }
            else{
                strDay = Integer.toString(day);
            }










            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            dates = databaseAccess.getRecipes(strDay,strMonth,year);

            if (dates.get(0).equalsIgnoreCase("Today is an empty day")){
                Toast.makeText(getApplicationContext(),dates.get(0), Toast.LENGTH_LONG).show();
            }
            else {
                ImageButton ib = (ImageButton) findViewById(R.id.breakfast1);

                Picasso.with(this)
                        .load(dates.get(0))
                        .into(ib);

                ImageButton ib2 = (ImageButton) findViewById(R.id.breakfast2);

                Picasso.with(this)
                        .load(dates.get(4))
                        .into(ib2);

                ImageButton ib3 = (ImageButton) findViewById(R.id.lunch1);

                Picasso.with(this)
                        .load(dates.get(8))
                        .into(ib3);

                ImageButton ib4 = (ImageButton) findViewById(R.id.lunch2);

                Picasso.with(this)
                        .load(dates.get(12))
                        .into(ib4);

                ImageButton ib5 = (ImageButton) findViewById(R.id.dinner1);

                Picasso.with(this)
                        .load(dates.get(16))
                        .into(ib5);

                ImageButton ib6 = (ImageButton) findViewById(R.id.dinner2);

                Picasso.with(this)
                        .load(dates.get(20))
                        .into(ib6);
            }

            databaseAccess.close();


        }
        else if(isCalendar == false){
            date = bundle.getString("date");

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            dates = databaseAccess.getRecipesFromHome(date);

            if (dates.get(0).equalsIgnoreCase("Today is an empty day")){
                Toast.makeText(getApplicationContext(),dates.get(0), Toast.LENGTH_LONG).show();
            }
            else {
                ImageButton ib = (ImageButton) findViewById(R.id.breakfast1);

                Picasso.with(this)
                        .load(dates.get(0))
                        .into(ib);

                ImageButton ib2 = (ImageButton) findViewById(R.id.breakfast2);

                Picasso.with(this)
                        .load(dates.get(4))
                        .into(ib2);

                ImageButton ib3 = (ImageButton) findViewById(R.id.lunch1);

                Picasso.with(this)
                        .load(dates.get(8))
                        .into(ib3);

                ImageButton ib4 = (ImageButton) findViewById(R.id.lunch2);

                Picasso.with(this)
                        .load(dates.get(12))
                        .into(ib4);

                ImageButton ib5 = (ImageButton) findViewById(R.id.dinner1);

                Picasso.with(this)
                        .load(dates.get(16))
                        .into(ib5);

                ImageButton ib6 = (ImageButton) findViewById(R.id.dinner2);

                Picasso.with(this)
                        .load(dates.get(20))
                        .into(ib6);
            }
            databaseAccess.close();
        }

        ImageButton breakfast01 = (ImageButton) findViewById(R.id.breakfast1);
        breakfast01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(1));
            }
        });

        ImageButton breakfast02 = (ImageButton) findViewById(R.id.breakfast2);
        breakfast02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(5));
            }
        });

        ImageButton lunch01 = (ImageButton) findViewById(R.id.lunch1);
        lunch01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(9));
            }
        });

        ImageButton lunch02 = (ImageButton) findViewById(R.id.lunch2);
        lunch02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(13));
            }
        });

        ImageButton dinner01 = (ImageButton) findViewById(R.id.dinner1);
        dinner01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(17));
            }
        });

        ImageButton dinner02 = (ImageButton) findViewById(R.id.dinner2);
        dinner02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(dates.get(21));
            }
        });
    }

    public void buttonOnClick(String name)
    {
        // do something when the button is clicked
        Intent recipe = new Intent(this, RecipeActivity.class);


        recipe.putExtra("name", name);
        recipe.putExtra("isSearch", true);




        startActivity(recipe);
    }

    public class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DailyMealsActivity.this);
        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if (image != null) {
                img.setImageBitmap(image);
                img2.setImageBitmap(image);
                pDialog.dismiss();

            } else {

                pDialog.dismiss();
                Toast.makeText(DailyMealsActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}




