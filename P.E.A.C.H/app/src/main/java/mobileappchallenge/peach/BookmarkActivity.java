package mobileappchallenge.peach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();

    ListView lv;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        final Button button = (Button) findViewById(R.id.bookmar_filter);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        final List<SearchObject> items = databaseAccess.getBookmarkRecipes("-----", "-----");
        databaseAccess.close();

        lv = (ListView) findViewById(R.id.listViewBookmarks);
        lv.setAdapter(new CustomSearchAdapter(this, R.layout.search_entry, items));



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item = items.get(position).name;
                Intent openBook = new Intent(BookmarkActivity.this, RecipeActivity.class);

                openBook.putExtra("name",item);
                openBook.putExtra("isSearch",true);
                startActivity(openBook);

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       }

    public void bookmarkButtonOnClick(View v) {
        // do something when the button is clicked

        Spinner spinner1 = (Spinner) findViewById(R.id.BookmarkNutrientSpinner);
        String nutrient = spinner1.getSelectedItem().toString();
        Spinner spinner2 = (Spinner) findViewById(R.id.BookmarkAscDescSpinner);
        String ascdesc = spinner2.getSelectedItem().toString();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        final List<SearchObject> items = databaseAccess.getBookmarkRecipes(nutrient, ascdesc);
        databaseAccess.close();

        lv = (ListView) findViewById(R.id.listViewBookmarks);
        lv.setAdapter(new CustomSearchAdapter(this, R.layout.search_entry, items));



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item = items.get(position).name;
                Intent openBook = new Intent(BookmarkActivity.this, RecipeActivity.class);

                openBook.putExtra("name",item);
                openBook.putExtra("isSearch",false);
                startActivity(openBook);

            }
        });


    }

}
