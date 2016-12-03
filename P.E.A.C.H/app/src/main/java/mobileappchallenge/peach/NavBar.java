package mobileappchallenge.peach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.lang.String;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class NavBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pDialog;
    Bitmap bitmap;
    ImageButton img;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        String Cal;
        Cal = databaseAccess.getTargetCal();

        //set button 1
        String targetCal = Cal;
        Button button = (Button) findViewById(R.id.targetCal);
        button.setText("Targeted" + "\n" + "Calories" + "\n" + targetCal);

        Cal = databaseAccess.getConsumedCal();

        //set button 2
        String caloriesConsumed = Cal;

        button = (Button) findViewById(R.id.consumedCal);
        button.setText("Consumed" + "\n" + "Calories" + "\n" + caloriesConsumed);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get random image
        //img = (ImageButton) findViewById(R.id.randomRecipe);
        //new NavBar.LoadImage().execute(databaseAccess.getRandomMealImage());

        list = databaseAccess.getRandomMealImage();
        String name = list.get(1);
        String URL = list.get(0);

        databaseAccess.close();

        ImageButton ib = (ImageButton) findViewById(R.id.randomRecipe);

        Picasso.with(this)
                .load(URL)
                .into(ib);

        Button gotoSetup = (Button) findViewById(R.id.plannerSetup);
        gotoSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(0);
            }
        });

        ImageButton gotoDailyMeals = (ImageButton) findViewById(R.id.nextMeal);
        gotoDailyMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(1);
            }
        });

        Button gotoNutrients1 = (Button) findViewById(R.id.consumedCal);
        gotoNutrients1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(2);
            }
        });

        Button gotoNutrients2 = (Button) findViewById(R.id.targetCal);
        gotoNutrients2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(2);
            }
        });

        ImageButton gotoRandomRecipe = (ImageButton) findViewById(R.id.randomRecipe);
        gotoRandomRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick(3);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        return true;
    }

    public void buttonOnClick(int ID)
    {
        // do something when the button is clicked
        if (ID == 0) {
            Intent setup = new Intent(this, PlannerSetupActivity.class);
            startActivity(setup);
        }
        else if (ID == 1) {
            Intent setup = new Intent(this, DailyMealsActivity.class);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            setup.putExtra("isCalendar", false);
            setup.putExtra("date",sdf.format(cal.getTime()));



            startActivity(setup);
        }
        else if (ID == 2) {
            Intent setup = new Intent(this, Nutrients.class);
            startActivity(setup);
        }
        else if (ID == 3) {
            Intent setup = new Intent(this, RecipeActivity.class);

            setup.putExtra("name",list.get(1));
            setup.putExtra("isSearch",true);


            startActivity(setup);
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            // Handle the calendar action
            Intent cal = new Intent(this, CalendarActivity.class);
            startActivity(cal);
        } else if (id == R.id.nav_search) {
            Intent search = new Intent(this, SearchActivity.class);
            startActivity(search);
        }else if (id == R.id.nav_shoppinglist) {
            Intent shop = new Intent(this, ShoppingListActivity.class);
            startActivity(shop);
        }
        else if (id == R.id.nav_profilesettings) {
            Intent settings = new Intent(this, IntakeSettingsActivity.class);
            startActivity(settings);
        } else if (id == R.id.nav_bookmarks) {
            Intent bookmark = new Intent(this, BookmarkActivity.class);
            startActivity(bookmark);
        } else if (id == R.id.nav_about) {
            Intent about = new Intent(this, AboutActivity.class);
            startActivity(about);
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NavBar.this);
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
                pDialog.dismiss();

            } else {

                pDialog.dismiss();
                Toast.makeText(NavBar.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
